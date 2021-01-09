package io.potatoBlindTest.network.handlerMessage.serverNetwork.notification;

import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.communication.Message;

import java.util.concurrent.CopyOnWriteArrayList;

public class SubjectClientHandler {

    public void notifyAll(CopyOnWriteArrayList<ClientHandler> clientsToNotify, Message messageNotify) {
        for (ClientHandler clientHandler : clientsToNotify) {
            System.out.println("Notifying ...");
            clientHandler.notify(messageNotify);
        }
    }

    public void notifyOne(ClientHandler clientToNotify, Message messageNotify) {
        System.out.println("Notifying ...");
        clientToNotify.notify(messageNotify);
    }
}
