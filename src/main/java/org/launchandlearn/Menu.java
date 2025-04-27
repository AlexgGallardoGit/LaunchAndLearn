package org.launchandlearn;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Application {
    private int nextLevel = 1;
    private double width;
    private double height;
    private boolean isFullScreen;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        show(stage, "A", 1);
    }

    public void show(Stage stage, String grade, int nextLevelNumber) {
        this.nextLevel = nextLevelNumber;
        
        // Preserve current stage dimensions and fullscreen state
        this.width = stage.getWidth();
        this.height = stage.getHeight();
        this.isFullScreen = stage.isFullScreen();
        
        // Set default size if not set
        if (width == 0 || height == 0) {
            width = 1080;
            height = 720;
            isFullScreen = false;
        }

        // Create a smaller scene for the menu overlay
        double menuWidth = width * 0.4;
        double menuHeight = height * 0.4;

        // Grade display label
        Label gradeLabel = new Label("Your Grade: " + grade);
        gradeLabel.setStyle("-fx-font-size: 45px; -fx-text-fill: darkgreen;");

        // Level display
        Label levelLabel = new Label("Level " + (nextLevel - 1) + " Complete!");
        levelLabel.setStyle("-fx-font-size: 35px; -fx-text-fill: darkblue;");

        // Next button
        Button nextButton = new Button("Next Level");
        nextButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");

        // Exit button
        Button exitButton = new Button("Exit to Menu");
        exitButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");

        // Layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(levelLabel, gradeLabel, nextButton, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 10;");

        // Create a semi-transparent overlay
        StackPane overlay = new StackPane(layout);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Scene
        Scene menuScene = new Scene(overlay, width, height);
        stage.setScene(menuScene);
        
        // Apply fullscreen if needed
        if (isFullScreen) {
            stage.setFullScreen(true);
        }

        // Button Actions with Fade-out effect
        nextButton.setOnAction(e -> {
            try {
                GameApp gameApp = new GameApp();
                gameApp.setStartLevel(nextLevel);
                gameApp.setScreenDimensions(width, height, isFullScreen);
                gameApp.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        exitButton.setOnAction(e -> {
            try {
                MainMenu menu = new MainMenu();
                menu.setWidth(width);
                menu.setHeight(height);
                menu.setFullScreen(isFullScreen);
                menu.setStartLevel(nextLevel);
                menu.start(stage);
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
