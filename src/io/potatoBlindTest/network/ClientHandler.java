package io.potatoBlindTest.network;

import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMesssageMapping;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.notification.OberverClientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Callable, OberverClientHandler {

    private Socket socket;
    private CopyOnWriteArrayList<ClientHandler> clientHandlers;
    private CopyOnWriteArrayList<ServerGame> serverGames;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientHandler(Socket socketClient,
                         CopyOnWriteArrayList<ClientHandler> clientHandlers,
                         CopyOnWriteArrayList<ServerGame> serverGames) throws IOException {

        this.socket = socket;
        this.clientHandlers = clientHandlers;
        this.serverGames = serverGames;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public Integer call() throws Exception {
        while(true) {
            try {
                // On lit le Message
                System.out.println("waiting message");
                Message messageReceived = (Message) this.ois.readObject();

                Message messageToSend = ClientMesssageMapping.getMapping().get(messageReceived.getCode()).handle(
                        messageReceived.hasAttachment() ? ((MessageAttachment) messageReceived).getAttachment() : null,
                        this);

                this.oos.writeObject(messageToSend);

            } catch (IOException e) {
                System.out.println("Server Client Handler can't read messages : IO ...");
                System.out.println("CLIENT CLOSED : maybe the connection has been closed !");
                this.socket.close();
                return 1;
            }
        }
    }

    @Override
    public void notify(Message messageNotify) {
        try {
            System.out.println("Notifying a Client Handler ...");
            this.oos.writeObject(messageNotify);

        } catch (IOException e) {
            System.out.println("Can't notify the Client by the ClientHandler : IO ...");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public CopyOnWriteArrayList<ServerGame> getServerGames() {
        return serverGames;
    }

    public CopyOnWriteArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

}
