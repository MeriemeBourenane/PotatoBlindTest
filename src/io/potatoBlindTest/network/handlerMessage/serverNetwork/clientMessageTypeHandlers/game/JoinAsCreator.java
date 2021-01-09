package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.NameCreator;
import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.ServerNetwork;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;

public class JoinAsCreator implements ClientMessageHandler<NameCreator> {

    @Override
    public Message handle(NameCreator dataMessage, ClientHandler clientHandlers) {
        System.out.println("Handling Join as creator ...");
        // Join as creator :
        //  - put the creator in the mapPlayerSocket
        //  - initialize the Player creator
        Message messageToSend = null;

        if (clientHandlers.getServerNetwork().isServerGame() && ((ServerGame)clientHandlers.getServerNetwork()).getCreator() == null) {
            Player creator = new Player(dataMessage.getName(), true);
            ((ServerGame)clientHandlers.getServerNetwork()).setCreator(creator);
            ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().put(creator, clientHandlers);
            ((ServerGame)clientHandlers.getServerNetwork()).setStatesGame(StatesGame.INIT);

            messageToSend = new Message(ServerMessageType.OK.getValue());
            System.out.println("[JoinAsCreator] Creator : " + ((ServerGame) clientHandlers.getServerNetwork()).getCreator());

        } else {
            messageToSend = new Message(ServerMessageType.FORBIDDEN.getValue());
        }

        return messageToSend;
    }
}
