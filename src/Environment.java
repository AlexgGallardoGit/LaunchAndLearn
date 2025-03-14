import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.*;

public class Environment {
    // Data attributes
    private Player player;
    private Wall[] wall;
    private Target[] target;
    private final int gamePaneWidth;
    private final int gamePaneHeight;

    // Constructor
    public Environment(int numWalls, int numTargets, int gamePaneWidth, int gamePaneHeight) {
        // Size of the space before and after the structures
        double freeSpaceLeft = 100;
        double freeSpaceRight = 20;

        // Actual Pane width
        double actualPaneWidth = gamePaneWidth - freeSpaceLeft - freeSpaceRight;

        // The Number of free spaces minimum is 10(places for structures)
        int nbOfStructureSlots = Math.max(10, (numWalls + numTargets));

        // Initialize the game pane size
        this.gamePaneWidth = gamePaneWidth;
        this.gamePaneHeight = gamePaneHeight;

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

    public Environment(Player player, Wall[] wall, Target[] target, int gamePaneWidth, int gamePaneHeight) {
        this.player = player;
        this.wall = Arrays.copyOf(wall, wall.length);
        this.target = Arrays.copyOf(target, target.length);
        this.gamePaneWidth = gamePaneWidth;
        this.gamePaneHeight = gamePaneHeight;
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

    public BorderPane getEnvironmentPane() {
        Pane pane = new Pane();

        // Add the targets to the pane
        for (int i = 0; i < this.target.length; i++) {
            pane.getChildren().add(target[i].getStructure(gamePaneHeight));
        }

        // Add the walls to the pane
        for (int i = 0; i < this.wall.length; i++) {
            pane.getChildren().add(wall[i].getStructure(gamePaneHeight));
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        return borderPane;
    }






}
