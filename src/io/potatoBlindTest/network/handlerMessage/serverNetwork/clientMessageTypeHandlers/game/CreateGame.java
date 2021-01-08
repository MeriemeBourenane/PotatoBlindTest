package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.ServerNetwork;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.communication.additionalAttachements.SpecificServerGame;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageType;
import io.potatoBlindTest.service.Network;

public class CreateGame implements ClientMessageHandler<Player> {

    // The current clientHandler containes all clientHandlers of the ServerNetwork and all the ServerGames
    @Override
    public Message handle(Player dataMessage, ClientHandler clientHandler) {
        // Create a ServerGame :
        // - add it to the CopyOnWriteArrayList<ServerGame>
        // - send the new ServerSocket 's ServerGame to the client
        Message messageToSend;
        ServerGame newServerGame = Network.createServerGame();

        if (newServerGame == null) {
            messageToSend = new Message(403);
        } else {
            ServerNetwork.addServerGameToList(newServerGame);
            SpecificServerGame specificServerGame = new SpecificServerGame(newServerGame.getServerSocket().getLocalPort(),
                    newServerGame.getServerSocket().getInetAddress());
            messageToSend = new MessageAttachment<SpecificServerGame>(200, specificServerGame);
        }

        return messageToSend;
    }
}
