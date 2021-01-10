package io.potatoBlindTest.gameEngine;

import java.io.File;

public class Turn {

    private Player playerWinner;
    private File file;
    private String answer;

    public Turn() {
        this.file = null;
        this.answer = null;
        this.playerWinner = null;
    }

    public Turn( File file, String answer) {
        this.file = file;
        this.answer = answer;
        this.playerWinner = null;
    }

    public Player getPlayerWinner() {
        return playerWinner;
    }

    public void setPlayerWinner(Player playerWinner) {
        this.playerWinner = playerWinner;
        this.playerWinner.incrementScore();
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
                ", file=" + file +
                ", answer='" + answer + '\'' +
                '}';
    }
}
