package io.potatoBlindTest.gameEngine.statsGame;

public enum StatesGame {

    CREATED(900),
    INIT(901),
    STARTED(902),
    ENDED(903);

    private int value;
    private StatesGame(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
