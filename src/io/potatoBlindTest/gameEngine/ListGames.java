package io.potatoBlindTest.gameEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListGames implements Serializable {

    private ArrayList<Game> listGames;

    public ListGames() {
        listGames = new ArrayList<Game>();
    }
    public ListGames(ArrayList<Game> listGames) {
        this.listGames = listGames;
    }

    public List<Game> getListGames() {
        return listGames;
    }

    public void addGameToList(Game game) {
        this.listGames.add(game);
    }

    @Override
    public String toString() {
        return "ListGames{" +
                "listGames=" + listGames +
                '}';
    }
}
