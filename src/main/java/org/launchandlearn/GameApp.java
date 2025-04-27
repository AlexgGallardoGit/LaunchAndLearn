package org.launchandlearn;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Group;

/**
 * Main application class for the game.
 */
public class GameApp extends Application {
    private Level level;
    private int numberOfTrys;
    private Pane currentFrame;

    private Group trajectoryPreview; // stores the preview dots

    private static final double TARGET_FPS = 120;
    private static final double FRAME_TIME = 1_000_000_000 / TARGET_FPS;
    public Environment environment;
    private Stage stage;
    private double screenWidth;
    private double screenHeight;
    private boolean isFullScreen;
    private int startLevel = 1;

    public GameApp() {
        this.level = new Level(startLevel);
        this.numberOfTrys = 0;
    }

    public void setStartLevel(int level) {
        this.startLevel = level;
        this.level = new Level(level);
    }

    public void setScreenDimensions(double width, double height, boolean fullScreen) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.isFullScreen = fullScreen;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        // If dimensions haven't been set, get them from the stage or use defaults
        if (screenWidth == 0 || screenHeight == 0) {
            if (primaryStage.getWidth() > 0 && primaryStage.getHeight() > 0) {
                this.screenWidth = primaryStage.getWidth();
                this.screenHeight = primaryStage.getHeight();
                this.isFullScreen = primaryStage.isFullScreen();
            } else {
                this.screenWidth = 1080;
                this.screenHeight = 720;
                this.isFullScreen = false;
            }
        }

        // Apply dimensions to stage
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);
        primaryStage.setResizable(false);
        startLevel(startLevel);
    }

    private void startLevel(int levelNumber) {
        // Clear any existing content
        if (currentFrame != null) {
            currentFrame.getChildren().clear();
        }

        level.setCurrentLevel(levelNumber);
        int numWalls = level.calculateNumberOfWalls();
        int numTargets = level.calculateNumberOfTargets();
        environment = new Environment(numWalls, numTargets, screenWidth, screenHeight, level);

        // Reset tries counter for new level
        numberOfTrys = 0;

        // Generate a random mas between 0.1 and 1 kg for the projectile
        double mass = Math.random() * (1 - 0.1) + 0.1;

        // Round the mass to one decimal place
        mass = Math.round(mass * 10) / 10.0;

        // Create projectile with initial position
        Projectile initialProjectile = new Projectile(mass, 0, 0, 9.80, 0, 0, screenWidth);
        Player player = new Player(initialProjectile);
        environment.setPlayer(player);

        // Create UI elements for user input
        TextField forceInput = new TextField();
        TextField angleInput = new TextField();
        Button launchButton = new Button("Launch");

        forceInput.setPromptText("Enter Force (N)");
        angleInput.setPromptText("Enter Angle (°)");

        forceInput.setStyle("-fx-font-size: 14px; -fx-pref-width: 120px; -fx-padding: 5px;");
        angleInput.setStyle("-fx-font-size: 14px; -fx-pref-width: 120px; -fx-padding: 5px;");
        launchButton.setStyle("-fx-font-size: 14px; -fx-padding: 5px 15px;");

        launchButton.setOnAction(event -> {
            try {
                double force = Double.parseDouble(forceInput.getText());
                double angle = Double.parseDouble(angleInput.getText());
                boolean valid = true;
                if (force <= 0 || angle < 0 || angle > 90) {
                    // Optionally show error elsewhere or ignore
                    valid = false;
                }
                if (!valid) return;
                initialProjectile.setForce(force);
                initialProjectile.setAngleInDegrees(angle);
                Player updatedPlayer = new Player(initialProjectile);
                environment.setPlayer(updatedPlayer);
                environment.incrementTries();
                numberOfTrys = environment.getNumberOfTries();
                environment.startGameLoop();
                // Check if level is complete after each launch
                if (environment.getTargetsLeft() == 0) {
                    showLevelComplete();
                }
            } catch (NumberFormatException e) {
                // Optionally show error elsewhere or ignore
            }
        });

        // Create an Hbox to hold the forceInput
        Label forceLabel = new Label("N");
        forceLabel.setFont(Font.font("Arial", 14));
        HBox forceInputBox = new HBox(1, forceInput, forceLabel);
        forceInputBox.setAlignment(Pos.CENTER);

        // Create an Hbox to hold the angleInput
        Label angleLabel = new Label("°");
        angleLabel.setFont(Font.font("Arial", 14));
        HBox angleInputBox = new HBox(1, angleInput, angleLabel);

        HBox inputBox = new HBox(15, new Label("Force:"), forceInputBox, new Label("Angle:"), angleInputBox, launchButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 15px; -fx-background-color: #eeeeee;");

        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(inputBox);
        bottomPane.setStyle("-fx-padding: 15px;");
        // --- Add Return to Main Menu Button ---
        Button returnToMenuButton = new Button("Return to Main Menu");
        returnToMenuButton.setStyle("-fx-font-size: 14px; -fx-padding: 5px 15px; -fx-background-color: #d9534f; -fx-text-fill: white;");
        returnToMenuButton.setOnAction(e -> returnToMenu());

        // Place button at bottom right
        bottomPane.setRight(returnToMenuButton);
        BorderPane.setAlignment(returnToMenuButton, Pos.BOTTOM_RIGHT);
        // --- End Add ---

        // Draw initial state
        Pane staticFrame = environment.getProjectilePane(0);
        environment.getGamePane().getChildren().setAll(staticFrame.getChildren());

        currentFrame = environment.getGamePane();
        BorderPane root = new BorderPane();
        root.setCenter(currentFrame);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(scene);
        if (isFullScreen) {
            stage.setFullScreen(true);
        }
        stage.show();
    }

    private void showLevelComplete() {
        try {
            Menu menu = new Menu();
            menu.show(stage, String.valueOf(level.getScore(numberOfTrys)), level.getCurrentLevel() + 1);
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to main menu if there's an error
            returnToMenu();
        }
    }

    private void returnToMenu() {
        try {
            // Save the current level before returning to the menu
            MainMenu menu = new MainMenu();
            menu.setStartLevel(level.getCurrentLevel());
            menu.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
            // If we can't even return to menu, close the application
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
