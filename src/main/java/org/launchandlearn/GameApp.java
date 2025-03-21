package org.launchandlearn;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameApp extends Application {

    private Pane currentFrame;
    private static final double TARGET_FPS = 120;
    private static final double FRAME_TIME = 1_000_000_000 / TARGET_FPS; // Time per frame in nanoseconds
    public Environment environment;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        environment = new Environment(5, 5,1080, 720);

        // Create the projectile class
        Projectile test = new Projectile(0.7, 800, 70, 9.80, 40, 30);

        Player testPlayer = new Player(test);

        environment.setPlayer(testPlayer);


        currentFrame = environment.getGamePane();

        // Set screen dimensions
        double screenWidth = 1080;
        double screenHeight = 720;
        Scene scene = new Scene(currentFrame, screenWidth, screenHeight);
        primaryStage.setScene(scene);

        primaryStage.show();
        environment.startGameLoop();
    }

    // Example pane: Moving circle animation
    private Pane generatePane(double seconds) {
        return environment.getProjectilePane(seconds);
    }
}
