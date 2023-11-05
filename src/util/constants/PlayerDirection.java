package util.constants;

public enum PlayerDirection {
    LEFT(1),
    UP(2),
    RIGHT(3),
    DOWN(4);

    private final int value;

    PlayerDirection(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
