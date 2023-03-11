package net.deechael.invokationtcg;

import net.deechael.invokationtcg.card.Deck;
import net.deechael.invokationtcg.network.Connection;
import net.deechael.invokationtcg.network.PacketListener;

public class User {

    private final Connection connection;

    public User(Connection connection, PacketListener listener) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return this.connection.isConnected();
    }

    public Deck getCurrentDeck() {
        return null;
    }

}
