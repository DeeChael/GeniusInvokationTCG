package net.deechael.invokationtcg.server;

import net.deechael.invokationtcg.User;
import net.deechael.invokationtcg.network.Connection;
import net.deechael.invokationtcg.network.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class GeniusInvokationTCGServer {

    private final NetworkManager networkManager = new NetworkManager(this);

    private final Map<String, User> onlineUsers = new HashMap<>();

    public GeniusInvokationTCGServer() {
    }

    public void start() {
        networkManager.start();
    }

    public void close() {
        this.onlineUsers.values().stream().map(User::getConnection).forEach(Connection::disconnect);
        this.networkManager.close();
    }

}
