package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.NameCreator;
import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.gameEngine.Player;
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
        // Join as creator :
        //  - put the creator in the mapPlayerSocket
        //  - initialize the Player creator
        Message messageReceived = null;

        if (clientHandlers.getServerNetwork().isServerGame() && ((ServerGame)clientHandlers.getServerNetwork()).getCreator() == null) {
            Player creator = new Player(dataMessage.getName(), true);
            ((ServerGame)clientHandlers.getServerNetwork()).setCreator(creator);
            ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerSocket().put(creator, clientHandlers.getSocket());
            messageReceived = new Message(ServerMessageType.OK.getValue());
            System.out.println("[JoinAsCreator] Creator : " + ((ServerGame) clientHandlers.getServerNetwork()).getCreator());
        } else {;
            messageReceived = new Message(ServerMessageType.FORBIDDEN.getValue());
        }

        return messageReceived;
    }
}
