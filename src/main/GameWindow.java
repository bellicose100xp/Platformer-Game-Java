package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gamePanel);  // Add game panel before setting sizing and positioning

        this.setResizable(false);
        this.pack();  // Set size relative to the preferred size of its components
        this.setLocationRelativeTo(null);  // Center the window on the screen, must come after .pack() line

        this.setVisible(true);

        // Add window focus listener event to freeze game movement
        // Resume when window gets focus
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
