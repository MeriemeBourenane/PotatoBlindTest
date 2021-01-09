package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.NameCreator;
import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.notification.SubjectClientHandler;

import java.util.concurrent.CopyOnWriteArrayList;

public class StartTheGame extends SubjectClientHandler implements ClientMessageHandler<Player> {
    @Override
    public Message handle(Player dataMessage, ClientHandler clientHandlers) {
        System.out.println("Handling start the game ...");
        // Start the game :
        //  - verify if the current clientHandler is the same as the creator's clientHandler
        //  - if it is      -> update stateGame at STARTED
        //                  -> send OK
        //                  -> notify all players of the game
        //  - if it's not   -> send FORBIDDEN
        Message messageToSend;

        if (clientHandlers.getServerNetwork().isServerGame() &&
                clientHandlers == ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler()
                        .get(((ServerGame)clientHandlers.getServerNetwork()).getCreator()) &&
                ((ServerGame)clientHandlers.getServerNetwork()).getStatesGame() == StatesGame.INIT) {

            ((ServerGame)clientHandlers.getServerNetwork()).setStatesGame(StatesGame.SATARTED);
            messageToSend = new Message(ServerMessageType.OK.getValue());
        } else {
            messageToSend = new Message(ServerMessageType.FORBIDDEN.getValue());
        }

        if (messageToSend.getCode() == ServerMessageType.OK.getValue()) {
            Thread threadNotify = new Thread(() -> {

                Message notification = new Message(ServerMessageType.BEGINNING_GAME.getValue());
                for (Player player: ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().keySet()) {
                    try {
                        this.notifyOne(((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler()
                                        .get(player),
                                notification);
                    } catch (Exception e) {
                        System.out.println("[StartTheGame] Can't notify the players ...");
                    }
                }

            });
            threadNotify.start();
        }

        return messageToSend;
    }
}
