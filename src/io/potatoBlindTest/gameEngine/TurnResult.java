package io.potatoBlindTest.gameEngine;

import java.io.Serializable;

public class TurnResult implements Serializable {

    private String answer;

    private String turnWinner;

    public TurnResult(String answer, String turnWinner) {
        this.answer = answer;
        this.turnWinner = turnWinner;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTurnWinner() {
        return turnWinner;
    }

    @Override
    public String toString() {
        return "TurnResult{" +
                "answer='" + answer + '\'' +
                ", turnWinner='" + turnWinner + '\'' +
                '}';
    }
}
