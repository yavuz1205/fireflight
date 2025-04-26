import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Represents a fireball shot by the Dragon.
 * Animates through a series of images and moves upward until it leaves the screen.
 */

public class Fireball extends ImageView {
    private int currentFrame = 0;  // To track current frame in animation
    private static final int NUM_FRAMES = 4; // fire0.png to fire3.png for looping
    private final double SPEED = 10;  // Fireball speed

    private final Image[] fireballImages = new Image[NUM_FRAMES];
    private Timeline animationTimeline; // Controls sprite animation

    public Fireball(double startX, double startY) {
        // Load only fire0 to fire3
        for (int i = 0; i < NUM_FRAMES; i++) {
            fireballImages[i] = new Image("resources/fireball/fire" + i + ".png");
        }

        // Set initial image and position
        setImage(fireballImages[currentFrame]);
        setFitWidth(64);
        setFitHeight(64);
        setLayoutX(startX);
        setLayoutY(startY);

        moveUp();        // Start movement
        startAnimation(); // Start looping animation
    }

    /**
     * Moves the fireball upward until it leaves the top of the window.
     */

    private void moveUp() {
        Timeline moveTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.05), e -> {
                    setLayoutY(getLayoutY() - SPEED);

                    // Remove fireball when off-screen (top)
                    if (getLayoutY() + getFitHeight() < 0) {
                        animationTimeline.stop(); // Stop sprite animation
                        if (getParent() instanceof Pane pane) {
                            pane.getChildren().remove(this);
                            System.out.println("Fireball removed! Parent after removal: " + getParent()); // Should print null, checking !!!!
                        }
                    }
                })
        );
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();
    }

    /**
     * Starts looping fire0.png to fire3.png for flying animation.
     */

    private void startAnimation() {
        animationTimeline = new Timeline(
                new KeyFrame(Duration.millis(100), e -> updateFireball())
        );
        animationTimeline.setCycleCount(Timeline.INDEFINITE); // Loop forever
        animationTimeline.play();
    }

    /**
     * Updates fireball image by looping through frames 0-3.
     */

    private void updateFireball() {
        currentFrame = (currentFrame + 1) % NUM_FRAMES;
        setImage(fireballImages[currentFrame]);
    }
}
