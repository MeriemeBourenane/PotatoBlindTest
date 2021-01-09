package io.potatoBlindTest.network;

import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.Turn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerGame extends ServerNetwork{

    private ConcurrentHashMap<Player, Socket> mapPlayerSocket;
    private Player creator;
    private Turn currentTurn;

    public ServerGame(ServerSocket serverSocket) throws IOException {
        super(serverSocket);
        this.mapPlayerSocket = new ConcurrentHashMap<>();
        this.creator = null;
        this.currentTurn = null;
    }

    public ConcurrentHashMap<Player, Socket> getMapPlayerSocket() {
        return mapPlayerSocket;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Turn currentTurn) {
        this.currentTurn = currentTurn;
    }

    @Override
    public boolean isServerGame() {
        return true;
    }

    public Player getCreator() {
        return creator;
    }

    public void setCreator(Player creator) {
        this.creator = creator;
    }

}
