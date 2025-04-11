package org.launchandlearn;

import javafx.scene.layout.Pane;

public class GameApp {
    private Level level;
    private int numberOfTrys;
    private Pane currentFrame;
    private static final double TARGET_FPS = 120;
    private static final double FRAME_TIME = 1_000_000_000 / TARGET_FPS; // Time per frame in nanoseconds
    public Environment environment;
    double screenWidth = 1080;
    double screenHeight = 720;

    public GameApp() {
        this.level = new Level();
        this.numberOfTrys = 0;
    }
    public GameApp(int currentLevel) {
        this.level = new Level(currentLevel);
        this.numberOfTrys = 0;
    }
    public GameApp(int currentLevel, int numberOfTrys) {
        this.level = new Level(currentLevel);
        this.numberOfTrys = numberOfTrys;
    }
}
