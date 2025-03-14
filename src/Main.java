import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("Launching JavaFX application...");
        launch(args); // Properly initializes the JavaFX environment
    }

    @Override
    public void start(Stage stage) {

        Environment environment = new Environment(5, 5,1080, 720);

        // Create the projectile class
        Projectile test = new Projectile(1, 300, 45, 9.80, 40, 30);

        Player testPlayer = new Player(test);

        environment.setPlayer(testPlayer);


        Pane pane = environment.getProjectilePane(2.7);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 1080, 720);

        stage.setTitle("RandomStructures"); // Set the stage title
        stage.setScene(scene);    // Place the scene in the stage

        stage.show();            // Display the stage
    }
}
