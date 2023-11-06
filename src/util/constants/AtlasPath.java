package util.constants;

public enum AtlasPath {
    PLAYER("/player_sprites.png");

    private final String path;

    AtlasPath(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
