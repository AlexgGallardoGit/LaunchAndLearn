package org.launchandlearn;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainMenu extends Application {
    private double width = Screen.getPrimary().getBounds().getWidth();
    private double height = Screen.getPrimary().getBounds().getHeight();
    private boolean isFullScreen = true;
    private static BackgroundMusic backgroundMusic;
    private int startLevel = 1;

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setFullScreen(boolean fullScreen) {
        this.isFullScreen = fullScreen;
    }

    public void setStartLevel(int level) {
        this.startLevel = level;
    }

    @Override
    public void start(Stage primaryStage) {
        if (backgroundMusic != null) {
            backgroundMusic.stop(); // Stop any previous music
        }
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.play();

        // If stage has valid dimensions, use them
        if (primaryStage.getWidth() > 0 && primaryStage.getHeight() > 0) {
            width = primaryStage.getWidth();
            height = primaryStage.getHeight();
            isFullScreen = primaryStage.isFullScreen();
        }

        // Load the background image
        Image image1 = new Image(getClass().getResource("/images/MainMenu.png").toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setPreserveRatio(true);
        updateImageViewSize(imageView1);

        // Start button
        Button startButton = new Button("Click Here to Start!");
        startButton.setStyle("-fx-font-size: 22px; -fx-background-color: white; -fx-border-color: black;");
        startButton.setTranslateY(140);
        startButton.setOnAction(event -> {
            GameApp gameApp = new GameApp();
            gameApp.setScreenDimensions(width, height, isFullScreen);
            gameApp.setStartLevel(startLevel);
            try {
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
        Slider musicSlider = new Slider(0, 1, BackgroundMusic.getCurrentVolume());  
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            backgroundMusic.setVolume(newValue.doubleValue());
        });

        Label iconLabel = new Label("Sound Effects Volume");
        Slider iconSlider = new Slider(0, 100, 100); // Default set to 100
        // Set initial sound effect volume
        SoundEffects.setVolume(iconSlider.getValue() / 100.0);
        iconSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            SoundEffects.setVolume(newValue.doubleValue() / 100.0);
        });

        Label resolutionLabel = new Label("Screen Resolution");
        ComboBox<String> resolutionBox = new ComboBox<>();
        resolutionBox.getItems().addAll("1080x720", "1280x720", "1920x1080", "2560x1440", "Full Screen");
        resolutionBox.setValue(getCurrentResolutionString());

        // Adjust screen resolution when selected
        resolutionBox.setOnAction(e -> {
            String selected = resolutionBox.getValue();
            updateScreenDimensions(selected);
            applyScreenSettings(primaryStage, imageView1);
            
            // Store the new dimensions for other classes to use
            width = primaryStage.getWidth();
            height = primaryStage.getHeight();
            isFullScreen = primaryStage.isFullScreen();
        });

        Button closeButton = new Button("Close Settings");
        closeButton.setOnAction(e -> {
            settingsMenu.setVisible(false);
            for (Node node : root1.getChildren()) {
                if (node != settingsMenu) {
                    node.setEffect(null);
                }
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

        // Settings icon click handler
        settingsCircle.setOnMouseClicked(event -> {
            settingsMenu.setVisible(!settingsMenu.isVisible());
            for (Node node : root1.getChildren()) {
                if (node != settingsMenu) {
                    node.setEffect(settingsMenu.isVisible() ? blur : null);
                }
            }
        });

        // --- Exit Button ---
        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 18px; -fx-background-color: #ff5555; -fx-text-fill: white; -fx-border-color: black;");
        exitButton.setOnAction(e -> System.exit(0));

        // Position exit button bottom right
        StackPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);
        exitButton.setTranslateX(-20); // Padding from right
        exitButton.setTranslateY(-20); // Padding from bottom

        root1.getChildren().add(exitButton);

        // Create scene and configure stage
        Scene scene1 = new Scene(root1);
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Launch and Learn");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        
        // Apply screen settings and ensure fullscreen is set before showing
        applyScreenSettings(primaryStage, imageView1);
        if (isFullScreen) {
            primaryStage.setFullScreen(true);
        }
        
        // Display the stage
        primaryStage.show();

        // Lock the primary stage to prevent resizing
        primaryStage.setResizable(false);
    }

    private void updateScreenDimensions(String resolution) {
        if ("Full Screen".equals(resolution)) {
            width = Screen.getPrimary().getBounds().getWidth();
            height = Screen.getPrimary().getBounds().getHeight();
            isFullScreen = true;
        } else {
            String[] parts = resolution.split("x");
            width = Integer.parseInt(parts[0]);
            height = Integer.parseInt(parts[1]);
            isFullScreen = false;
        }
    }

    private void updateImageViewSize(ImageView imageView) {
        if (isFullScreen) {
            imageView.setFitWidth(Screen.getPrimary().getBounds().getWidth());
            imageView.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        } else {
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
        }
    }

    private void applyScreenSettings(Stage stage, ImageView imageView) {
        stage.setFullScreen(false); // Temporarily disable fullscreen
        stage.setWidth(width);
        stage.setHeight(height);
        updateImageViewSize(imageView);
        stage.setResizable(false);
        if (isFullScreen) {
            stage.setFullScreen(true);
        }
    }

    private String getCurrentResolutionString() {
        if (isFullScreen) {
            return "Full Screen";
        }
        return String.format("%.0fx%.0f", width, height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
