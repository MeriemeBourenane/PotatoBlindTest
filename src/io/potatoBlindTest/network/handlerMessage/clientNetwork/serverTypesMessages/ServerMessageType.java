package io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages;

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
    SPECIFIC_SERVER_GAME(802);

    private int value;
    private ServerMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
