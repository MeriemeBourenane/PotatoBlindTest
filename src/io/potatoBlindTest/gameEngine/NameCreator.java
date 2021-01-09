package io.potatoBlindTest.gameEngine;

import java.io.Serializable;

public class NameCreator extends NamePlayer implements Serializable {

    boolean isCreator = true;

    public NameCreator(String name) {
        super(name);
    }

    public boolean isCreator() {
        return isCreator;
    }
}
