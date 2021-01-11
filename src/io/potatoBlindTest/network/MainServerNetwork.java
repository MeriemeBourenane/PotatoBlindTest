package io.potatoBlindTest.network;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainServerNetwork {

    private static String dataFolderPath;
    private static String ipAddressServer;


    public static String getDataFolderPath() {
        return dataFolderPath;
    }

    public static String getIpAddressServer() {
        return ipAddressServer;
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ClassNotFoundException {
        if (args.length != 2) {
            System.err.println("Missing arguments");
            return;
        }
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        if (!args[0].matches(PATTERN)) {
            return;
        }
        ipAddressServer = args[0];

        File folder = new File(args[1]);
        if (folder.exists() && folder.isDirectory()) {
            dataFolderPath = folder.getAbsolutePath();
        } else {
            System.err.println("Le chemin renseigné ne correspond pas à un dossier ou n'existe pas.");
            return;
        }
        ServerNetwork server = new ServerNetwork();
    }
}
