package doodlejump;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Author: Russell

public class PlatformTest {

    @Test
    void storesWorldPosition() {
        Platform p = new Platform(100, 200);
        assertEquals(100, p.getWorldX());
        assertEquals(200, p.getWorldY());
    }

    @Test
    void isNotBelowScreenWhenOnScreen() {
        Platform p = new Platform(50, 300);
        assertFalse(p.isBelowScreen(0, 600));
    }

    @Test
    void isBelowScreenWhenScrolledOff() {
        Platform p = new Platform(50, 300);
        assertTrue(p.isBelowScreen(-400, 600));
    }
}
