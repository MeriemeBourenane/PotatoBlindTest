package io.potatoBlindTest.gameEngine;

import java.io.Serializable;

public class IsCorrectAnswer implements Serializable  {

    private boolean isCorrect;

    public IsCorrectAnswer(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
