package org.launchandlearn;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImageDisplayAppDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load the first image
        Image image1 = new Image(getClass().getResource("/images/MainMenu.png").toExternalForm());

        ImageView imageView1 = new ImageView(image1);

        // Make the image take the full screen
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
        imageView1.setFitHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());


        // Create the button
        Button startButton = new Button("Click Here to Start!");
        startButton.setStyle("-fx-font-size: 22px; -fx-background-color: white; -fx-border-color: black;");
        startButton.setTranslateY(140); // Move the button up

        // Set button action to launch GameAppUserInput
        startButton.setOnAction(event -> {
            try {
                GameAppUserInput gameApp = new GameAppUserInput();
                gameApp.start(primaryStage); // Reuse the current stage
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Load the settings icon image
        Image settingsIcon = new Image(getClass().getResource("/images/SettingsIcon.png").toExternalForm());
        ImageView settingsImageView = new ImageView(settingsIcon);
        settingsImageView.setFitWidth(30); // Set the icon size
        settingsImageView.setFitHeight(30);
        settingsImageView.setTranslateX(0); // Move the icon 10 pixels from the left edge
        settingsImageView.setTranslateY(0); // Adjust the height as needed

        // Create a white circle behind the settings icon
        Circle settingsCircle = new Circle(20, Color.TRANSPARENT ); // radius of 15
        settingsCircle.setTranslateX(0); // Move the circle to the same position as the icon
        settingsCircle.setTranslateY(0); // Adjust the height as needed


        // Rotate the image when the mouse hovers over it
        settingsCircle.setOnMouseEntered(event -> {
            settingsImageView.setRotate(25);
        });

        settingsCircle.setOnMouseExited(event -> {
            settingsImageView.setRotate(0);
        });

        // Create a root pane
        StackPane settingsPane = new StackPane();
        settingsPane.getChildren().addAll(settingsImageView, settingsCircle);
        StackPane root1 = new StackPane();
        root1.getChildren().addAll(imageView1, startButton, settingsPane);
        StackPane.setAlignment(settingsCircle, Pos.BOTTOM_LEFT); // Position at bottom left
        settingsCircle.setTranslateX(8); // Move 10 pixels to the right
        settingsCircle.setTranslateY(-8); // Move 10 pixels up
        StackPane.setAlignment(settingsImageView, Pos.BOTTOM_LEFT); // Position at bottom left
        settingsImageView.setTranslateX(10); // Move 10 pixels to the right
        settingsImageView.setTranslateY(-10); // Move 10 pixels up

        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(10);

        // Add an event handler to the settings icon
        settingsCircle.setOnMouseClicked(event -> {
            // Apply the blur effect to the root pane, excluding the settings pane
            for (Node node : root1.getChildren()) {
                if (node != settingsPane) {
                    node.setEffect(blur);
                }
            }
        });

        // Create the scene for the first image
        Scene scene1 = new Scene(root1, 1080,
                720);

        // Set up the stage to full screen
        primaryStage.setTitle("Launch and Learn");
        primaryStage.setScene(scene1);
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
