import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameApp extends Application {

    private Pane currentFrame;
    private static final double TARGET_FPS = 60;
    private static final double FRAME_TIME = 1_000_000_000 / TARGET_FPS; // Time per frame in nanoseconds
    public Environment environment;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        currentFrame = new Pane();

        // Get screen dimensions
        double screenWidth = 1080;
        double screenHeight = 720;
        Scene scene = new Scene(currentFrame, screenWidth, screenHeight);
        primaryStage.setScene(scene);

        primaryStage.show();
        startGameLoop();

        environment = new Environment(5, 5,1080, 720);

        // Create the projectile class
        Projectile test = new Projectile(1, 100, 50, 9.80, 40, 30);

        Player testPlayer = new Player(test);

        environment.setPlayer(testPlayer);
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            int i = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= FRAME_TIME) {
                    long timeMillis = now / 1_000_000;

                    Pane newFrame = generatePane(i*6/TARGET_FPS);
                    currentFrame.getChildren().setAll(newFrame.getChildren());

                    lastUpdate = now;
                    i++;
                }
            }
        };

        gameLoop.start();
    }

    // Example pane: Moving circle animation
    private Pane generatePane(double seconds) {
        return environment.getProjectilePane(seconds);
    }
}
