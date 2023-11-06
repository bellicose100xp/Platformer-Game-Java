package entities;

import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.constants.PlayerActionSprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static util.constants.PlayerActionSprite.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    private int animationTick;
    private int animationIndex;
    private PlayerActionSprite playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        setAnimation();
        updateAnimationTick();
    }

    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(animations[playerAction.idx()][animationIndex], (int) x, (int) y, 256, 160, null);
    }

    private void updateAnimationTick() {
        animationTick++;

        // Game FPS: 120FPS. 120/15 = 8 images per second will be displayed
        int aniSpeed = 15;

        if (animationTick >= aniSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= playerAction.frames()) {
                animationIndex = 0;
                attacking = false; // If attack animation was set, reset it after the last attack sprite
            }
        }
    }

    private void updatePosition() {
        moving = false;

        double speed = 2.0;
        if (left && !right) {
            x -= speed;
            moving = true;
        } else if (right && !left) {
            x += speed;
            moving = true;
        }

        if (up && !down) {
            y -= speed;
            moving = true;
        } else if (down && !up) {
            y += speed;
            moving = true;
        }
    }

    private void setAnimation() {
        PlayerActionSprite startAnimation  = playerAction;

        playerAction = moving ? RUNNING : IDLE;

        // Attacking animation overrides moving and idle animations
        if (attacking) playerAction = ATTACK_1;

        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
           animationTick = 0;
           animationIndex = 0;
    }

    private void loadAnimations() {
        try (InputStream inputStream = getClass().getResourceAsStream("/player_sprites.png")) {
            if (inputStream == null) throw new IllegalArgumentException("Image not found!");
            BufferedImage img = ImageIO.read(inputStream);

            animations = new BufferedImage[9][6];
            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
                }
            }
        } catch (IOException e) {
            logger.error("Error importing image: " + e.getMessage(), e);
        }
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirBooleans() {
        left = right = up = down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
