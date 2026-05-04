package doodlejump;

import edu.macalester.graphics.Rectangle;
import java.awt.Color;

/**
 * The player character. Manages physics (gravity, velocity) and horizontal movement.
 *
 * Author: Russell
 * Acknowledgements: Doodle Jump (Lima Sky, 2009) as the original game reference.
 */
public class Player {
    public static final double WIDTH = 30;
    public static final double HEIGHT = 30;

    private static final double GRAVITY = 0.4;
    private static final double JUMP_VELOCITY = -13.0;
    private static final double MOVE_SPEED = 5.0;

    private double worldX;
    private double worldY;
    private double velocityX;
    private double velocityY;
    private final Rectangle graphic;

    public Player(double startX, double startY) {
        worldX = startX;
        worldY = startY;
        velocityX = 0;
        velocityY = 0;
        graphic = new Rectangle(startX, startY, WIDTH, HEIGHT);
        graphic.setFillColor(Color.BLUE);
        graphic.setStroked(false);
    }

    /**
     * Advances physics one frame. Applies gravity, moves the player, and wraps
     * horizontally around the screen edges.
     */
    public void update(boolean leftPressed, boolean rightPressed, int screenWidth) {
        if (leftPressed) {
            velocityX = -MOVE_SPEED;
        } else if (rightPressed) {
            velocityX = MOVE_SPEED;
        } else {
            velocityX = 0;
        }

        velocityY += GRAVITY;
        worldX += velocityX;
        worldY += velocityY;

        if (worldX + WIDTH < 0) {
            worldX = screenWidth;
        } else if (worldX > screenWidth) {
            worldX = -WIDTH;
        }
    }

    /** Launches the player upward — called on platform collision. */
    public void bounce() {
        velocityY = JUMP_VELOCITY;
    }

    /** Moves the graphic to its correct screen position based on camera scroll. */
    public void updateScreenPosition(double cameraY) {
        graphic.setPosition(worldX, worldY - cameraY);
    }

    public double getWorldX() {
        return worldX;
    }

    public double getWorldY() {
        return worldY;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public Rectangle getGraphic() {
        return graphic;
    }
}
