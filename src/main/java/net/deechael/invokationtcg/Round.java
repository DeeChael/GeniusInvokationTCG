package net.deechael.invokationtcg;

public class Round {

    private final Player host;
    private final Player guest;
    private final boolean hostEnded = false;
    private final boolean guestEnded = false;

    public Round(Player host, Player guest, int roundIndex, RoundIndex nextIndex) {
        this.host = host;
        this.guest = guest;
    }

    public Player getLastOperator() {
        return this.getNextRoundIndex() == RoundIndex.HOST ? this.getGuest() : this.getHost();
    }

    public Player getNextOperator() {
        return this.getNextRoundIndex() == RoundIndex.HOST ? this.getHost() : this.getGuest();
    }

    public Player getHost() {
        return host;
    }

    public Player getGuest() {
        return guest;
    }

    public boolean isHostEnded() {
        return hostEnded;
    }

    public boolean isGuestEnded() {
        return guestEnded;
    }

    public boolean isEnded() {
        return hostEnded && guestEnded;
    }

    public RoundIndex getNextRoundIndex() {
        return RoundIndex.HOST;
    }

}
