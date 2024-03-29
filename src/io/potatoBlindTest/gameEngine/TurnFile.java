package io.potatoBlindTest.gameEngine;

import io.potatoBlindTest.gameEngine.typeOfMedia.TypeOfMedia;

import java.io.*;
import java.util.Arrays;

public class TurnFile implements Serializable {
    private byte[] fileByteArray;
    private TypeOfMedia typeOfMedia;

    public TurnFile(File file, TypeOfMedia typeOfMedia)  {
        this.typeOfMedia = typeOfMedia;
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

    public TypeOfMedia getTypeOfMedia() {
        return typeOfMedia;
    }

    public byte[] getFileByteArray() {
        return fileByteArray;
    }

    @Override
    public String toString() {
        return "TurnFile{" +
                "fileByteArray= blablabyte" +
                ", typeOfMedia=" + typeOfMedia +
                '}';
    }
}
