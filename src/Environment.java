import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class Environment {
    // Data attributes
    private Player player;
    private Wall[] wall;
    private Target[] target;
    private final int gamePaneWidth;
    private final int gamePaneHeight;
    private BorderPane environment;

    // Constructor
    public Environment(int numWalls, int numTargets, int maxHeightOfStructure, int gamePaneWidth, int gamePaneHeight) {
        this.gamePaneWidth = gamePaneWidth;
        this.gamePaneHeight = gamePaneHeight;
        this.player = new Player();
        for (int i = 0; i < numWalls; i++) {
            this.wall[i] = new Wall(maxHeightOfStructure);
        }
        for (int i = 0; i < numTargets; i++) {
            this.target[i] = new Target(maxHeightOfStructure);
        }
    }

    public Environment(Player player, Wall[] wall, Target[] target, int gamePaneWidth, int gamePaneHeight) {
        this.player = player;
        this.wall = Arrays.copyOf(wall, wall.length);
        this.target = Arrays.copyOf(target, target.length);
        this.gamePaneWidth = gamePaneWidth;
        this.gamePaneHeight = gamePaneHeight;
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







}
