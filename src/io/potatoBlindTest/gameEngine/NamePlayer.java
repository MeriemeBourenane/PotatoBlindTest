package io.potatoBlindTest.gameEngine;

import java.io.Serializable;

public class NamePlayer implements Serializable {

    private String name;

    public NamePlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NamePlayer{" +
                "name='" + name + '\'' +
                '}';
    }
}
