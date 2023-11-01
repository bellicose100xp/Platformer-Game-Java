package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    private double xDelta = 0, yDelta = 0;
    private double xDir = 1, yDir = 1;
    private Color color = new Color(150, 20, 90);
    private final Random random = new Random();

    public GamePanel() {
        this.addKeyListener(new KeyboardInputs(this));
        MouseInputs mouseInputs = new MouseInputs(this);
        this.addMouseListener(mouseInputs);
        this.addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int xDelta) {
        this.xDelta += xDelta;
    }

    public void changeYDelta(int yDelta) {
        this.yDelta += yDelta;
    }

    public void setRectPos(int x, int y) {
        xDelta = x;
        yDelta = y;
    }

    // Paints component the first time it loads
    // And on every repaint() call
    public void paintComponent(Graphics g) {
        // This lets other components in the background shine through
        // Without it this component will repaint the entire surface
        // Rather than just the parts it's responsible for
        super.paintComponent(g);

        updateRectangle();
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(color);
        g2D.fillRect((int) xDelta, (int) yDelta, 200, 50);


    }

    private void updateRectangle() {
        xDelta += xDir;
        if (xDelta > 400 || xDelta < 0) {
            xDir *= -1;
            color = getRndColor();
        }

        yDelta += yDir;
        if (yDelta > 400 || yDelta < 0) {
            yDir *= -1;
            color = getRndColor();
        }
    }

    private Color getRndColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return new Color(r, g, b);
    }
}
