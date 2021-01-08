package io.potatoBlindTest.network;

import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.Turn;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerGame extends ServerNetwork{

    private CopyOnWriteArrayList<Player> players;
    private Turn currentTurn;

    public ServerGame(ServerSocket serverSocket) throws IOException {
        super(serverSocket);
        this.players = new CopyOnWriteArrayList<>();
        this.currentTurn = null;
    }

    public CopyOnWriteArrayList<Player> getPlayers() {
        return players;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Turn currentTurn) {
        this.currentTurn = currentTurn;
    }
}
