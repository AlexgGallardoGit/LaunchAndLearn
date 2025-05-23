package org.launchandlearn;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.List;

import javafx.scene.paint.Color;

public class Environment {
    // Data attributes
    private static final double TARGET_FPS = 120;
    private static final double FRAME_TIME = 1_000_000_000 / TARGET_FPS; // Time per frame in nanoseconds
    private Player player;
    private int numberOfTries = 0; // Tracks total projectile launches
    private SoundEffects soundEffects = new SoundEffects();

    // Data attributes for the game environment
    private Wall[] wall;
    private Target[] target;
    private final double gamePaneWidth;
    private final double gamePaneHeight;
    private Pane gamePane;
    private int targetsLeft;
    private Level level;
    private int currentGameState = 0;

    public int getNumberOfTries() {
        return numberOfTries;
    }
    public void incrementTries() {
        numberOfTries++;
    }
    private void showScoreMenu() {
        char grade = level.getScore(numberOfTries);
        Menu menu = new Menu();
        Stage stage = (Stage) gamePane.getScene().getWindow();
        menu.show(stage, String.valueOf(grade), level.getCurrentLevel() + 1);
    }



    // Constructor with level support
    public Environment(int numWalls, int numTargets, double gamePaneWidth, double gamePaneHeight, Level level) {
        this(numWalls, numTargets, gamePaneWidth, gamePaneHeight);
        this.level = level;
    }

    // Original constructor for backward compatibility
    public Environment(int numWalls, int numTargets, double gamePaneWidth, double gamePaneHeight) {
        // Set the default gamePane Value
        this.gamePane = new Pane();
        this.level = new Level(1); // Default to level 1 for backward compatibility

        // Size of the space before and after the structures
        double freeSpaceLeft = (130 * gamePaneWidth )/ 1080;
        double freeSpaceRight = 0;

        // Initialize the game pane size
        this.gamePaneWidth = gamePaneWidth;
        this.gamePaneHeight = gamePaneHeight;

        // Set the size for the motion environment
        gamePaneWidth = (int) (this.gamePaneWidth * 0.80);
        gamePaneHeight = (int) (this.gamePaneHeight * 0.80);

        // Set initialize targets left
        this.targetsLeft = numTargets;

        // Actual Pane width
        double actualPaneWidth = gamePaneWidth - freeSpaceLeft - freeSpaceRight;

        // The Number of free spaces minimum is 10(places for structures)
        int nbOfStructureSlots = Math.max(10, (numWalls + numTargets));

        // Initialize the player attribute
        this.player = new Player();

        // Calculate the width of the structure
        double width = actualPaneWidth/nbOfStructureSlots;

        // Initialize the wall and target
        this.wall = new Wall[numWalls];
        this.target = new Target[numTargets];

        // Calculate the max StructureHeight (70% of the game pane height)
        double maxHeightOfStructure = gamePaneHeight * 0.70;

        // Place walls and targets in an array
        int blankCount = nbOfStructureSlots - (numTargets + numWalls);
        String[] structureList = new String[numTargets + numWalls + blankCount];

        // Assign all the values in the array
        for (int i = 0; i < numTargets; i++) {
            structureList[i] = "Target";
        }
        for (int i = 0; i < numWalls; i++) {
            structureList[i + numTargets] = "Wall";
        }
        for (int i = 0; i < blankCount; i++) {
            structureList[i + numTargets + numWalls] = "Blank";
        }

        // Shuffle the array, making sure there are no concurrent target's
        structureList = generateValidStructureOrder(structureList);

        // Create all the structures
        int numTargetsLeft = numTargets;
        int numWallsLeft = numWalls;
        for (int i = 0; i < structureList.length; i++) {
            if (structureList[i].equals("Target")) {
                double leftXLocation = i * width + freeSpaceLeft;
                this.target[numTargets - numTargetsLeft] = new Target(maxHeightOfStructure, width, leftXLocation);
                numTargetsLeft--;
            } else if (structureList[i].equals("Wall")) {
                double leftXLocation = i * width + freeSpaceLeft;
                this.wall[numWalls - numWallsLeft] = new Wall(maxHeightOfStructure, width, leftXLocation);
                numWallsLeft--;
            }
        }
    }

