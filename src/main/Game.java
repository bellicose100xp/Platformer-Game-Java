package main;


import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {

    private final GamePanel gamePanel;

    private Player player;
    private LevelManager levelManager;

    public static final int TILE_DEFAULT_SIZE = 32;
    public static final double SCALE = 2;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;

    public Game() {
        // This needs to be first item
        // As this initialized player is used in render methods of Game and GamePanel
        initGame();

        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocus(); // This component gets the input keystrokes from keyboard

        startGameLoop();
    }

    private void initGame() {
        levelManager = new LevelManager(this);

        int playerWidth =  (int) (64 * SCALE);
        int playerHeight = (int) (40 * SCALE);
        player = new Player(200, 200, playerWidth, playerHeight );
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        
    }

    private void startGameLoop() {
        Thread gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void render (Graphics g) {
        levelManager.draw((Graphics2D) g);
        player.render(g);
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        // Frames per second: How often the component is drawn
        int FPS = 120;
        // How often to draw the component in nanoseconds
        // 1 second = 1_000_000_000 nanoseconds
        double drawInterval = 1_000_000_000.0 / FPS;
        int frames = 0;
        double deltaFrames = 0;
        long lastFrame = System.nanoTime();

        // Updates per second: How often the component is updated
        int UPS = 200;
        double updateInterval = 1_000_000_000.0 / UPS;
        int updates = 0;
        double deltaUpdate = 0;
        long lastUpdate = System.nanoTime();

        long lastChecked = System.currentTimeMillis();

        /* Main Game Loop — Loops Indefinitely */
        while (true) {
            long now = System.nanoTime();

            // deltaUpdate will be 1.0 or more if time passed since last update is >= updateInterval
            deltaUpdate += (now - lastUpdate) / updateInterval;
            lastUpdate = now;

            // Since the draw time can vary slightly (could be slightly more than 1 second)
            // we want to make sure they don't accumulate these to cause a drop in how often the game updates
            // To that effect, we use the UPS (Updates per second), which accounts for this excess (delta) time
            if (deltaUpdate >= 1) {
                update();
                updates++;
                // after removing 1, we're still keeping track of any additional time in the deltaUpdate
                // For instance, if deltaUpdate = 1.2, we'll carry over 0.2
                // so next component update will come sooner (after 0.8+ time)
                deltaUpdate--;
            }

            deltaFrames += (now - lastFrame) / drawInterval;
            lastFrame = now;

            /* Draw Frame*/
            if (deltaFrames >= 1) {
                gamePanel.repaint();
                frames++;
                deltaFrames--;
            }

            /* Prints FPS and UPS every second */
            if (System.currentTimeMillis() - lastChecked >= 1000) {
                // System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastChecked = System.currentTimeMillis();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }
}
