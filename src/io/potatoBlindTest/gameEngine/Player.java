package io.potatoBlindTest.gameEngine;

import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable {

    String name;
    Integer score;
    Integer rank;
    Socket socket;

    public Player(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        this.score = 0;
        this.rank = 0;
    }

    public Player(String name, Integer score, Socket socket) {
        this.name = name;
        this.score = score;
        this.socket = socket;
    }

    public Player(String name, Integer score, Integer rank, Socket socket) {
        this.name = name;
        this.score = score;
        this.rank = rank;
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Integer getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                ", socket=" + socket +
                '}';
    }
}
