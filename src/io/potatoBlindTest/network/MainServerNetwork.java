package io.potatoBlindTest.network;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainServerNetwork {

    private static String dataFolderPath;


    public static String getDataFolderPath() {
        return dataFolderPath;
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ClassNotFoundException {
        if (args.length != 1) {
            System.err.println("No path argument");
            return;
        }
        File folder = new File(args[0]);
        if (folder.exists() && folder.isDirectory()) {
            dataFolderPath = folder.getAbsolutePath();
        } else {
            System.err.println("Le chemin renseigné ne correspond pas à un dossier ou n'existe pas.");
            return;
        }
        ServerNetwork server = new ServerNetwork();
    }
}
