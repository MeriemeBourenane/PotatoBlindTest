package io.potatoBlindTest.network;

import io.potatoBlindTest.controller.ControllerClient;
import io.potatoBlindTest.gameEngine.*;
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
    private int serverPort = 20_200;

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
        this.ip = InetAddress.getByName(ControllerClient.getIpAddressServer());
        this.socket = new Socket(ip, serverPort);
        this.runClient();
    }

    public ClientNetwork(ControllerClient controllerClient) throws IOException {
        this.controllerClient = controllerClient;
        this.ip = InetAddress.getByName(ControllerClient.getIpAddressServer());
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
     * @return Message null if client error, message from server otherwise
     * @throws InterruptedException e
     */
    public Message sendMessage(Message sendMessage) {
        Message response = null;

        try {
            System.out.println("[ClientNetwork] Sending " + sendMessage);
            oos.writeObject(sendMessage);

        } catch (IOException e) {
            System.out.println("[ClientNetwork] Sending failed");
            return null;
        }

        // Waiting until response variable is set
        System.out.println("[ClientNetwork] Waiting until the response variable is set ...");
        this.lock.lock();
        try {
            this.condition.await();
        } catch (InterruptedException e) {
            System.out.println("[ClientNetwork] error in wait sendMessage ...");
            e.printStackTrace();
            return null;
        }
        if (this.response == null) {
            System.out.println("[ClientNetwork] The response is null");
        } else {
            if (this.response.hasAttachment()) {
                response = new MessageAttachment((MessageAttachment) this.response);
            } else {
                response = new Message(this.response);
            }
        }
        System.out.println("[ClientNetwork] Received " + response);
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
                    System.out.println("[ClientNetwork] readContinuouslyMessages into lock");
                    System.out.println("[ClientNetwork] code received : " + messageReceived.getCode());
                    this.response = messageReceived;
                    this.condition.signalAll();
                    this.lock.unlock();
                } else {
                    System.out.println("[ClientNetwork] code received : " + messageReceived.getCode());
                    if (messageReceived.hasAttachment()) {
                        System.out.println("[ClientNetwork] attachment received : " + ((MessageAttachment)messageReceived).getAttachment());
                    }

                    ControllerClient.getCurrentController().handleMessage(messageReceived);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[ClientNetwork] SERVER CLOSED !");
                e.printStackTrace();
                System.out.println("connected : " + this.socket.isBound() + " shutdown : " + this.socket.isInputShutdown() + " " + this.socket.isOutputShutdown());
                ControllerClient.getCurrentController().handleErrorNetwork();
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

    public void closeNetwork() {
        try {
            this.threadReadMessage.interrupt();
            this.ois.close();
            this.oos.close();
            this.socket.close();
        } catch (IOException e) {
            System.out.println("[ClientNetwork] Error while closing client");
        }

    }


    public ServerMessageType sendCreateGameMessage(String creatorName) {
        Message message = new Message(ClientMessageType.CREATE_GAME.getValue());
        Message receivedMessage = this.sendMessage(message);

        if (receivedMessage == null) {
            return ServerMessageType.ERROR_SERVER;
        } else if (receivedMessage.getCode() != ServerMessageType.OK.getValue()) {
            return ServerMessageType.valueOfLabel(receivedMessage.getCode());
        }

        MessageAttachment<SpecificServerGame> answer = (MessageAttachment) receivedMessage;

        this.changeConnnection(answer.getAttachment().getPort(), answer.getAttachment().getIpAddress());
        NameCreator nameCreator = new NameCreator(creatorName);
        message = new MessageAttachment<NameCreator>(ClientMessageType.JOIN_AS_CREATOR.getValue(), nameCreator);

        receivedMessage = this.sendMessage(message);

        if (receivedMessage == null) {
            return ServerMessageType.ERROR_SERVER;
        }

        return ServerMessageType.valueOfLabel(receivedMessage.getCode());
    }

    public ListGames sendSearchGamesMessage() {
        Message message = new Message(ClientMessageType.GET_ALL_GAMES.getValue());
        Message receivedMessage = this.sendMessage(message);

        if (receivedMessage == null) {
            return null;
        } else if (receivedMessage.getCode() != ServerMessageType.OK.getValue())  {
            return null;
        }

        MessageAttachment<ListGames> answer = (MessageAttachment) this.sendMessage(message);

        if (receivedMessage == null) {
            return null;
        }
        return answer.getAttachment();
    }

    public NamePlayer sendJoinAsPlayerMessage(String playerName, String ip, int port) {
        this.changeConnnection(port, ip);

        Message message = new MessageAttachment<NamePlayer>(ClientMessageType.JOIN_AS_PLAYER.getValue(), new NamePlayer(playerName));
        Message receivedMessage = this.sendMessage(message);

        if (receivedMessage == null) {
            return null;
        } else if (receivedMessage.getCode() != ServerMessageType.OK.getValue())  {
            return null;
        }

        MessageAttachment<NamePlayer> answer = (MessageAttachment<NamePlayer>) receivedMessage;

        return answer.getAttachment();
    }

    public ServerMessageType sendStartGameMessage() {
        Message message = new Message(ClientMessageType.START_THE_GAME.getValue());
        Message receivedMessage = this.sendMessage(message);

        if (receivedMessage == null) {
            return null;
        }

        return ServerMessageType.valueOfLabel(receivedMessage.getCode());

    }

    public ServerMessageType sendReadyPlayerMessage() {
        Message message = new Message(ClientMessageType.READY_PLAYER.getValue());
        Message receivedMessage = this.sendMessage(message);

        if (receivedMessage == null) {
            return null;
        }

        return  ServerMessageType.valueOfLabel(receivedMessage.getCode());
    }

    public Boolean sendAnswerMessage(String answer) {
        Message message = new MessageAttachment<Answer>(ClientMessageType.SUBMIT_ANSWER.getValue(), new Answer(answer));
        Message receivedMessage = this.sendMessage(message);

        if (receivedMessage == null) {
            return null;
        } else if (receivedMessage.getCode() != ServerMessageType.OK.getValue())  {
            return null;
        }

        IsCorrectAnswer isCorrectAnswer = ((MessageAttachment<IsCorrectAnswer>) receivedMessage).getAttachment();

        return  isCorrectAnswer.isCorrect();
    }
}
