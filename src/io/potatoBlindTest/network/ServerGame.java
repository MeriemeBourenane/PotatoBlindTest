package io.potatoBlindTest.network;

import io.potatoBlindTest.gameEngine.GameEngine;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerGame extends ServerNetwork {

    private ConcurrentHashMap<Player, ClientHandler> mapPlayerClientHandler;
    private Player creator;
    private StatesGame statesGame;
    private AtomicInteger nbReadyPlayer;
    private GameEngine gameEngine;

    public ServerGame(ServerSocket serverSocket, CopyOnWriteArrayList<ServerGame> serverGames) throws IOException {
        super(serverSocket);
        this.mapPlayerClientHandler = new ConcurrentHashMap<>();
        this.creator = null;
        this.nbReadyPlayer = new AtomicInteger(0);
        this.gameEngine = new GameEngine();
    }

    public ConcurrentHashMap<Player, ClientHandler> getMapPlayerClientHandler() {
        return mapPlayerClientHandler;
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

    public StatesGame getStatesGame() {
        return statesGame;
    }

    public void setStatesGame(StatesGame statesGame) {
        this.statesGame = statesGame;
    }

    public void setNbReadyPlayer(AtomicInteger nbReadyPlayer) {
        this.nbReadyPlayer = nbReadyPlayer;
    }

    public AtomicInteger getNbReadyPlayer() {
        return nbReadyPlayer;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    @Override
    public String toString() {
        return "ServerGame{" +
                "mapPlayerClientHandler=" + mapPlayerClientHandler +
                '}';
    }
}
