import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("Launching JavaFX application...");
        launch(args); // Properly initializes the JavaFX environment
    }

    @Override
    public void start(Stage stage) {

        Environment environment = new Environment(2, 3, 1080, 720);
        BorderPane borderPane = environment.getEnvironmentPane();

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 1080, 720);

        stage.setTitle("RandomStructures"); // Set the stage title
        stage.setScene(scene);    // Place the scene in the stage

        stage.show();            // Display the stage
    }
}
