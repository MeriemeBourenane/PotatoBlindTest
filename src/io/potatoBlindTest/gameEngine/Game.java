package io.potatoBlindTest.gameEngine;

public class Game {

    private String creator;
    private Integer nbPlayers;

    public Game(String creator, Integer nbPlayers) {
        this.creator = creator;
        this.nbPlayers = nbPlayers;
    }

    public Integer getNbPlayers() {
        return nbPlayers;
    }

    public String getCreator() {
        return creator;
    }
}
