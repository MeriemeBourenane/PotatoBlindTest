package io.potatoBlindTest.network;

import io.potatoBlindTest.controller.ControllerClient;
import io.potatoBlindTest.gameEngine.ListGames;
import io.potatoBlindTest.gameEngine.NameCreator;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.communication.additionalAttachements.SpecificServerGame;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientNetwork {

    private Socket socket;
    private InetAddress ip;
    // test local
    private int serverPort = 50_200;

    // port Serveur Ubuntu :
    // final int serverPort = 40_000;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Thread threadReadMessage;
    private Message response;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private ControllerClient controllerClient;

    public ClientNetwork() throws IOException {
        this.controllerClient = controllerClient;
        this.ip = InetAddress.getByName("127.0.0.1");
        this.socket = new Socket(ip, serverPort);
        this.runClient();
    }

    public ClientNetwork(ControllerClient controllerClient) throws IOException {
        this.controllerClient = controllerClient;
        this.ip = InetAddress.getByName("127.0.0.1");
        this.socket = new Socket(ip, serverPort);
        this.runClient();
    }

    /**
     * Method to run the Client
     */
    private void runClient() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            threadReadMessage = new Thread(() -> this.readContinuouslyMessages());
            threadReadMessage.start();

        } catch (IOException e) {
            System.out.println("Can't run the client : IO ... ");
        }
    }

    /**
     * Method to send a Message to the server
     * @param sendMessage Message
     * @return Message
     * @throws InterruptedException e
     */
    public Message sendMessage(Message sendMessage) {
        Message response = null;

        try {
            System.out.println("writing sendMessage...");
            oos.writeObject(sendMessage);

        } catch (IOException e) {
            System.out.println("Writing error sendMessage");
            e.printStackTrace();
        }

        // Waiting until response variable is set
        System.out.println("Waiting until the response varibale is set ...");
        this.lock.lock();
        try {
            this.condition.await();
        } catch (InterruptedException e) {
            return null;
        }
        if (this.response == null) {
            System.out.println("sendMessage An error occurred : response null");
        } else {
            if (this.response.hasAttachment()) {
                response = new MessageAttachment((MessageAttachment) this.response);
            } else {
                response = new Message(this.response);
            }
        }
        this.response = null;
        this.lock.unlock();
        return response;
    }

    /**
     * Method to read continuously entrering Message (s) coming from the server
     */
    private void readContinuouslyMessages() {
        while (true) {
            try {
                Message messageReceived = (Message) ois.readObject();

                if (messageReceived.getCode() >= 200 && messageReceived.getCode() <= 500) {
                    // Notify with signalAll that response has been set
                    this.lock.lock();
                    System.out.println("readContinuouslyMessages into lock");
                    this.response = messageReceived;
                    this.condition.signalAll();
                    this.lock.unlock();
                } else {
                    // TODO :
                    //  - message from server
                    //  - notify ControllerClient of the reception of the messsage using Oberver Pattern
                    //  - (example : call method setChannels to update channels attribut)
                    // ServerMessageHandler(messageReceived);

                    System.out.println("Message coming from server , maybe update channel");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("SERVER CLOSED !");

                break;
            }
        }
    }

    public void changeConnnection(int port, String ipAddress) {
        // Change the conneciton with the server : connect to Server Game
        // - stop the Thread that read continuously messages
        // - set attributs ip, serverPort and socket
        // - run the client network
        this.threadReadMessage.interrupt();
        try {
            this.ois.close();
            this.oos.close();
            this.socket.close();

            this.ip = InetAddress.getByName(ipAddress);
            this.serverPort = port;
            this.socket = new Socket(ip, port);

            this.runClient();
        } catch (IOException e) {
            System.out.println("Closing the IO streams and the socket ...");
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return socket;
    }


    public ServerMessageType sendCreateGameMessage(String creatorName) {
        Message message = new Message(ClientMessageType.CREATE_GAME.getValue());
        Message receivedMessage = this.sendMessage(message);

        if (receivedMessage.getCode() != ServerMessageType.OK.getValue())  {
            return ServerMessageType.valueOfLabel(receivedMessage.getCode());
        }

        MessageAttachment<SpecificServerGame> answer = (MessageAttachment) receivedMessage;

        this.changeConnnection(answer.getAttachment().getPort(), answer.getAttachment().getIpAddress());
        NameCreator nameCreator = new NameCreator(creatorName);
        message = new MessageAttachment<NameCreator>(ClientMessageType.JOIN_AS_CREATOR.getValue(), nameCreator);

        receivedMessage = this.sendMessage(message);

        return ServerMessageType.valueOfLabel(receivedMessage.getCode());
    }

    public ListGames sendSearchGamesMessage() {
        Message message = new Message(ClientMessageType.GET_ALL_GAMES.getValue());
        Message receivedMessage = this.sendMessage(message);

        if (receivedMessage.getCode() != ServerMessageType.OK.getValue())  {
            return null;
        }

        MessageAttachment<ListGames> answer = (MessageAttachment) this.sendMessage(message);

        return answer.getAttachment();
    }
}