    // Constructor with level support for Player, Wall, Target version
    public Environment(Player player, Wall[] wall, Target[] target, int gamePaneWidth, int gamePaneHeight, Level level) {
        this(player, wall, target, gamePaneWidth, gamePaneHeight);
        this.level = level;
    }

    // Original constructor for Player, Wall, Target version
    public Environment(Player player, Wall[] wall, Target[] target, int gamePaneWidth, int gamePaneHeight) {
        // Set the default gamePane Value
        this.gamePane = new Pane();
        this.level = new Level(1); // Default to level 1 for backward compatibility

        this.player = player;
        this.wall = Arrays.copyOf(wall, wall.length);
        this.target = Arrays.copyOf(target, target.length);
        this.gamePaneWidth = gamePaneWidth;
        this.gamePaneHeight = gamePaneHeight;

        // Initialize targetsLeft based on the Target array provided
        this.targetsLeft = 0;
        for (Target t : target) {
            if (!t.getIsHit()) {
                this.targetsLeft++;
            }
        }
    }

    // Methode to shuffle structures
    public static String[] generateValidStructureOrder(String[] structureList) {
        List<String> elements = new ArrayList<>(Arrays.asList(structureList));
        Random rand = new Random();
        int maxRetries = 1000; // Maximum shuffle attempts before falling back

        for (int attempt = 0; attempt < maxRetries; attempt++) {
            Collections.shuffle(elements, rand); // Random shuffle

            // Check if "Target" words are consecutive
            boolean valid = true;
            for (int i = 1; i < elements.size(); i++) {
                if (elements.get(i).equals("Target") && elements.get(i - 1).equals("Target")) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                return elements.toArray(new String[0]); // Return a valid sequence
            }
        }

        // Fallback: Efficiently distribute "Target" words
        return generateValidStructureOrderFallback(structureList);
    }

    private static String[] generateValidStructureOrderFallback(String[] structureList) {
        List<String> targets = new ArrayList<>();
        List<String> others = new ArrayList<>();

        // Separate Targets and other elements
        for (String s : structureList) {
            if (s.equals("Target")) {
                targets.add(s);
            } else {
                others.add(s);
            }
        }

        // Shuffle both lists for randomness
        Collections.shuffle(targets);
        Collections.shuffle(others);

        List<String> result = new ArrayList<>();
        Queue<String> targetQueue = new LinkedList<>(targets);
        Queue<String> otherQueue = new LinkedList<>(others);
        boolean lastWasTarget = false;

        // Build the sequence efficiently
        while (!targetQueue.isEmpty() || !otherQueue.isEmpty()) {
            if (!lastWasTarget && !targetQueue.isEmpty()) {
                result.add(targetQueue.poll());
                lastWasTarget = true;
            } else if (!otherQueue.isEmpty()) {
                result.add(otherQueue.poll());
                lastWasTarget = false;
            } else {
                result.add(targetQueue.poll());
                lastWasTarget = true;
            }
        }

        return result.toArray(new String[0]);
    }

    // Getters and Setters
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public Wall[] getWall() {
        return Arrays.copyOf(wall, wall.length);
    }
    public void setWall(Wall[] wall) {
        this.wall = Arrays.copyOf(wall, wall.length);
    }
    public Target[] getTarget() {
        return Arrays.copyOf(target, target.length);
    }
    public void setTarget(Target[] target) {
        this.target = Arrays.copyOf(target, target.length);
    }
    public Pane getGamePane() {
        return gamePane;
    }
    public void setGamePane(Pane gamePane) {
        this.gamePane = gamePane;
    }
    public int getTargetsLeft() {
        return targetsLeft;
    }
    public void setTargetsLeft(int targetsLeft) {
        this.targetsLeft = targetsLeft;
    }

