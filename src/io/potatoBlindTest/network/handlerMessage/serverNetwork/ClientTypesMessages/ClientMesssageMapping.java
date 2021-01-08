package io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages;

import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game.GetAllGames;

import java.util.HashMap;
import java.util.Map;

public class ClientMesssageMapping {

    private static final Map<Integer, ClientMessageHandler> handlers =
            new HashMap<Integer, ClientMessageHandler>() {
                {
                    put(ClientMessageType.GET_ALL_GAMES.getValue(), new GetAllGames());
                }
            };

    public static Map<Integer, ClientMessageHandler> getMapping() {
        return new HashMap<>(handlers);
    }
}
