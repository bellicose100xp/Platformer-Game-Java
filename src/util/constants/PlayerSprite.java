package util.constants;

public enum PlayerSprite {
    IDLE(0, 5),
    RUNNING(1, 6),
    JUMP(2, 3),
    FALLING(3, 1),
    GROUND(4, 2),
    HIT(5, 4),
    ATTACK_1(6, 3),
    ATTACK_JUMP_1(7, 3),
    ATTACK_JUMP_2(8, 3);

    private final int idx;
    private final int frames;

    PlayerSprite(int idx, int frames) {
        this.idx = idx;
        this.frames = frames;
    }

    public int idx() {
        return idx;
    }

    public int frames() {
        return frames;
    }
}
