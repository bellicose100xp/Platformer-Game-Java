package entities;

import main.Game;
import util.LoadSave;
import util.constants.AtlasPath;
import util.constants.PlayerActionSprite;

import static util.HelperMethods.canMoveHere;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.SCALE;
import static util.constants.PlayerActionSprite.*;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private int animationTick;
    private int animationIndex;
    private PlayerActionSprite playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;

    private int[][] levelData;

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        // 20 pixel is the actual size of the players body width in the hitbox
        // 28 pixel is the actual size of the players body height in the hitbox
        initHitbox(x, y, 20 * SCALE, 28 * SCALE);
    }

    public void update() {
        updatePosition();
        setAnimation();
        updateAnimationTick();
    }

    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        // Players body is 21 pixels to right of the hitbox's left edge
        double xDrawOffset = 21 * SCALE;
        // Players body is 4 pixels below the top edge of the hitbox
        double yDrawOffset = 4 * SCALE;
        g2D.drawImage(animations[playerAction.idx()][animationIndex], (int) (hitbox.x - xDrawOffset),
                (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g2D);
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

        // If we are not pressing any navigation keys actively, we shouldn't update player
        if (!left && !right && !up && !down) {
            return;
        }

        double speed = 2.0;
        double xSpeed = 0.0, ySpeed = 0.0;

        if (left && !right) {
            xSpeed = -speed;
        } else if (right && !left) {
            xSpeed = speed;
        }

        if (up && !down) {
            ySpeed = -speed;
        } else if (down && !up) {
            ySpeed = speed;
        }

        // Move player only if it can move there
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width,  hitbox.height, levelData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
        }
    }

    private void setAnimation() {
        PlayerActionSprite startAnimation = playerAction;

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
        BufferedImage img = LoadSave.getAtlas(AtlasPath.PLAYER);

        animations = new BufferedImage[9][6];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
            }
        }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
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
