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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        Screen screen = Screen.getPrimary();
        double screenWidth = 1080;
        double screenHeight = 720;

        environment = new Environment(3, 5, screenWidth, screenHeight);

        // Load the image and style it
        Image image1 = new Image(getClass().getResource("/images/test1figure.png").toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitHeight(300); // Adjust as needed
        imageView1.setPreserveRatio(true);
        imageView1.setTranslateY(120); // Lower the image on screen

        // Put image in a VBox to center it vertically
        VBox imageBox = new VBox(imageView1);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setStyle("-fx-background-color: #eeeeee; -fx-padding: 20px;");

        // Create UI input
        TextField forceInput = new TextField();
        TextField angleInput = new TextField();
        Button launchButton = new Button("Launch");

        forceInput.setPromptText("Enter Force");
        angleInput.setPromptText("Enter Angle");

        forceInput.setStyle("-fx-font-size: 14px; -fx-pref-width: 120px; -fx-padding: 5px;");
        angleInput.setStyle("-fx-font-size: 14px; -fx-pref-width: 120px; -fx-padding: 5px;");
        launchButton.setStyle("-fx-font-size: 14px; -fx-padding: 5px 15px;");

        launchButton.setOnAction(event -> {
            try {
                double force = Double.parseDouble(forceInput.getText());
                double angle = Double.parseDouble(angleInput.getText());
                Projectile test = new Projectile(0.7, force, angle, 9.80, -95, 110, screenHeight);

                Player testPlayer = new Player(test);
                environment.setPlayer(testPlayer);
                environment.startGameLoop();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter numeric values.");
                System.out.println("Number of targets left: " + environment.getTargetsLeft());
            }
        });

        HBox inputBox = new HBox(15, new Label("Force:"), forceInput, new Label("Angle:"), angleInput, launchButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 15px; -fx-background-color: #eeeeee;");

        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(inputBox);
        bottomPane.setStyle("-fx-padding: 15px;");

        // Info panel for displaying mass, acceleration, and formulas
        VBox infoPanel = new VBox(10);
        infoPanel.setAlignment(Pos.TOP_LEFT);
        infoPanel.setStyle("-fx-padding: 10px; -fx-background-color: rgba(244, 244, 244, 0.8); -fx-border-color: #ccc;");

        // Add labels for mass, acceleration, and formulas
        Label massLabel = new Label("Mass (m): 0.7 kg");
        Label accelerationLabel = new Label("Acceleration (g): 9.8 m/s²");
        Label formulaLabel = new Label("Projectile Motion Formula:");
        Label rangeFormulaLabel = new Label("Range (R) = (v² * sin(2θ)) / g");

        infoPanel.getChildren().addAll(massLabel, accelerationLabel, formulaLabel, rangeFormulaLabel);

        // Set a default projectile so the ball appears
        Projectile defaultProjectile = new Projectile(0.7, 0, 0, 9.80, -95, 110, screenHeight);
        Player defaultPlayer = new Player(defaultProjectile);
        environment.setPlayer(defaultPlayer);

        // Manually draw the ball at t = 0 (no game loop)
        Pane staticFrame = environment.getProjectilePane(0);
        environment.getGamePane().getChildren().setAll(staticFrame.getChildren());

        // Use the gamePane (the one that will be animated later)
        currentFrame = environment.getGamePane();
        currentFrame.setStyle("-fx-background-color: #eeeeee;");

        // Stack the info panel on top of the image using StackPane
        StackPane imagePaneWithInfo = new StackPane();
        imagePaneWithInfo.getChildren().addAll(imageBox, infoPanel);

        // Create layout with the image and info panel on the left, and game in the center
        HBox centerPane = new HBox();
        centerPane.getChildren().addAll(imagePaneWithInfo, currentFrame);

        BorderPane root = new BorderPane();
        root.setCenter(centerPane);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
