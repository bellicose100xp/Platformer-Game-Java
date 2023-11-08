package util.constants;

public enum AtlasPath {
    PLAYER("/player_sprites.png"),
    LEVEL("/outside_sprites.png"),
    LEVEL_ONE_DATA("/level_one_data.png");

    private final String path;

    AtlasPath(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
