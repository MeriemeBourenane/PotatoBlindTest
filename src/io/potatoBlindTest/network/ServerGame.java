package io.potatoBlindTest.network;

import io.potatoBlindTest.gameEngine.GameEngine;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Optional;
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
        this.serverGames = serverGames;
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

    public void removeGameFromList() {
        System.out.println("[ServerGame] Remove the server game from the list");
        serverGames.remove(this);
    }

    public void playerHasLeftTheGame(ClientHandler player) {
        Optional<Map.Entry<Player, ClientHandler>> entry = getMapPlayerClientHandler()
                .entrySet()
                .stream()
                .filter(playerClientHandlerEntry -> playerClientHandlerEntry.getValue().equals(player))
                .findFirst();
        if (entry.isPresent()) {
            System.out.println("[ServerGame] Player has left the game");
            getMapPlayerClientHandler().entrySet().remove(entry.get());
            if (entry.get().getKey().getCreator()) {
                System.out.println("[ServerGame] Creator has left the game");
                creatorHasLeftTheGame();
            }
        } else {
            // TODO: No entry, strange
            System.out.println("[ServerGame] Someone has left the game");

        }

        // stop the server if all client has left the game
        if (getMapPlayerClientHandler().size() == 0) {
            this.shutdown();
        }

    }
    public void creatorHasLeftTheGame() {
        if (statesGame.equals(StatesGame.INIT)) {
            mapPlayerClientHandler.values().forEach(clientHandler -> clientHandler.closeNetwork());
            removeGameFromList();
        }
    }

    @Override
    public String toString() {
        return "ServerGame{" +
                "mapPlayerClientHandler=" + mapPlayerClientHandler +
                '}';
    }
}