    public Pane getStructurePane() {
        Pane structurePane = new Pane();

        // Add the targets to the pane
        for (int i = 0; i < this.target.length; i++) {
            structurePane.getChildren().add(target[i].getStructure((int) (gamePaneHeight * 0.80)));
            //structurePane.getChildren().add(target[i].getStructure((int) gamePaneHeight));
        }

        // Add the walls to the pane
        for (int i = 0; i < this.wall.length; i++) {
            structurePane.getChildren().add(wall[i].getStructure((int) (gamePaneHeight * 0.80)));
            //structurePane.getChildren().add(wall[i].getStructure((int) gamePaneHeight));
        }

        // Add the player to the pane
        Image characterImage = new Image(getClass().getResource("/images/standingCharacterImage.png").toExternalForm());
        ImageView characterImageView = new ImageView(characterImage);
        // Set height to 1 meter in pixels
        double characterHeight = player.getProjectile().getNumberOfPixelsPerMeter();
        characterImageView.setFitHeight(characterHeight);
        characterImageView.setPreserveRatio(true);

        // Calculate character width based on preserved ratio
        double characterWidth = characterHeight * characterImage.getWidth() / characterImage.getHeight();

        // Position character at the leftmost edge
        double characterY = gamePaneHeight * 0.80 - characterHeight; // Same level as structures
        characterImageView.setX(0); // Directly at the left edge
        characterImageView.setY(characterY);
        structurePane.getChildren().add(characterImageView);

        // Update projectile's starting position to match character's right hand
        double handX = characterWidth * 0.8; // Right hand position relative to left edge
        double handY = characterY + (characterHeight * 0.70); // Hand at 70% from top of character
        player.getProjectile().setStartX(handX);
        player.getProjectile().setStartY(gamePaneHeight * 0.80 - handY); // Convert to coordinate system used by projectile

        return structurePane;
    }


