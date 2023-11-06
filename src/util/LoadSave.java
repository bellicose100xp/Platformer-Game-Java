package util;

import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.constants.AtlasPath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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
}
