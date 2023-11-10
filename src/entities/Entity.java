package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected double x, y;
    protected int width, height;
    protected Rectangle2D.Double hitbox;

    public Entity(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawHitbox(Graphics2D g) {
        // For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public void initHitbox(double x, double y, double width, double height) {
        hitbox = new Rectangle2D.Double(x, y, width, height);
    }

    // public void updateHitbox() {
    //     hitbox.x = (int) x;
    //     hitbox.y = (int) y;
    // }

    public Rectangle2D.Double getHitbox() {
        return hitbox;
    }
}
