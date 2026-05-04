package doodlejump;

import edu.macalester.graphics.Rectangle;
import java.awt.Color;

/**
 * A single platform that the player can bounce off of.
 *
 * Author: Russell
 * Acknowledgements: Doodle Jump (Lima Sky, 2009) as the original game reference.
 */
public class Platform {
    public static final double WIDTH = 70;
    public static final double HEIGHT = 12;

    private final double worldX;
    private final double worldY;
    private final Rectangle graphic;

    public Platform(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        graphic = new Rectangle(worldX, worldY, WIDTH, HEIGHT);
        graphic.setFillColor(Color.GREEN);
        graphic.setStroked(false);
    }

    public double getWorldX() {
        return worldX;
    }

    public double getWorldY() {
        return worldY;
    }

    public Rectangle getGraphic() {
        return graphic;
    }

    /** Moves the graphic to its correct screen position based on camera scroll. */
    public void updateScreenPosition(double cameraY) {
        graphic.setPosition(worldX, worldY - cameraY);
    }

    /** Returns true if this platform has scrolled below the visible screen. */
    public boolean isBelowScreen(double cameraY, int screenHeight) {
        return worldY - cameraY > screenHeight + 50;
    }
}
