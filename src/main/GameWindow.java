package main;

import javax.swing.*;

class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        this.setLocationRelativeTo(null);
        this.add(gamePanel);
        this.setVisible(true);
    }
}
