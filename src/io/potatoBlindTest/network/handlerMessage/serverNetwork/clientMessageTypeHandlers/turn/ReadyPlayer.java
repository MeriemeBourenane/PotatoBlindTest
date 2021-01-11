package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.turn;

import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.Turn;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;
import io.potatoBlindTest.gameEngine.TurnFile;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.notification.SubjectClientHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class ReadyPlayer extends SubjectClientHandler implements ClientMessageHandler<Player> {
    @Override
    public Message handle(Player dataMessage, ClientHandler clientHandlers) {
        System.out.println("Handling ready player ...");
        // Ready Player :
        //  - answer with 200, 403
        //  - increment the counter of ready players
        //  - when the counter is equal to the number of players -> notify all the players with TURN_FILE code
        Message messageToSend;
        int nbReadyPlayer = -1;
        if (clientHandlers.getServerNetwork().isServerGame() && ((ServerGame)clientHandlers.getServerNetwork()).getStatesGame() == StatesGame.STARTED) {
            nbReadyPlayer = ((ServerGame)clientHandlers.getServerNetwork()).getNbReadyPlayer().incrementAndGet();
            messageToSend = new Message(ServerMessageType.OK.getValue());
        } else {
            messageToSend = new Message(ServerMessageType.FORBIDDEN.getValue());
        }
        System.out.println("[ReadyPlayer] message return code : " + messageToSend.getCode());
        if (nbReadyPlayer == ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().size()) {
            ((ServerGame)clientHandlers.getServerNetwork()).getNbReadyPlayer().set(0);
            System.out.println("[ReadyPlayer] will notify");

            Thread threadNotify = new Thread(() -> {
                // Call game engine to choose a file
                Turn turn = ((ServerGame)clientHandlers.getServerNetwork()).getGameEngine().newTurn();
                TurnFile turnFile = new TurnFile(turn.getFile(), turn.getTypeOfMedia());

                Message notification = new MessageAttachment<TurnFile>(ServerMessageType.TURN_FILE.getValue(), turnFile);

                for (Player player: ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().keySet()) {
                    Thread threadNotify_client = new Thread(() -> {
                        try {
                            this.notifyOne(((ServerGame) clientHandlers.getServerNetwork()).getMapPlayerClientHandler()
                                            .get(player),
                                    notification);
                        } catch (Exception e) {
                            System.out.println("[ReadyPlayer]->[thread notify] Can't notify the players ...");
                        }
                    });
                    threadNotify_client.start();
                }

            });
            threadNotify.start();
        }

        return messageToSend;
    }
}