    public Pane getProjectilePane(double currentSeconds) {
        // Define the size of the projectile
        int projectilePixelRadius = (int) (gamePaneHeight * 0.01);

        // Start with the structure (background, walls, etc.)
        Pane structurePane = getStructurePane();

        Projectile projectile = player.getProjectile();

        double projX_px = projectile.calculateHorizontalPosition(currentSeconds);
        double projY_px = projectile.calculateVerticalPosition(currentSeconds);

        // Calculate actual range in meters (horizontal distance from startX to currentX)
        double startX_px = projectile.getStartX();
        double actualRange_px = Math.abs(projX_px - startX_px);
        double actualRange_m = actualRange_px / projectile.getNumberOfPixelsPerMeter();

        // Create the projectile circle at the starting position or current trajectory position
        Circle projectileCircle = new Circle(
                projX_px, // Use calculated X position always
                gamePaneHeight * 0.80 - projY_px, // Convert to screen coordinates
                projectilePixelRadius
        );

        // Add the projectile to the pane
        structurePane.getChildren().add(projectileCircle);
        projectileCircle.setFill(Color.BLACK);

        // --- Polished HUD + Formula Overlay ---
        double velocityX_px = projectile.calculateHorizontalVelocity();
        double velocityY_px = projectile.calculateVerticalVelocity(currentSeconds);
        double elapsedTime = currentSeconds;
        double pxPerMeter = projectile.getNumberOfPixelsPerMeter();
        double velocityX_m = velocityX_px / pxPerMeter;
        double velocityY_m = velocityY_px / pxPerMeter;
        double gravity_mps2 = 9.8;
        double startY_px = projectile.getStartY();
        double startY_m = startY_px / pxPerMeter;

        // Labels for Projectile Info
        Label hudTitleLabel = new Label("Projectile Info");
        Label massLabel = new Label("Mass: " + projectile.getMass() + " kg");
        Label gravityLabel = new Label(String.format("Gravity: %.2f m/s²", gravity_mps2));
        Label velocityXLabel = new Label(String.format("Vx: %.2f m/s", velocityX_m));
        Label velocityYLabel = new Label(String.format("Vy: %.2f m/s", velocityY_m));
        Label timeLabel = new Label(String.format("Time: %.2f s", elapsedTime));
        // Remove estimated range, replace actual range with just "Range"
        Label rangeLabel = new Label(String.format("Range: %.2f m", actualRange_m));

        // Labels for Formula Sheet
        Label formulaTitleLabel = new Label("Formula Sheet");
        Label velocityEqLabel = new Label("v = F / m");
        Label velocityXEqLabel = new Label("vₓ = v * cos(θ)");
        Label velocityYEqLabel = new Label("vᵧ = v * sin(θ)");
        Label xEqLabel = new Label("x(t) = x₀ + vₓ * t");
        Label yEqLabel = new Label("y(t) = y₀ + vᵧ * t - 0.5 * g * t²");
        // Use HBox to visually subscript 'final' in tfinal
        Label rangeEqPrefixLabel = new Label("R = vₓ * t");
        Label rangeEqSubLabel = new Label("final");
        rangeEqSubLabel.setStyle("-fx-font-size: 10px; -fx-translate-y: 4;");
        HBox rangeEqHBox = new HBox(0, rangeEqPrefixLabel, rangeEqSubLabel);
        rangeEqHBox.setAlignment(Pos.BASELINE_LEFT);

        // Apply styles
        String titleStyle = "-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #333;";
        String valueStyle = "-fx-font-size: 16px; -fx-text-fill: #222;";
        hudTitleLabel.setStyle(titleStyle); massLabel.setStyle(valueStyle);
        gravityLabel.setStyle(valueStyle);  velocityXLabel.setStyle(valueStyle);
        velocityYLabel.setStyle(valueStyle); timeLabel.setStyle(valueStyle);
        rangeLabel.setStyle(valueStyle);
        formulaTitleLabel.setStyle(titleStyle);
        velocityEqLabel.setStyle(valueStyle); velocityXEqLabel.setStyle(valueStyle); velocityYEqLabel.setStyle(valueStyle);
        xEqLabel.setStyle(valueStyle); yEqLabel.setStyle(valueStyle); rangeEqPrefixLabel.setStyle(valueStyle);

        // Create two “cards”
        VBox infoCard = new VBox(8, hudTitleLabel, massLabel, gravityLabel, velocityXLabel, velocityYLabel, timeLabel, rangeLabel);
        infoCard.setAlignment(Pos.TOP_LEFT);
        infoCard.setStyle(
                "-fx-background-color: rgba(200,230,255,0.9);" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 10px;"
        );

        VBox formulaCard = new VBox(6, formulaTitleLabel, velocityEqLabel, velocityXEqLabel, velocityYEqLabel, xEqLabel, yEqLabel, rangeEqHBox);
        formulaCard.setAlignment(Pos.TOP_LEFT);
        formulaCard.setStyle(
                "-fx-background-color: rgba(255,240,210,0.9);" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 10px;"
        );

        // Stack them in a single overlay
        VBox overlay = new VBox(12, infoCard, formulaCard);
        overlay.setAlignment(Pos.TOP_RIGHT);
        overlay.setLayoutX(gamePaneWidth - 250);  // tweak horizontally
        overlay.setLayoutY(30);                   // tweak vertically
        structurePane.getChildren().add(overlay);

        // Display the getCurrentGameState info on the structure pane
        switch (this.currentGameState) {
            case 1:
                // No collision detected, continue the game
                break;
            case 2:
                // Hit target
                projectileCircle.setFill(Color.GREEN);
                break;
            case 3:
                // Hit a hit target
                projectileCircle.setFill(Color.ORANGE);
                break;
            case 4:
                // Hit target's wall
                projectileCircle.setFill(Color.RED);
                break;
            case 5:
                // Hit wall
                projectileCircle.setFill(Color.RED);
                break;
            case 6:
                // Missed
                projectileCircle.setFill(Color.GRAY);
                break;
            default:
                // Invalid game state
                break;
        }

        return structurePane;
    }

