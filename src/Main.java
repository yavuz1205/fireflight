import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry point of the game.
 * Launches the JavaFX application and starts the Game.
 */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game(); // Create an instance of the game
        game.start(primaryStage); // Start the game
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX
    }
}
