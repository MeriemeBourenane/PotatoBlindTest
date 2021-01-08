package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageType;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class GetAllGames implements ClientMessageHandler<Player> {

    // The current clientHandler containes all clientHandlers of the ServerNetwork and all the ServerGames
    @Override
    public Message handle(Player dataMessage, ClientHandler clientHandler) {
        // Return all server games

        Message mes = new Message(ClientMessageType.ALL_SERVER_GAMES.getValue());
        return mes;
    }
}
