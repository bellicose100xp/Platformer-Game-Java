package util;

import main.Game;

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
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
}
