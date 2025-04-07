package org.launchandlearn;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImageDisplayAppDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load the background image
        Image image1 = new Image(getClass().getResource("/images/MainMenu.png").toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        imageView1.setFitHeight(Screen.getPrimary().getBounds().getHeight());

        // Start button
        Button startButton = new Button("Click Here to Start!");
        startButton.setStyle("-fx-font-size: 22px; -fx-background-color: white; -fx-border-color: black;");
        startButton.setTranslateY(140);
        startButton.setOnAction(event -> {
            try {
                GameAppUserInput gameApp = new GameAppUserInput();
                gameApp.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Settings icon
        Image settingsIcon = new Image(getClass().getResource("/images/SettingsIcon.png").toExternalForm());
        ImageView settingsImageView = new ImageView(settingsIcon);
        settingsImageView.setFitWidth(30);
        settingsImageView.setFitHeight(30);
        settingsImageView.setTranslateX(10);
        settingsImageView.setTranslateY(-10);

        Circle settingsCircle = new Circle(20, Color.TRANSPARENT);
        settingsCircle.setTranslateX(8);
        settingsCircle.setTranslateY(-8);

        settingsCircle.setOnMouseEntered(event -> settingsImageView.setRotate(25));
        settingsCircle.setOnMouseExited(event -> settingsImageView.setRotate(0));

        // Settings pane containing icon and circle
        StackPane settingsPane = new StackPane(settingsImageView, settingsCircle);
        StackPane.setAlignment(settingsCircle, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(settingsImageView, Pos.BOTTOM_LEFT);

        // Root layout
        StackPane root1 = new StackPane(imageView1, startButton, settingsPane);
        startButton.toFront();

        // Blur effect
        GaussianBlur blur = new GaussianBlur(10);

        // Settings menu UI
        VBox settingsMenu = new VBox(15);
        settingsMenu.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-padding: 20px; -fx-background-radius: 10;");
        settingsMenu.setAlignment(Pos.CENTER);
        settingsMenu.setVisible(false);

        // Settings menu controls
        Label musicLabel = new Label("Music Volume");
        Slider musicSlider = new Slider(0, 100, 50);

        Label iconLabel = new Label("Icon Volume");
        Slider iconSlider = new Slider(0, 100, 50);

        Label resolutionLabel = new Label("Screen Resolution");
        ComboBox<String> resolutionBox = new ComboBox<>();
        resolutionBox.getItems().addAll("1080x720", "1280x720", "1920x1080", "2560x1440", "Full Screen");
        resolutionBox.setValue("Full Screen");

        // Optional: Adjust screen resolution when selected
        resolutionBox.setOnAction(e -> {
            String selected = resolutionBox.getValue();
            if (selected.equals("Full Screen")) {
                primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
                primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());
                primaryStage.setFullScreenExitHint("");
                primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                primaryStage.setFullScreen(true);
            } else {
                String[] parts = selected.split("x");
                int width = Integer.parseInt(parts[0]);
                int height = Integer.parseInt(parts[1]);
                primaryStage.setFullScreen(false);
                primaryStage.setWidth(width);
                primaryStage.setHeight(height);
            }
        });

        Button closeButton = new Button("Close Settings");
        closeButton.setOnAction(e -> {
            settingsMenu.setVisible(false);
            for (Node node : root1.getChildren()) {
                node.setEffect(null);
            }
        });

        settingsMenu.getChildren().addAll(
                musicLabel, musicSlider,
                iconLabel, iconSlider,
                resolutionLabel, resolutionBox,
                closeButton
        );

        // Add settings menu to root and center it
        root1.getChildren().add(settingsMenu);
        StackPane.setAlignment(settingsMenu, Pos.CENTER);

        // Show settings menu on icon click
        settingsCircle.setOnMouseClicked(event -> {
            settingsMenu.setVisible(true);
            for (Node node : root1.getChildren()) {
                if (node != settingsMenu && node != settingsPane) {
                    node.setEffect(blur);
                }
            }
        });

        // Final scene and stage setup
        Scene scene1 = new Scene(root1, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Launch and Learn");
        primaryStage.setScene(scene1);
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
