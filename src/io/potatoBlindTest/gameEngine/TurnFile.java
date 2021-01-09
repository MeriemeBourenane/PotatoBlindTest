package io.potatoBlindTest.gameEngine;

import java.io.Serializable;
import java.util.Arrays;

public class TurnFile implements Serializable {
    private byte[] fileByteArray;
    public TurnFile() {
        this.fileByteArray = new byte[]{1};
    }
    public TurnFile(byte[] fileByteArray) {
        this.fileByteArray = fileByteArray;
    }

    public byte[] getFileByteArray() {
        return fileByteArray;
    }

    @Override
    public String toString() {
        return "TurnFile{" +
                "fileByteArray=" + Arrays.toString(fileByteArray) +
                '}';
    }
}
