package net.deechael.invokationtcg;

public enum LostReason {

    ALL_CHARACTER_DEAD(true),
    DISCONNECTED(true),
    GIVE_UP(true),
    WON(false),
    NOT_END(false);

    private final boolean lost;

    LostReason(boolean lost) {
        this.lost = lost;
    }

    boolean isLost() {
        return this.lost;
    }

}
