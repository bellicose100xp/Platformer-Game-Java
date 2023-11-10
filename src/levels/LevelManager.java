package levels;

import main.Game;
import util.LoadSave;
import util.constants.AtlasPath;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static main.Game.*;

public class LevelManager {
    private final Game game;
    private BufferedImage[] levelSprites;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        // levelSprite = LoadSave.getAtlas(AtlasPath.LEVEL);
        importOutsideAtlas();
        levelOne = new Level(LoadSave.getLevelData());
    }

    private void importOutsideAtlas() {
        // There are 12 x 4 image grid = 48 images
        levelSprites = new BufferedImage[48];
        BufferedImage img = LoadSave.getAtlas(AtlasPath.LEVEL);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                levelSprites[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics2D g) {

        for (int i = 0; i < TILES_IN_WIDTH; i++) {
            for (int j = 0; j < TILES_IN_HEIGHT; j++) {
                int idx = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprites[idx], TILE_SIZE * i, TILE_SIZE * j, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}
