package org.launchandlearn;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        show(stage, "A");
    }

    public void show(Stage stage, String grade) {
        // Grade display label
        Label gradeLabel = new Label("Your Grade: " + grade);
        gradeLabel.setStyle("-fx-font-size: 45px; -fx-text-fill: darkgreen;");


        // Next button
        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");
        nextButton.setOnAction(e -> {
            try {
                GameAppUserInput gameApp = new GameAppUserInput();
                gameApp.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Restart button
        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");
        restartButton.setOnAction(e -> {
            // Placeholder for future logic
            System.out.println("Next level coming soon!");
        });

        // Exit button
        Button exitButton = new Button("Exit to Menu");
        exitButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");
        exitButton.setOnAction(e -> {
            try {
                ImageDisplayAppDemo menu = new ImageDisplayAppDemo();
                menu.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Layout
        VBox layout = new VBox(30);
        layout.getChildren().addAll(gradeLabel, nextButton, restartButton,exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: white;");

        // Scene setup
        Scene endScene = new Scene(layout, 520, 350);
        stage.setScene(endScene);
        stage.setTitle("Menu");
        stage.show();
    }
}
