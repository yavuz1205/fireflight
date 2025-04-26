import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Represents the player character: the Dragon.
 * Handles movement and sprite changes based on direction.
 */

public class Dragon extends ImageView {
    private final double SPEED = 5; // Pixels per frame

    // Load images
    private final Image left = new Image("resources/dragon/dragon_left.png");
    private final Image right = new Image("resources/dragon/dragon_right.png");

    private final int NUM_FRAMES = 12;
    private final Image[] flyImages = new Image[NUM_FRAMES];
    private int currentFrame = 0;


    public Dragon() {
        // Initial appearance and size
        setFitWidth(64);
        setFitHeight(64);
        setSmooth(false); // Makes it look sharper

        // Load flying animation frames
        for (int i = 0; i < NUM_FRAMES; i++) {
            flyImages[i] = new Image("resources/dragon/fly" + i + ".png");
        }

        // Start the idle flying animation
        startFlyAnimation();
    }


    private void startFlyAnimation() {
         Timeline flyAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            setImage(flyImages[currentFrame]);
            currentFrame = (currentFrame + 1) % NUM_FRAMES;
        }));
        flyAnimation.setCycleCount(Timeline.INDEFINITE);
        flyAnimation.play();
    }

    public void moveLeft() {
        setImage(left); // Show left-facing sprite
        double newX = getLayoutX() - SPEED;
        // Keep inside left screen boundary
        setLayoutX(Math.max(0, newX));
    }

    public void moveRight() {
        setImage(right); // Show right-facing sprite
        double newX = getLayoutX() + SPEED;
        double maxX = getScene().getWidth() - getFitWidth();
        // Keep inside right screen boundary
        setLayoutX(Math.min(newX, maxX));
    }

    // Shoot fireball when space is pressed
    public void shootFireball() {
        double fireballWidth = 64;

        // getLayoutX() gives X position of dragon (top-left corner)
        // getFitWidth() / 2 gives half of the width of the dragon
        // getLayoutX() + getFitWidth() / 2 -> X coordinate of center of the dragon
        // fireballWidth / 2 gives half of width of fireball
        // -> center of the fireball is placed at the center of the dragon.
        double startX = getLayoutX() + getFitWidth() / 2 - fireballWidth / 2;

        double startY = getLayoutY() - 32; // slightly above the dragon
        Fireball fireball = new Fireball(startX, startY);
        ((Pane) getScene().getRoot()).getChildren().add(fireball); // Add fireball to the scene
    }


    //----------------------------
    //for later:
    private int maxHealth = 100;      // Max health
    private int health = 100;         // Current health
    private int hearts = 3;           // Number of lives (hearts)
    private boolean isAlive = true;   // For quick status check
    public int getHealth() { return health; }
    public int getHearts() { return hearts; }
    public boolean isAlive() { return isAlive; }
    public void takeDamage(int amount) {
        if (!isAlive) return;

        health -= amount;
        if (health <= 0) {
            hearts--;
            if (hearts > 0) {
                health = maxHealth; // Reset health for next heart
            } else {
                health = 0;
                isAlive = false;
                // trigger game over
            }
        }
    }
    //----------------------------
}


