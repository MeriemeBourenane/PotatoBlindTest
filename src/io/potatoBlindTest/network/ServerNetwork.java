package io.potatoBlindTest.network;

import io.potatoBlindTest.utils.NetworkUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class ServerNetwork {

    // Server Games
    public CopyOnWriteArrayList<ServerGame> serverGames;

    private ExecutorService exectutorService;
    private CompletionService<Integer> completionService;
    protected ServerSocket serverSocket;
    protected Thread threadReadMessage = null;

    public ServerNetwork() throws IOException {
        serverGames = new CopyOnWriteArrayList<>();

        this.exectutorService = Executors.newFixedThreadPool(7);
        this.completionService = new ExecutorCompletionService<>(this.exectutorService);
        this.serverSocket = new ServerSocket(20_200);

        this.threadReadMessage = new Thread(() -> this.runServer());
        threadReadMessage.start();

    }

    /**
     * Used to create a ServerGame
     * /!\ Can only me called by the constructor of ServerGame
     * @param serversocket
     * @throws IOException
     */
    protected ServerNetwork(ServerSocket serversocket) throws IOException {
        serverGames = new CopyOnWriteArrayList<>();

        this.exectutorService = Executors.newFixedThreadPool(8);
        this.completionService = new ExecutorCompletionService<>(this.exectutorService);
        this.serverSocket = serversocket;

        this.threadReadMessage = new Thread(() -> this.runServer());
        threadReadMessage.start();

    }

    /**
     * Method to run the Server Network
     */
    public void runServer() {

        while (true) {
            try {
                System.out.println("Server Network is running on  port " + this.serverSocket.getLocalPort() +
                        " at ip address " + InetAddress.getLocalHost().getHostAddress());

                Socket socketClient = this.serverSocket.accept();

                System.out.println("Server as accepted a new client ... ");

                // Lance un thread par Socket client
                ClientHandler clientHandler = new ClientHandler(socketClient, this);
                this.completionService.submit(clientHandler);

            } catch (IOException e) {
                System.out.println("runServer : error IOEXception");
                e.printStackTrace();
            }
        }
    }

    public void addServerGameToList(ServerGame serverGame) {
        serverGames.add(serverGame);
    }

    public void shutdown() {
        System.out.println("[ServerNetwork] Shutdown of Server");
        if (this.threadReadMessage != null) {
            System.out.println("[ServerNetwork] stop the read thread");
            this.threadReadMessage.interrupt();
        }
        this.exectutorService.shutdown();
        System.out.println("[ServerNetwork] Server is shutdown");

    }

    public ServerSocket getServerSocket() {

        return serverSocket;
    }

    public CopyOnWriteArrayList<ServerGame> getServerGames() {
        return serverGames;
    }

    public CompletionService<Integer> getCompletionService() {
        return completionService;
    }

    public boolean isServerGame() {
        return false;
    }
}
