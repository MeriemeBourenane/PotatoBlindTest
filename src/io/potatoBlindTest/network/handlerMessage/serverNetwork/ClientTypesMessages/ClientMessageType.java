package io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages;

public enum ClientMessageType {

    // -------------------- Set up a game --------------------
    CREATE_GAME(600),
    GET_ALL_GAMES(601),
    JOIN_AS_PLAYER(602),
    START_THE_GAME(603),
    QUIT_THE_GAME(604),

    // -------------------- After joining a game --------------------
    TURN_RESULT(700),
    READY_PLAYER(701),
    TURN_FILE(702),
    SUBMIT_ANSWER(703),
    END_GAME_RESULTS(704),

    // -------------------- About Server Games --------------------
    ALL_SERVER_GAMES(801),
    SPECIFIC_SERVER_GAME(802);

    private int value;
    private ClientMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
