package org.launchandlearn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;

public class ImageDisplayAppDemo extends Application {
    @Override

    public void start(Stage primaryStage) {
        // Load the first image
        File file1 = new File("C:\\Users\\nancy\\OneDrive\\Computer Science1\\src\\main\\java\\org\\launchandlearn\\Picture 1.png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView imageView1 = new ImageView(image1);

        // Make the image take the full screen
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
        imageView1.setFitHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());

        // Create the button
        Button startButton = new Button("Click Here to Start!");
        startButton.setStyle("-fx-font-size: 22px; -fx-background-color: white; -fx-border-color: white;");


// Move the button up
        startButton.setTranslateY(-15); // Adjust the value as needed


        // Create a root pane
        StackPane root1 = new StackPane();
        root1.getChildren().addAll(imageView1, startButton);

        // Create the scene for the first image
        Scene scene1 = new Scene(root1, javafx.stage.Screen.getPrimary().getBounds().getWidth(),
                javafx.stage.Screen.getPrimary().getBounds().getHeight());

        // Load the second image
        File file2 = new File("C:\\Users\\nancy\\OneDrive\\Computer Science1\\src\\main\\java\\org\\launchandlearn\\Picturedemo.png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView imageView2 = new ImageView(image2);

        // Make the second image take the full screen
        imageView2.setPreserveRatio(true);
        imageView2.setFitWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
        imageView2.setFitHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());

        // Create a root pane for the second image
        StackPane root2 = new StackPane();
        root2.getChildren().add(imageView2);

        // Create the scene for the second image
        Scene scene2 = new Scene(root2, javafx.stage.Screen.getPrimary().getBounds().getWidth(),
                javafx.stage.Screen.getPrimary().getBounds().getHeight());

        // Set button action to switch to the second image
        startButton.setOnAction(e -> primaryStage.setScene(scene2));

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
