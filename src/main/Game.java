package main;

class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS = 120;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    @Override
    public void run() {
        // How often to draw the component in nanoseconds
        // 1 second = 1_000_000_000 nanoseconds
        double drawInterval = 1_000_000_000.0 / FPS;
        long lastFrame = System.nanoTime();

        int frames = 0;
        long lastChecked = System.currentTimeMillis();

        while (true) {
            long now = System.nanoTime();
            if (now - lastFrame >= drawInterval) {
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            /* Prints FPS */
            if (System.currentTimeMillis() - lastChecked >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                lastChecked = System.currentTimeMillis();
            }
        }
    }
}
