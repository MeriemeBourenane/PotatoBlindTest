package io.potatoBlindTest.gameEngine;

import java.io.*;
import java.util.Arrays;

public class TurnFile implements Serializable {
    private byte[] fileByteArray;

    public TurnFile(File file)  {
        try {
            FileInputStream fileIn = new FileInputStream(file);

            //find out length
            long fileLen = file.length();

            //convert length to int and create byteArray with this int
            int intFileLen = (int) fileLen;
            fileByteArray = new byte[intFileLen];
            System.out.println(intFileLen);
            //read file into byte array
            fileIn.read(fileByteArray);

            //Close FileInputStream...
            fileIn.close();
        } catch (IOException e) {
            System.out.println("[TurnFile] Error while reading the file " + file.getPath());
        }
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
                "fileByteArray=" + "toolong" +
                '}';
    }
}
