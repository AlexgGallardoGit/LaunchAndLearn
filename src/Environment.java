import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class Environment {
    // Data attributes
    Player player;
    Wall[] wall;
    Target[] target;
    int gamePaneWidth = 1080;
    int gamePaneHeight = 720;
    BorderPane environment;

    // Constructor
    Environment(int numWalls, int numTargets) {
        player = new Player();
        wall = new Wall[numWalls];
        target = new Target[numTargets];

    }

    Environment(Player player, Wall[] wall, Target[] target) {
        this.player = player;
        this.wall = Arrays.copyOf(wall, wall.length);
        this.target = Arrays.copyOf(target, target.length);
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
