package gameLogic;

import character.Dragon;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

/**
 * The main game logic class.
 * Sets up the scene, handles input, game loop, and manages the dragon.
 */

public class Game {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private Set<KeyCode> pressedKeys = new HashSet<>(); // Stores currently pressed keys
    private Dragon dragon;
    private Pane root;

    public void start(Stage stage) {
        // Set up the root container and scene
        root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Create and add the dragon
        dragon = new Dragon();
        dragon.setLayoutX(WIDTH / 2.0 - 16);  // Start near horizontal center
        dragon.setLayoutY(HEIGHT - 64);       // Initial bottom placement
        root.getChildren().add(dragon);

        // Position dragon at bottom on start
        dragon.setLayoutY(scene.getHeight() - dragon.getFitHeight() - 20); // 20px padding


        // Key input handling
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));


        // Listener to reposition Y when window is resized
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newY = newVal.doubleValue() - dragon.getFitHeight() - 20;
            if (newY < 0) newY = 0; // prevent negative Y
            dragon.setLayoutY(newY);
        });

        // Listener to reposition X when window is resized
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double maxX = newVal.doubleValue() - dragon.getFitWidth();
            if (dragon.getLayoutX() > maxX) {
                dragon.setLayoutX(Math.max(0, maxX)); // keep it within bounds
            }
        });


        // Load background image
        Image background = new Image("resources/background/skyBackground.png");  // First background image
        BackgroundImage backgroundImage = new BackgroundImage(background,
                BackgroundRepeat.REPEAT,  // Repeat horizontally
                BackgroundRepeat.REPEAT,  // Repeat vertically
                null, null);  // No specific alignment or sizing
        root.setBackground(new Background(backgroundImage));  // Apply the background to the pane


        // gameLogic.Game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(); // Call update logic every frame
            }
        };

        timer.start();

        // Show the game window
        stage.setScene(scene);
        stage.setTitle("Fire Flight");
        stage.show();
    }


    /**
     * Called every frame by AnimationTimer.
     * Handles movement and direction based on key input.
     */

    private boolean spacePressed = false;

    private void update() {
        boolean left = pressedKeys.contains(KeyCode.A);
        boolean right = pressedKeys.contains(KeyCode.D);
        boolean shoot = pressedKeys.contains(KeyCode.SPACE); // Check if space is pressed

        // Movement logic: if both A and D are held → stop and face forward
        if (left && right) {
            // cancel out
        } else if (left) {
            dragon.moveLeft();
        } else if (right) {
            dragon.moveRight();
        }

        if (shoot) {
            if (!spacePressed) {
                dragon.shootFireball();
                spacePressed = true; // Mark that we already handled this press
            }
        } else {
            spacePressed = false; // Reset when space is released
        }

    }
}




