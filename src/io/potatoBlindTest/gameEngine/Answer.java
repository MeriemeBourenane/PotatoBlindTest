package io.potatoBlindTest.gameEngine;

import java.io.Serializable;

public class Answer implements Serializable {

    private String answer;

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                '}';
    }
}
