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
import javafx.stage.Screen;
import javafx.stage.Stage;



public class GameAppUserInput extends Application {



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
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        //int screenWidth = 1080;
        //int screenHeight = 720;

        environment = new Environment(3, 5, screenWidth, screenHeight);





        Projectile test1 = new Projectile(0.7, 0, 0, 9.80, 40, 30, screenHeight);
        Player testPlayer1 = new Player(test1);
        environment.setPlayer(testPlayer1);

        // Create UI elements for user input
        TextField forceInput = new TextField();
        TextField angleInput = new TextField();
        Button launchButton = new Button("Launch");

        forceInput.setPromptText("Enter Force");
        angleInput.setPromptText("Enter Angle");

        // Adjusted styling for better aesthetics
        forceInput.setStyle("-fx-font-size: 14px; -fx-pref-width: 120px; -fx-padding: 5px;");
        angleInput.setStyle("-fx-font-size: 14px; -fx-pref-width: 120px; -fx-padding: 5px;");
        launchButton.setStyle("-fx-font-size: 14px; -fx-padding: 5px 15px;");

        launchButton.setOnAction(event -> {
            try {
                double force = Double.parseDouble(forceInput.getText());
                double angle = Double.parseDouble(angleInput.getText());
                Projectile test = new Projectile(0.7, force, angle, 9.80, 40, 30, screenHeight);
                Player testPlayer = new Player(test);
                environment.setPlayer(testPlayer);
                //
                environment.incrementTries(); // Count the attempt

                // Start the game only after input is given
                environment.startGameLoop();

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter numeric values.");
            }
        });

        HBox inputBox = new HBox(15, new Label("Force:"), forceInput, new Label("Angle:"), angleInput, launchButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 15px; -fx-background-color: #eeeeee;");

        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(inputBox);
        bottomPane.setStyle("-fx-padding: 15px;");

        //  Manually draw the ball at t = 0 (no game loop)
        Pane staticFrame = environment.getProjectilePane(0);
        environment.getGamePane().getChildren().setAll(staticFrame.getChildren());

        currentFrame = environment.getGamePane();
        BorderPane root = new BorderPane();
        root.setCenter(currentFrame);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}