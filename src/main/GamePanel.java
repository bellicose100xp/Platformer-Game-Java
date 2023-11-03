package main;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {
    private double xDelta = 0, yDelta = 0;
    private double xDir = 1, yDir = 1;
    private BufferedImage img, subImg;
    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    public GamePanel() {
        importImg();
        setPanelSize();

        // Key Listeners
        this.addKeyListener(new KeyboardInputs(this));

        // Mouse Listeners
        MouseInputs mouseInputs = new MouseInputs(this);
        this.addMouseListener(mouseInputs);
        this.addMouseMotionListener(mouseInputs);
    }

    private void importImg() {
        try (InputStream inputStream = getClass().getResourceAsStream("/player_sprites.png")) {
            if (inputStream == null) throw new IllegalArgumentException("Image not found!");
            img = ImageIO.read(inputStream);
        } catch (IOException e) {
            logger.error("Error importing image: " + e.getMessage(), e);
        }
    }

    private void setPanelSize() {
        Dimension dimension = new Dimension(1280, 800);
        this.setPreferredSize(dimension);
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

        Graphics2D g2D = (Graphics2D) g;
        subImg = img.getSubimage(64, 6 * 40, 64, 40);
        g2D.drawImage(subImg, (int) xDelta, (int) yDelta, 128, 80, null);
    }
}
