package doodlejump;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Author: Russell

public class PlayerTest {

    private static final int SCREEN_WIDTH = 400;
    private static final double DELTA = 0.001;

    @Test
    void storesInitialPosition() {
        Player player = new Player(100, 300);
        assertEquals(100, player.getWorldX(), DELTA);
        assertEquals(300, player.getWorldY(), DELTA);
    }

    @Test
    void gravityIncreasesVelocityDownward() {
        Player player = new Player(100, 300);
        player.update(false, false, SCREEN_WIDTH);
        assertTrue(player.getVelocityY() > 0);
    }

    @Test
    void playerFallsWhenNoInput() {
        Player player = new Player(100, 300);
        double startY = player.getWorldY();
        for (int i = 0; i < 5; i++) {
            player.update(false, false, SCREEN_WIDTH);
        }
        assertTrue(player.getWorldY() > startY);
    }

    @Test
    void bounceMovesPlayerUpward() {
        Player player = new Player(100, 300);
        double startY = player.getWorldY();
        player.bounce();
        player.update(false, false, SCREEN_WIDTH);
        assertTrue(player.getWorldY() < startY);
    }

    @Test
    void movesLeftWhenLeftPressed() {
        Player player = new Player(100, 300);
        double startX = player.getWorldX();
        player.update(true, false, SCREEN_WIDTH);
        assertTrue(player.getWorldX() < startX);
    }

    @Test
    void movesRightWhenRightPressed() {
        Player player = new Player(100, 300);
        double startX = player.getWorldX();
        player.update(false, true, SCREEN_WIDTH);
        assertTrue(player.getWorldX() > startX);
    }

    @Test
    void wrapsFromLeftEdgeToRight() {
        Player player = new Player(-Player.WIDTH - 1, 300);
        player.update(true, false, SCREEN_WIDTH);
        assertTrue(player.getWorldX() >= SCREEN_WIDTH - Player.WIDTH);
    }

    @Test
    void wrapsFromRightEdgeToLeft() {
        Player player = new Player(SCREEN_WIDTH + 1, 300);
        player.update(false, true, SCREEN_WIDTH);
        assertTrue(player.getWorldX() <= 0);
    }
}
