package io.potatoBlindTest.gameEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TableScore implements Serializable {

    private List<Player> players;

    public TableScore(CopyOnWriteArrayList players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "TableScore{" +
                "players=" + players +
                '}';
    }
}
