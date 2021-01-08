package io.potatoBlindTest.gameEngine;

public class Game {

    private String creator;
    private Integer nbPlayers;
    private String ipAddress;
    private Integer port;

    public Game(String creator, Integer nbPlayers, String ipAddress, Integer port) {
        this.creator = creator;
        this.nbPlayers = nbPlayers;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Integer getNbPlayers() {
        return nbPlayers;
    }

    public String getCreator() {
        return creator;
    }

    public Integer getPort() {
        return port;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
