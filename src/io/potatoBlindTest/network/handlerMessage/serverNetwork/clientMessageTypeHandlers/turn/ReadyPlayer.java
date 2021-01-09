package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.turn;

import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.gameEngine.Player;
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

        if (clientHandlers.getServerNetwork().isServerGame() && ((ServerGame)clientHandlers.getServerNetwork()).getStatesGame() == StatesGame.STARTED) {
            ((ServerGame)clientHandlers.getServerNetwork()).getNbReadyPlayer().incrementAndGet();
            messageToSend = new Message(ServerMessageType.OK.getValue());
        } else {
            messageToSend = new Message(ServerMessageType.FORBIDDEN.getValue());
        }

        if (((ServerGame)clientHandlers.getServerNetwork()).getNbReadyPlayer() == new AtomicInteger(((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().size())) {
            ((ServerGame)clientHandlers.getServerNetwork()).getNbReadyPlayer().set(0);
            Thread threadNotify = new Thread(() -> {
                // Call game engine to choose a file
                TurnFile turnFile = new TurnFile();
                Message notification = new MessageAttachment<TurnFile>(ServerMessageType.TURN_FILE.getValue(), turnFile);

                for (Player player: ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().keySet()) {
                    try {
                        this.notifyOne(((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler()
                                        .get(player),
                                notification);
                    } catch (Exception e) {
                        System.out.println("[ReadyPlayer]->[thread notify] Can't notify the players ...");
                    }
                }

            });
            threadNotify.start();
        }

        return messageToSend;
    }
}
