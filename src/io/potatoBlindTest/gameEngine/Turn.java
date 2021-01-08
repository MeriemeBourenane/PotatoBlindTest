package io.potatoBlindTest.gameEngine;

import java.io.File;
import java.io.Serializable;

public class Turn implements Serializable {

    private Player playerWinner;
    private String fileName;
    private File file;
    private String answer;

    public Turn(String fileName, File file, String answer) {
        this.fileName = fileName;
        this.file = file;
        this.answer = answer;
        this.playerWinner = null;
    }

    public Player getPlayerWinner() {
        return playerWinner;
    }

    public void setPlayerWinner(Player playerWinner) {
        this.playerWinner = playerWinner;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "playerWinner=" + playerWinner +
                ", fileName='" + fileName + '\'' +
                ", file=" + file +
                ", answer='" + answer + '\'' +
                '}';
    }
}
