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

public class GameAppUserInput extends Application {

    private Pane currentFrame;
    private static final double TARGET_FPS = 120;
    private static final double FRAME_TIME = 1_000_000_000 / TARGET_FPS;
    public Environment environment;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        double screenWidth = 1080;
        double screenHeight = 720;

        environment = new Environment(3, 5, screenWidth, screenHeight);

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
                startCustomGameLoop();
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

        VBox infoPanel = new VBox(10);
        infoPanel.setAlignment(Pos.TOP_LEFT);
        infoPanel.setStyle("-fx-padding: 10px; -fx-background-color: rgba(244, 244, 244, 0.8); -fx-border-color: #ccc;");

        Label massLabel = new Label("Mass (m): 0.7 kg");
        Label accelerationLabel = new Label("Acceleration (g): 9.8 m/s²");
        Label formulaLabel = new Label("Projectile Motion Formula:");
        Label rangeFormulaLabel = new Label("Range (R) = (v² * sin(2θ)) / g");

        infoPanel.getChildren().addAll(massLabel, accelerationLabel, formulaLabel, rangeFormulaLabel);

        Projectile defaultProjectile = new Projectile(0.7, 0, 0, 9.80, -95, 110, screenHeight);
        Player defaultPlayer = new Player(defaultProjectile);
        environment.setPlayer(defaultPlayer);

        Pane staticFrame = environment.getProjectilePane(0);
        environment.getGamePane().getChildren().setAll(staticFrame.getChildren());

        currentFrame = environment.getGamePane();
        currentFrame.setStyle("-fx-background-color: #eeeeee;");

        HBox centerPane = new HBox();
        centerPane.getChildren().addAll(infoPanel, currentFrame);

        BorderPane root = new BorderPane();
        root.setCenter(centerPane);
        root.setBottom(bottomPane);

        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startCustomGameLoop() {
        javafx.animation.AnimationTimer gameLoop = new javafx.animation.AnimationTimer() {
            private long lastUpdate = 0;
            private long startTime = 0;
            private double previousElapsedTime = 0;

            @Override
            public void handle(long now) {
                if (startTime == 0) startTime = now;

                if (now - lastUpdate >= FRAME_TIME) {
                    double elapsedTime = (now - startTime) / 1_000_000_000.0;

                    boolean collisionDetected = false;
                    double checkInterval = 0.001;
                    double simulatedTime = previousElapsedTime;

                    while (simulatedTime < elapsedTime) {
                        simulatedTime += checkInterval;
                        int currentGameState = environment.getCurrentGameState(simulatedTime);

                        if (currentGameState != 1) {
                            collisionDetected = true;
                            Pane collisionFrame = environment.getProjectilePane(simulatedTime);
                            environment.getGamePane().getChildren().setAll(collisionFrame.getChildren());

                            this.stop();

                            if (currentGameState == 2) { // Hit new target
                                int remaining = 0;
                                for (Target t : environment.getTarget()) {
                                    if (!t.getIsHit()) remaining++;
                                }
                                if (remaining == 0) {
                                    GameControlPanel panel = new GameControlPanel(environment, primaryStage);
                                    panel.showPanel();
                                } else {
                                    Projectile resetProjectile = new Projectile(0.7, 0, 0, 9.80, -95, 110, 720);
                                    Player resetPlayer = new Player(resetProjectile);
                                    environment.setPlayer(resetPlayer);
                                    Pane resetFrame = environment.getProjectilePane(0);
                                    environment.getGamePane().getChildren().setAll(resetFrame.getChildren());
                                }
                            }
                            break;
                        }
                    }

                    if (!collisionDetected) {
                        Pane newFrame = environment.getProjectilePane(elapsedTime);
                        environment.getGamePane().getChildren().setAll(newFrame.getChildren());
                    }

                    previousElapsedTime = elapsedTime;
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }
}
