package org.launchandlearn;

public class GameApp {
    private Level level;

    public GameApp() {
        this.level = new Level();
    }
    public GameApp(int currentLevel) {
        this.level = new Level(currentLevel);
    }
}
