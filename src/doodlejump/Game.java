package doodlejump;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.events.Key;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sets up the canvas and runs the main game loop. Manages all platforms,
 * the player, camera scrolling, and score.
 *
 * Author: Russell
 * Acknowledgements: Doodle Jump (Lima Sky, 2009) as the original game reference.
 */
public class Game {
    static final int WIDTH = 400;
    static final int HEIGHT = 600;

    private static final int INITIAL_PLATFORM_COUNT = 12;
    private static final double INITIAL_PLATFORM_SPACING = 70;
    private static final double MIN_GAP = 50;
    private static final double MAX_GAP = 90;

    private final CanvasWindow canvas;
    private Player player;
    private final List<Platform> platforms = new ArrayList<>();
    private GraphicsText scoreText;
    private GraphicsText gameOverText;

    private double cameraY = 0;
    private double highestWorldY;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean gameOver = false;

    public Game() {
        canvas = new CanvasWindow("Doodle Jump", WIDTH, HEIGHT);
        canvas.setBackground(new Color(135, 206, 235));

        setupInitialPlatforms();
        setupPlayer();
        setupHUD();
        setupInput();

        canvas.animate(() -> update());
    }

    private void setupInitialPlatforms() {
        double y = HEIGHT - 80;
        // First platform centered so the player always has a safe start
        platforms.add(new Platform(WIDTH / 2.0 - Platform.WIDTH / 2.0, y));
        canvas.add(platforms.get(0).getGraphic());

        for (int i = 1; i < INITIAL_PLATFORM_COUNT; i++) {
            y -= INITIAL_PLATFORM_SPACING;
            double x = Math.random() * (WIDTH - Platform.WIDTH);
            Platform p = new Platform(x, y);
            platforms.add(p);
            canvas.add(p.getGraphic());
        }
    }

    private void setupPlayer() {
        Platform start = platforms.get(0);
        double px = start.getWorldX() + Platform.WIDTH / 2.0 - Player.WIDTH / 2.0;
        double py = start.getWorldY() - Player.HEIGHT;
        player = new Player(px, py);
        highestWorldY = py;
        canvas.add(player.getGraphic());
    }

    private void setupHUD() {
        scoreText = new GraphicsText("Score: 0", 10, 28);
        scoreText.setFont(FontStyle.BOLD, 20);
        canvas.add(scoreText);
    }

    private void setupInput() {
        canvas.onKeyDown(event -> {
            Key key = event.getKey();
            if (key == Key.LEFT_ARROW || key == Key.A) {
                leftPressed = true;
            } else if (key == Key.RIGHT_ARROW || key == Key.D) {
                rightPressed = true;
            }
        });
        canvas.onKeyUp(event -> {
            Key key = event.getKey();
            if (key == Key.LEFT_ARROW || key == Key.A) {
                leftPressed = false;
            } else if (key == Key.RIGHT_ARROW || key == Key.D) {
                rightPressed = false;
            }
        });
    }

    private void update() {
        if (gameOver) {
            return;
        }
        player.update(leftPressed, rightPressed, WIDTH);
        checkPlatformCollisions();
        scrollCamera();
        generatePlatforms();
        removeOffscreenPlatforms();
        updateScreenPositions();
        updateScore();
        checkGameOver();
    }

    private void checkPlatformCollisions() {
        if (player.getVelocityY() <= 0) {
            return; // Only bounce when falling downward
        }
        double playerBottom = player.getWorldY() + Player.HEIGHT;
        double playerLeft = player.getWorldX();
        double playerRight = player.getWorldX() + Player.WIDTH;

        for (Platform platform : platforms) {
            double platTop = platform.getWorldY();
            double platLeft = platform.getWorldX();
            double platRight = platform.getWorldX() + Platform.WIDTH;

            boolean landedOn = playerBottom >= platTop && playerBottom <= platTop + Platform.HEIGHT + 6;
            boolean overlapsHorizontally = playerRight > platLeft && playerLeft < platRight;

            if (landedOn && overlapsHorizontally) {
                player.bounce();
                break;
            }
        }
    }

    private void scrollCamera() {
        // Camera moves up when player reaches the upper third of the screen
        double playerScreenY = player.getWorldY() - cameraY;
        if (playerScreenY < HEIGHT / 3.0) {
            cameraY = player.getWorldY() - HEIGHT / 3.0;
        }
    }

    private void generatePlatforms() {
        double highestPlatY = platforms.stream()
            .mapToDouble(Platform::getWorldY)
            .min()
            .orElse(0);

        while (highestPlatY > cameraY - 100) {
            double gap = MIN_GAP + Math.random() * (MAX_GAP - MIN_GAP);
            double x = Math.random() * (WIDTH - Platform.WIDTH);
            double y = highestPlatY - gap;
            Platform p = new Platform(x, y);
            platforms.add(p);
            canvas.add(p.getGraphic());
            highestPlatY = y;
        }
    }

    private void removeOffscreenPlatforms() {
        Iterator<Platform> it = platforms.iterator();
        while (it.hasNext()) {
            Platform p = it.next();
            if (p.isBelowScreen(cameraY, HEIGHT)) {
                canvas.remove(p.getGraphic());
                it.remove();
            }
        }
    }

    private void updateScreenPositions() {
        for (Platform p : platforms) {
            p.updateScreenPosition(cameraY);
        }
        player.updateScreenPosition(cameraY);
    }

    private void updateScore() {
        if (player.getWorldY() < highestWorldY) {
            highestWorldY = player.getWorldY();
        }
        double initialPlayerY = HEIGHT - 80 - Player.HEIGHT;
        int score = (int) ((initialPlayerY - highestWorldY) / 10);
        scoreText.setText("Score: " + Math.max(0, score));
    }

    private void checkGameOver() {
        if (player.getWorldY() - cameraY > HEIGHT + Player.HEIGHT) {
            gameOver = true;
            showGameOver();
        }
    }

    private void showGameOver() {
        gameOverText = new GraphicsText("GAME OVER", WIDTH / 2.0, HEIGHT / 2.0 - 20);
        gameOverText.setFont(FontStyle.BOLD, 36);
        gameOverText.setAlignment(edu.macalester.graphics.TextAlignment.CENTER);
        canvas.add(gameOverText);

        GraphicsText sub = new GraphicsText("Score: " + scoreText.getText().replace("Score: ", ""), WIDTH / 2.0, HEIGHT / 2.0 + 30);
        sub.setFont(FontStyle.PLAIN, 22);
        sub.setAlignment(edu.macalester.graphics.TextAlignment.CENTER);
        canvas.add(sub);
    }
}
