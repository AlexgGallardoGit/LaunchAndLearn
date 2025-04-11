
package org.launchandlearn;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameControlPanel extends Application {

    private Environment environment;
    private Stage primaryStage;

    public GameControlPanel() {
        this.environment = new Environment(3, 5, 1080, 720);
    }

    public GameControlPanel(Environment environment, Stage primaryStage) {
        this.environment = environment;
        this.primaryStage = primaryStage;
    }

    public BorderPane createControlPane() {
        BorderPane controlPane = new BorderPane();

        // Title or status label
        Label statusLabel = new Label("Game Control Panel");
        statusLabel.setStyle("-fx-font-size: 20px; -fx-padding: 10px;");

        // Grade label
        Label gradeLabel = new Label("Grade: N/A");
        gradeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #333;");

        // Action Buttons
        Button restartButton = new Button("Restart");
        Button exitButton = new Button("Exit");
        Button nextButton = new Button("Next");

        restartButton.setOnAction(e -> {
            Environment newEnv = new Environment(3, 5, 1080, 720);
            Projectile defaultProjectile = new Projectile(0.7, 0, 0, 9.80, -95, 110, 720);
            Player player = new Player(defaultProjectile);
            newEnv.setPlayer(player);
            this.environment = newEnv;
            gradeLabel.setText("Grade: N/A");
            newEnv.startGameLoop();
        });

        exitButton.setOnAction(e -> {
            try {
                new ImageDisplayAppDemo().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        nextButton.setOnAction(e -> {
            int remaining = 0;
            for (Target t : environment.getTarget()) {
                if (!t.getIsHit()) remaining++;
            }

            if (remaining == 0) {
                gradeLabel.setText("Grade: " + calculateGrade());
            } else {
                Label warning = new Label("You must hit all targets first.");
                warning.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                VBox center = new VBox(statusLabel, warning);
                center.setAlignment(Pos.CENTER);
                controlPane.setCenter(center);
                return;
            }

            // Load new structure layout
            Environment newEnv = new Environment(3, 5, 1080, 720);
            Projectile defaultProjectile = new Projectile(0.7, 0, 0, 9.80, -95, 110, 720);
            Player player = new Player(defaultProjectile);
            newEnv.setPlayer(player);
            this.environment = newEnv;
            newEnv.startGameLoop();
        });

        HBox buttonBox = new HBox(20, restartButton, nextButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        VBox topBox = new VBox(statusLabel, gradeLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setSpacing(10);

        controlPane.setTop(topBox);
        controlPane.setCenter(buttonBox);
        return controlPane;
    }

    private String calculateGrade() {
        return "A"; // Placeholder for grading logic
    }

    public void showPanel() {
        BorderPane root = createControlPane();
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Control Panel");
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showPanel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
