package io.potatoBlindTest.gameEngine;

import io.potatoBlindTest.network.MainServerNetwork;
import javafx.scene.control.Tab;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameEngine {
    private ArrayList<Player> players;
    private Turn currentTurn;
    private File resourcesFolder;
    private File selectedFolder;
    private final int numberOfTurn = 3;
    private List<File> history;

    public GameEngine() {
        this.players = new ArrayList<>();
        this.currentTurn = new Turn();
        this.history = new ArrayList<>();
        this.resourcesFolder = new File(MainServerNetwork.getDataFolderPath());
        this.selectFolderTypeOfMedia();
        System.out.println("[GameEngine] selected");
    }

    public void selectFolderTypeOfMedia() {
        File [] listFiles = resourcesFolder.listFiles();

        Random rand = new Random();
        int selected = rand.nextInt(listFiles.length);
        this.selectedFolder = listFiles[selected];
    }

    public boolean isLastTurn() {
        return history.size() == this.numberOfTurn;
    }

    public boolean isCorrectAnswer(String answer) {
        return this.currentTurn.getAnswer().equals(answer.toUpperCase());
    }

    public Turn newTurn() {
        File turnFile = chooseFileInFolder();
        String answer = turnFile.getName().toUpperCase().replace("_", " ");
        this.setCurrentTurn(new Turn(turnFile, answer));

        return this.currentTurn;
    }

    public File chooseFileInFolder() {
        File[] listFiles = this.selectedFolder.listFiles();
        System.out.println("[GameEngine] " + listFiles);
        Random rand = new Random();
        int selected;
        do {
             selected = rand.nextInt(listFiles.length);
        } while (this.history.contains(listFiles[selected]));

        history.add(listFiles[selected]);

        return listFiles[selected];
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

    public TableScore makeTableScore() {
        List<Player> players = this.players.stream().sorted((player, t1) -> t1.getScore().compareTo(player.getScore())).collect((Collectors.toList()));
        TableScore tableScore = new TableScore(players);

        int previousScore = -1;
        int previousRank = -1;
        for (int i = 0; i < players.size(); i ++) {
            Player currentPlayer = players.get(i);
            if (previousScore == currentPlayer.getScore()) {
                currentPlayer.setRank(previousRank);
            } else {
                currentPlayer.setRank(i + 1);
                previousScore = currentPlayer.getScore();
                previousRank = i + 1;
            }
        }

        return tableScore;
    }
}
