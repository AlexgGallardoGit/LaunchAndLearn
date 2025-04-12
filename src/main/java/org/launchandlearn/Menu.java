package org.launchandlearn;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        show(stage, "A");
    }

    public void show(Stage stage, String grade) {
        // Screen size variables
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // Fullscreen toggle
        boolean fullscreen = true;

        // Grade display label
        Label gradeLabel = new Label("Your Grade: " + grade);
        gradeLabel.setStyle("-fx-font-size: 45px; -fx-text-fill: darkgreen;");

        // Next button
        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");

        // Exit button
        Button exitButton = new Button("Exit to Menu");
        exitButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");

        // Layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(gradeLabel, nextButton, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: white;");

        // Scene
        Scene endScene = new Scene(layout, screenWidth * 0.4, screenHeight * 0.4);
        stage.setScene(endScene);
        stage.setTitle("Menu");
        stage.setFullScreen(fullscreen);
        stage.show();

        // Fade-in when scene loads
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), layout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Button Actions with Fade-out effect
        nextButton.setOnAction(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.4), layout);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(ev -> {
                try {
                    GameAppUserInput gameApp = new GameAppUserInput();
                    gameApp.start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            fadeOut.play();
        });

        exitButton.setOnAction(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.4), layout);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(ev -> {
                try {
                    ImageDisplayAppDemo menu = new ImageDisplayAppDemo();
                    menu.start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            fadeOut.play();
        });
    }
}
