package io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages;

import java.util.HashMap;
import java.util.Map;

public enum ServerMessageType {

    // -------------------- Error Client --------------------
    FORBIDDEN(403),
    NOT_FOUND(404),

    // -------------------- Error Server --------------------
    ERROR_SERVER(500),

    // -------------------- Successful request --------------------
    OK(200),

    // -------------------- After joining a game --------------------
    TURN_RESULT(700),
    TURN_FILE(702),
    END_GAME_RESULTS(704),

    // -------------------- About Server Games --------------------
    ALL_SERVER_GAMES(801),
    SPECIFIC_SERVER_GAME(802),
    NEW_PLAYER_IN_GAME(803);

    private int value;
    private ServerMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    private static final Map<Integer, ServerMessageType> BY_LABEL = new HashMap<>();

    static {
        for (ServerMessageType e: values()) {
            BY_LABEL.put(e.getValue(), e);
        }
    }

    public static ServerMessageType valueOfLabel(Integer code) {
        return BY_LABEL.get(code);
    }
}
