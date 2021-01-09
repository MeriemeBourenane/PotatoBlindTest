package io.potatoBlindTest.gameEngine;

import java.io.File;
import java.util.ArrayList;

public class GameEngine {
    private ArrayList<Player> players;
    private Turn currentTurn;
    private File folder;
    private File[] filesOfFolder;

    public GameEngine() {
        this.players = new ArrayList<>();
        this.currentTurn = new Turn();
    }

    public void selectFolderTypeOfMedia() {
        // Select randomly a folder into /home/merieme/Documents/UEJava/PotatoBlindTest
        // TODO : replace the path if the server is elsewhere

    }

    public File[] chooseFileInFolder(File folder) {
        File[] listFiles = folder.listFiles();

        return  listFiles;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Turn getCurrentTurn() {

        return currentTurn;
    }

    public void setCurrentTurn(Turn currentTurn) {

        this.currentTurn = currentTurn;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
