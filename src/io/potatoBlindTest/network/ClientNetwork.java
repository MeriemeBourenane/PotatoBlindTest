package io.potatoBlindTest.network;

import io.potatoBlindTest.controller.ControllerClient;
import io.potatoBlindTest.network.communication.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientNetwork {

    final Socket socket;
    final InetAddress ip;
    // test local
    final int serverPort = 50_000;

    // port Serveur Ubuntu :
    // final int serverPort = 40_000;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Message response;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private ControllerClient controllerClient;

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

            Thread threadReadMessage = new Thread(() -> this.readContinuouslyMessages());
            threadReadMessage.start();
        } catch (IOException e) {
            System.out.println("Can't run the client : IO ...");
        }
    }

    /**
     * Method to send a Message to the server
     * @param sendMessage Message
     * @return Message
     * @throws InterruptedException e
     */
    public Message sendMessage(Message sendMessage) throws InterruptedException {
        Message response = null;

        try {
            System.out.println("writing sendMessage...");
            oos.writeObject(sendMessage);

        } catch (IOException e) {
            System.out.println("Writing error sendMessage");
            e.printStackTrace();
        }

        // Waiting until response variable is set
        this.lock.lock();
        this.condition.await();
        if (this.response == null) {
            System.out.println("sendMessage An error occurred : response null");
        } else {
            response = new Message(this.response);
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
                e.printStackTrace();

                break;
            }
        }
    }
}
