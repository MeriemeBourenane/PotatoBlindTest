package io.potatoBlindTest.gameEngine;

import java.io.Serializable;
import java.net.Socket;
import java.util.Objects;

public class Player implements Serializable {

    String name;
    Integer score;
    Integer rank;
    Boolean isCreator;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.rank = 0;
        this.isCreator = false;
    }

    public Player(String name, boolean isCreator) {
        this.name = name;
        this.score = 0;
        this.rank = 0;
        this.isCreator = isCreator;
    }

    public Player(String name, Integer score) {
        this.name = name;
        this.score = score;
        this.rank = 0;
    }

    public Player(String name, Integer score, Integer rank) {
        this.name = name;
        this.score = score;
        this.rank = rank;
        this.isCreator = false;
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

    public Integer getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
