package io.potatoBlindTest.network;

import io.potatoBlindTest.gameEngine.GameEngine;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.Turn;
import io.potatoBlindTest.gameEngine.TurnFile;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;

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

            if (statesGame.equals(StatesGame.INIT)) {
                if (entry.get().getKey().getCreator()) {
                    System.out.println("[ServerGame] Creator has left the game");
                    mapPlayerClientHandler.values().forEach(clientHandler -> clientHandler.closeNetwork());
                }

            } else if (statesGame.equals(StatesGame.STARTED)) {
                // Maybe one day something will be implemented there
            }

            // Verify again the nb of ready players
            if (nbReadyPlayer.get() == this.mapPlayerClientHandler.size()) {
                this.nbReadyPlayer.set(0);

                Thread threadNotify = new Thread(() -> {
                    // Call game engine to choose a file
                    Turn turn = this.gameEngine.newTurn();
                    TurnFile turnFile = new TurnFile(turn.getFile(), turn.getTypeOfMedia());

                    Message notification = new MessageAttachment<TurnFile>(ServerMessageType.TURN_FILE.getValue(), turnFile);

                    for (Player playerEntry: this.mapPlayerClientHandler.keySet()) {
                        try {
                            this.notifyClient(this.mapPlayerClientHandler.get(playerEntry),notification);
                        } catch (Exception e) {
                            System.out.println("[ServerGame]->[thread notify] Can't notify the players ...");
                        }
                    }

                });
                threadNotify.start();
            }

        } else {
            System.out.println("[ServerGame] Someone has left the game");

        }

        // stop the server if all client has left the game
        if (getMapPlayerClientHandler().size() == 0) {
            this.shutdown();
        }

    }

    public void notifyClient(ClientHandler clientToNotify, Message messageNotify) {
        System.out.println("[ServerGame] Notifying ...");
        clientToNotify.notify(messageNotify);
    }

    @Override
    public String toString() {
        return "ServerGame{" +
                "mapPlayerClientHandler=" + mapPlayerClientHandler +
                '}';
    }
}
