package org.launchandlearn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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


// Move the button up
        startButton.setTranslateY(140); // Adjust the value as needed


        // Create a root pane
        StackPane root1 = new StackPane();
        root1.getChildren().addAll(imageView1, startButton);

        // Create the scene for the first image
        Scene scene1 = new Scene(root1, javafx.stage.Screen.getPrimary().getBounds().getWidth(),
                javafx.stage.Screen.getPrimary().getBounds().getHeight());



        // Set up the stage to full screen
        primaryStage.setTitle("Launch and Learn");
        primaryStage.setScene(scene1);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
