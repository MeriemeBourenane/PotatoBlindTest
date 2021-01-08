package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;

public class JoinAsPlayer implements ClientMessageHandler<NamePlayer> {

    // The current clientHandler containes all clientHandlers of the ServerNetwork and all the ServerGames
    @Override
    public Message handle(NamePlayer dataMessage, ClientHandler clientHandlers) {
        System.out.println("Handling join as player ...");
        // Join as player :
        // - add it to the CopyOnWriteArrayList<ServerGame>
        // - send the new ServerSocket 's ServerGame to the client
        Message messageToSend;

        return null;
    }
}
