package io.potatoBlindTest.network;

import io.potatoBlindTest.utils.NetworkUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class ServerNetwork {

    // Client Tcp Connected
    static CopyOnWriteArrayList<ClientHandler> clientHandlers;

    // Server Games
    static CopyOnWriteArrayList<ServerGame> serverGames;

    private ExecutorService exectutorService;
    private CompletionService<Integer> completionService;
    protected ServerSocket serverSocket;

    public ServerNetwork() throws IOException {
        clientHandlers = new CopyOnWriteArrayList<>();
        serverGames = new CopyOnWriteArrayList<>();

        this.exectutorService = Executors.newFixedThreadPool(7);
        this.completionService = new ExecutorCompletionService<>(this.exectutorService);
        this.serverSocket = new ServerSocket(50_200);

        Thread threadReadMessage = new Thread(() -> this.runServer());
        threadReadMessage.start();

    }

    /**
     * Used to create a ServerGame
     * /!\ Can only me called by the constructor of ServerGame
     * @param serversocket
     * @throws IOException
     */
    protected ServerNetwork(ServerSocket serversocket) throws IOException {
        clientHandlers = new CopyOnWriteArrayList<>();
        serverGames = new CopyOnWriteArrayList<>();

        this.exectutorService = Executors.newFixedThreadPool(8);
        this.completionService = new ExecutorCompletionService<>(this.exectutorService);
        this.serverSocket = serversocket;

        Thread threadReadMessage = new Thread(() -> this.runServer());
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
                clientHandlers.add(clientHandler);
                this.completionService.submit(clientHandler);

            } catch (IOException e) {
                System.out.println("runServer : error IOEXception");
                e.printStackTrace();
            }
        }
    }

    public static void addServerGameToList(ServerGame serverGame) {

        serverGames.add(serverGame);
    }

    public void shutdown() {

        this.exectutorService.shutdown();
    }

    public ServerSocket getServerSocket() {

        return serverSocket;
    }

    public static CopyOnWriteArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public static CopyOnWriteArrayList<ServerGame> getServerGames() {
        return serverGames;
    }

    public CompletionService<Integer> getCompletionService() {
        return completionService;
    }

    public boolean isServerGame() {
        return false;
    }
}
