package io.potatoBlindTest.gameEngine.typeOfMedia;

import java.io.Serializable;

public enum TypeOfMedia implements Serializable {

    IMAGE("IMAGE"),
    AUDIO("AUDIO");

    private String value;
    private TypeOfMedia(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
