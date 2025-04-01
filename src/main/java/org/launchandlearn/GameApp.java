package org.launchandlearn;

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
        // Set screen dimensions
        int screenWidth = 1080;
        int screenHeight = 720;

        environment = new Environment(3, 6, screenWidth, screenHeight);

        // Create the projectile class
       // Projectile test = new Projectile(0.7, 800, 80, 9.80, 40, 30, screenHeight);
        Projectile test = new Projectile(0.7, 800, 80, 9.80, -95, 110, screenHeight);

        Player testPlayer = new Player(test);

        environment.setPlayer(testPlayer);


        currentFrame = environment.getGamePane();


        Scene scene = new Scene(currentFrame, screenWidth, screenHeight);
        primaryStage.setScene(scene);

        primaryStage.show();
        environment.startGameLoop();
        System.out.println("Game loop started!");
    }
}
