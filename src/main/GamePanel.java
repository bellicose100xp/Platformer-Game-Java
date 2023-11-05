package main;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import util.constants.PlayerDirection;
import util.constants.PlayerSprite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static util.constants.PlayerSprite.*;

public class GamePanel extends JPanel {
    private double xDelta = 0, yDelta = 0;
    private double xDir = 1, yDir = 1;
    private BufferedImage img;
    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    //    private BufferedImage[] idleAni;
    private BufferedImage[][] animations;

    // We want to display 4 images per second
    // we have 120 frames being displayed every second
    // So 120/4 = 30, so every 30 frames we want to display a new sprite
    // or 4 images per second
    private int aniTick, aniIndex, aniSpeed = 15;
    private PlayerSprite playerAction = IDLE;
    private PlayerDirection playerDirection;
    private boolean moving = false;

    public GamePanel() {
        importImg();
        loadAnimations();

        setPanelSize();

        // Key Listeners
        this.addKeyListener(new KeyboardInputs(this));

        // Mouse Listeners
        MouseInputs mouseInputs = new MouseInputs(this);
        this.addMouseListener(mouseInputs);
        this.addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        // Max number of unique sprites are 9
        // Max number of images in each sprite are 6 (in row 1)
        animations = new BufferedImage[9][6];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
            }
        }
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

    public void setDirection(PlayerDirection direction) {
        if (direction == null) {
            setMoving(false);
            return;
        }

        this.playerDirection = direction;
        setMoving(true);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= playerAction.frames()) {
                aniIndex = 0;
            }
        }
    }

    private void updatePosition() {
        if (moving) {
            switch (playerDirection) {
                case LEFT -> xDelta -= xDir;
                case UP -> yDelta -= yDir;
                case RIGHT -> xDelta += xDir;
                case DOWN -> yDelta += yDir;
            }
        }
    }

    private void setAnimation() {
        playerAction = moving ? RUNNING : IDLE;
    }

    // Paints component the first time it loads
    // And on every repaint() call
    public void paintComponent(Graphics g) {
        // This lets other components in the background shine through
        // Without it this component will repaint the entire surface
        // Rather than just the parts it's responsible for
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
//        subImg = img.getSubimage(64, 6 * 40, 64, 40);
        updateAnimationTick();
        setAnimation();
        updatePosition();
        g2D.drawImage(animations[playerAction.idx()][aniIndex], (int) xDelta, (int) yDelta, 256, 160, null);
    }

}
