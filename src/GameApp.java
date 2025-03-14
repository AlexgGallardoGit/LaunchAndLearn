import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameApp extends Application {

    private Pane currentFrame;
    private static final double TARGET_FPS = 120;
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
        Projectile test = new Projectile(0.7, 800, 70, 9.80, 40, 30);

        Player testPlayer = new Player(test);

        environment.setPlayer(testPlayer);
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private long startTime = 0;

            @Override
            public void handle(long now) {
                if (startTime == 0) startTime = now; // Capture the start time

                // Ensure we wait for the correct frame time
                if (now - lastUpdate >= FRAME_TIME) {
                    double elapsedTime = (now - startTime) / 1_000_000_000.0; // Elapsed time in seconds

                    Pane newFrame = generatePane(elapsedTime);
                    currentFrame.getChildren().setAll(newFrame.getChildren());

                    lastUpdate = now; // Update the last frame time
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
