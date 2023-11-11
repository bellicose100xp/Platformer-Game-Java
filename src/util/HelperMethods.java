package util;

import main.Game;

import java.awt.geom.Rectangle2D;

public class HelperMethods {
    /*
     * Checks if corners of the hitbox are hitting anything solid
     * This would determine if the player can move in that direction
     */
    public static boolean canMoveHere(double x, double y, double width, double height, int[][] levelData) {
        return !isSolid(x, y, levelData) && !isSolid(x + width, y, levelData) && !isSolid(x, y + height,
                levelData) && !isSolid(x + width, y + height, levelData);
    }

    /*
     * Checks if a point (x, y) is hitting anything solid
     */
    private static boolean isSolid(double x, double y, int[][] levelData) {
        if (x < 0 || x >= Game.GAME_WIDTH || y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        double xIndex = x / Game.TILE_SIZE;
        double yIndex = y / Game.TILE_SIZE;

        int spriteIndex = levelData[(int) yIndex][(int) xIndex];

        // We have 48 sprites. Sprite 11 is transparent sprite so that's the only sprite where we are allowed to move
        // This also covers any edge cases where the sprite is > 47 or < 0
        return spriteIndex != 11;
    }

    public static double getEntityXPosNextToWall(Rectangle2D.Double hitbox, double xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILE_SIZE);

        if (xSpeed > 0) {
            // Moving right
            int tileXPosition = currentTile * Game.TILE_SIZE;
            int xOffset = (int) (Game.TILE_SIZE - hitbox.width);
            return tileXPosition + xOffset - 1;
        } else {
            // Moving left
            return currentTile * Game.TILE_SIZE;
        }
    }

    public static double getEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Double hitbox, double airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILE_SIZE);

        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILE_SIZE;
            int yOffset = (int) (Game.TILE_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            // Jumping
            return currentTile * Game.TILE_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Double hitbox, int[][] levelData) {
        // Check the pixel below bottom-left and bottom-right
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData)) {
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData)) {
                return false;
            }
        }
        return true;
    }
}
