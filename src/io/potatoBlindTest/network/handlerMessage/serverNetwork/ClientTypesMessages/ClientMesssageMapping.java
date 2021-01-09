package io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages;

import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game.*;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.turn.SubmitAnswer;

import java.util.HashMap;
import java.util.Map;

public class ClientMesssageMapping {

    private static final Map<Integer, ClientMessageHandler> handlers =
            new HashMap<Integer, ClientMessageHandler>() {
                {
                    put(ClientMessageType.GET_ALL_GAMES.getValue(), new GetAllGames());
                    put(ClientMessageType.CREATE_GAME.getValue(), new CreateGame());
                    put(ClientMessageType.JOIN_AS_PLAYER.getValue(), new JoinAsPlayer());
                    put(ClientMessageType.JOIN_AS_CREATOR.getValue(), new JoinAsCreator());
                    put(ClientMessageType.SUBMIT_ANSWER.getValue(), new SubmitAnswer());
                    put(ClientMessageType.START_THE_GAME.getValue(), new StartTheGame());

                }
            };

    public static Map<Integer, ClientMessageHandler> getMapping() {
        return new HashMap<>(handlers);
    }
}
