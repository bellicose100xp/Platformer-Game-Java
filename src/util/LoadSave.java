package util;

import main.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.constants.AtlasPath;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static main.Game.TILES_IN_HEIGHT;
import static main.Game.TILES_IN_WIDTH;

public class LoadSave {
    private static final Logger logger = LogManager.getLogger(LoadSave.class);

    /*
    An atlas is a texture that contains multiple smaller textures, known as sprites. By using an atlas, game
    developers can reduce draw calls and memory usage, leading to better performance and faster loading times.
     */
    public static BufferedImage getAtlas(AtlasPath atlasPath) {
        BufferedImage img = null;

        try (InputStream inputStream = LoadSave.class.getResourceAsStream(atlasPath.path())) {
            if (inputStream == null) throw new IllegalArgumentException("Image not found!");
            img = ImageIO.read(inputStream);
        } catch (IOException e) {
            logger.error("Error importing image: " + e.getMessage(), e);
        }

        return img;
    }

    /**
     * Level data is stored in a 2D array of integers. Each integer corresponds to a sprite image in the atlas.
     * @return 2D array of integers representing level data
     */
    public static int[][] getLevelData() {
        int[][] levelData = new int[TILES_IN_HEIGHT][TILES_IN_WIDTH];
        BufferedImage img = LoadSave.getAtlas(AtlasPath.LEVEL_ONE_DATA);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color color = new Color(img.getRGB(i, j));
                // Red color value (0-47) corresponds to the sprite index in the level atlas (12 x 4 = 48 images)
                int spriteIdx = color.getRed();
                if (spriteIdx >= 48) {
                    spriteIdx = 0;
                }
                levelData[j][i] = spriteIdx;
            }
        }

        return levelData;
    }
}
