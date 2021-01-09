package io.potatoBlindTest.service;

import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.utils.NetworkUtils;

import java.io.IOException;
import java.net.ServerSocket;

public class Network {

    /**
     * Create the ServerSocket for the ServerGame
     * @return ServerSocket
     * returns null if an error occured                     -> no ServerGame created
     * returns the new ServerSocket of the new ServerGame   -> new ServerGame created
     */
    public static ServerGame createServerGame() {
        try {
            ServerSocket serverSocketForGame = NetworkUtils.create();
            ServerGame newServerGame = new ServerGame(serverSocketForGame);
            return newServerGame;

        } catch (IOException e) {
            System.out.println("Can't create a new server game : maybe ");
            return null;
        }
    }

}
