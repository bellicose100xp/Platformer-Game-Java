package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private final Game game;

    public GamePanel(Game game) {
        this.game = game;

        setPanelSize();

        // Key Listeners
        this.addKeyListener(new KeyboardInputs(this));

        // Mouse Listeners
        MouseInputs mouseInputs = new MouseInputs(this);
        this.addMouseListener(mouseInputs);
        this.addMouseMotionListener(mouseInputs);
    }


    private void setPanelSize() {
        Dimension dimension = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        this.setPreferredSize(dimension);
    }

    // Paints component the first time it loads
    // And on every repaint() call
    public void paintComponent(Graphics g) {
        // This lets other components in the background shine through
        // Without it this component will repaint the entire surface
        // Rather than just the parts it's responsible for
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
