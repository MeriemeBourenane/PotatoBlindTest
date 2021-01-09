package io.potatoBlindTest.gameEngine;

import java.io.Serializable;

public class Game implements Serializable {

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

    @Override
    public String toString() {
        return "Game{" +
                "creator='" + creator + '\'' +
                ", nbPlayers=" + nbPlayers +
                ", ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                '}';
    }
}
