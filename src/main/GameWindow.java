package main;

import javax.swing.*;

class GameWindow {
    private JFrame frame;

    public GameWindow() {
        frame = new JFrame();

        frame.setTitle("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
