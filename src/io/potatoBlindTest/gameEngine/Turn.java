package io.potatoBlindTest.gameEngine;

import io.potatoBlindTest.gameEngine.typeOfMedia.TypeOfMedia;

import java.io.File;

public class Turn {

    private Player playerWinner;
    private File file;
    private String answer;
    private TypeOfMedia typeOfMedia;

    public Turn() {
        this.file = null;
        this.answer = null;
        this.playerWinner = null;
        this.typeOfMedia = null;
    }

    public Turn( File file, String answer, TypeOfMedia typeOfMedia) {
        this.file = file;
        this.answer = answer;
        this.playerWinner = null;
        this.typeOfMedia = typeOfMedia;
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

    public TypeOfMedia getTypeOfMedia() {
        return typeOfMedia;
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
                ", typeOfMedia=" + typeOfMedia +
                '}';
    }
}
