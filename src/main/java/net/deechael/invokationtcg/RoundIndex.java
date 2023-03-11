package net.deechael.invokationtcg;

public enum RoundIndex {

    HOST, GUEST;

    public RoundIndex getNextIndex() {
        return this == HOST ? GUEST : HOST;
    }

}
