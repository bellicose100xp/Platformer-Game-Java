package main;

import javax.swing.*;

class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gamePanel);  // Add game panel before setting sizing and positioning

        this.setResizable(false);
        this.pack();  // Set size relative to the preferred size of its components
        this.setLocationRelativeTo(null);  // Center the window on the screen, must come after .pack() line

        this.setVisible(true);
    }
}