    // Get current game state, return (1 = no collision, 2 = hit target, 3 = hit a hit target, 4 = hit target's wall, 5 = hit wall, 6 = Missed)
    public int getCurrentGameState(double currentSeconds) {
        Projectile projectile = player.getProjectile();
        double currentXLocation = projectile.calculateHorizontalPosition(currentSeconds);
        double currentYLocation = projectile.calculateVerticalPosition(currentSeconds);

        // Y-coordinate for JavaFX coordinate system (invert Y-axis)
        double correctedYLocation = (gamePaneHeight * 0.80) - currentYLocation;
        int ballRadius = (int) (gamePaneHeight * 0.01);

        // Wall Collision Check
        for (int i = 0; i < wall.length; i++) {
            if (wall[i].contains(currentXLocation, correctedYLocation, (gamePaneHeight * 0.80), ballRadius)) {
                System.out.println("Collided with wall " + i + "!");
                soundEffects.playMissSound();
                this.currentGameState = 5;
                return 5;
            }
        }

        // Out-of-bounds check: allows projectile to move above the screen
        //if (currentXLocation < 0 || currentXLocation > gamePaneWidth * 0.80 ||
        if (currentXLocation < -100 || currentXLocation > gamePaneWidth * 0.80 ||
                correctedYLocation > gamePaneHeight * 0.80) {
            System.out.println("Missed!");
            soundEffects.playMissSound();
            this.currentGameState = 6;
            return 6;
        }

        // Target Collision Check
        for (int i = 0; i < target.length; i++) {
            if (target[i].contains(currentXLocation, correctedYLocation, (gamePaneHeight * 0.80), ballRadius)) {
                double targetTop = (gamePaneHeight * 0.80) - target[i].getHeigth();
                double ballBottom = correctedYLocation + ballRadius;
                double ballTop = correctedYLocation - ballRadius;
                double verticalVelocity = projectile.calculateVerticalVelocity(currentSeconds);

                // Check collision from above and ensure the projectile is moving downward
                if (ballBottom >= targetTop && ballTop < targetTop && verticalVelocity < 0) {
                    if (!target[i].getIsHit()) {
                        System.out.println("Hit Target " + i + "!");
                        target[i].setIsHit(true);
                        this.targetsLeft--;
                        soundEffects.playHitSound();
                        if (this.targetsLeft == 0) {
                            showScoreMenu(); // display score menu when all targets are hit
                        }
                        this.currentGameState = 2;
                        return 2;
                    } else {
                        System.out.println("Hit Target " + i + " that was already hit!");
                        this.currentGameState = 3;
                        return 3;
                    }
                } else {
                    System.out.println("Collided with Target " + i + "'s wall!");
                    soundEffects.playMissSound();
                    this.currentGameState = 4;
                    return 4;
                }
            }
        }
        // No collision
        this.currentGameState = 1;
        return 1;
    }

    public void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private long startTime = 0;
            private double previousElapsedTime = 0;

            @Override
            public void handle(long now) {
                if (startTime == 0) startTime = now;

                if (now - lastUpdate >= FRAME_TIME) {
                    double elapsedTime = (now - startTime) / 1_000_000_000.0;

                    boolean collisionDetected = false;
                    double checkInterval = 0.001; // Small interval for accurate checking
                    double simulatedTime = previousElapsedTime;

                    // Simulate small steps between last and current frames
                    while (simulatedTime < elapsedTime) {
                        simulatedTime += checkInterval;

                        int currentGameState = getCurrentGameState(simulatedTime);

                        // If collision detected
                        if (currentGameState != 1) {
                            collisionDetected = true;

                            // Update frame exactly at the collision moment
                            Pane collisionFrame = getProjectilePane(simulatedTime);
                            gamePane.getChildren().setAll(collisionFrame.getChildren());

                            this.stop();
                            break;
                        }
                    }

                    // No collision detected, continue normally
                    if (!collisionDetected) {
                        Pane newFrame = getProjectilePane(elapsedTime);
                        gamePane.getChildren().setAll(newFrame.getChildren());
                    }

                    previousElapsedTime = elapsedTime;
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }
}