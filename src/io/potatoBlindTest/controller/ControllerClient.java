package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.ListGames;
import io.potatoBlindTest.gameEngine.TableScore;
import io.potatoBlindTest.gameEngine.TurnResult;
import io.potatoBlindTest.gameEngine.typeOfMedia.TypeOfMedia;
import io.potatoBlindTest.network.ClientNetwork;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class ControllerClient extends Application {

    private static Stage primaryStage;
    private static UIController currentController;
    private static ClientNetwork transport;
    private static String ipAddressServer;


    public static UIController getCurrentController() {
        return currentController;
    }

    public static ClientNetwork getTransport() {
        return transport;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Load a new scene
     *
     * @param fxml the fxml file to load
     * @return return the loader for future customizations
     */
    public static FXMLLoader changeScene(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerClient.class.getResource(fxml));

        try {
            /**
             * The scene may be null, it is the case for the starting view
             */
            Scene scene = ControllerClient.getPrimaryStage().getScene();
            if (scene != null) {
                ControllerClient.getPrimaryStage().getScene().setRoot(loader.load());
            } else {
                scene = new Scene(loader.load());
                ControllerClient.getPrimaryStage().setScene(scene);
                ControllerClient.getPrimaryStage().setOnCloseRequest(e -> shutdown());
                getPrimaryStage().setTitle("Potato's Blind Test");
                getPrimaryStage().setResizable(false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Change the controller
         */
        currentController = loader.getController();

        return loader;
    }

    /**
     * Show the scene
     */
    public static void showScene() {
        ControllerClient.getPrimaryStage().show();
    }

    public static void shutdown() {
        System.out.println("[DEBUG] Closing Application");
        if (transport != null) {
            transport.closeNetwork();
        }
        System.out.println("[DEBUG] Application closed");
    }

    public static void initializeCreateGameView(String playerName) {
        FXMLLoader loader = ControllerClient.changeScene("resources/createGameView.fxml");

        loader.<CreateGameController>getController().setPlayerName(playerName);

        /**
         * Request the creation of the game at the server
         */
        try {
            transport = new ClientNetwork();
        } catch (IOException e) {
            initializeMainMenuView("Impossible de contacter le serveur", playerName);
            return;
        }

        switch (transport.sendCreateGameMessage(playerName)) {
            case OK:
                ControllerClient.showScene();
                break;
            case ERROR_SERVER:
                initializeMainMenuView("Erreur interne du serveur", playerName);
                break;
            case FORBIDDEN:
                initializeMainMenuView("Opération non autorisée par le serveur", playerName);
                break;
            case NOT_FOUND:
                initializeMainMenuView("Opération non traitée par le serveur", playerName);
                break;
            default:
                initializeMainMenuView("Erreur interne", playerName);
                break;
        }


    }

    public static void initializeSearchGamesView(String playerName) {
        FXMLLoader loader = ControllerClient.changeScene("resources/joinGameView.fxml");

        loader.<SearchGamesController>getController().setPlayerName(playerName);

        /*
         * Request the creation of the game at the server
         */
        try {
            transport = new ClientNetwork();
        } catch (IOException e) {
            initializeMainMenuView("Impossible de contacter le serveur", playerName);
            return;
        }

        ListGames listGames = transport.sendSearchGamesMessage();

        if (listGames == null) {
            initializeMainMenuView("Erreur lors de la récupération des parties", playerName);
        } else {
            loader.<SearchGamesController>getController().setGameValue(listGames.getListGames());
            ControllerClient.showScene();
        }


    }

    public static void initializeReadyView(String playerName, TurnResult turnResult) {

        FXMLLoader loader = ControllerClient.changeScene("resources/readyView.fxml");

        loader.<ReadyController>getController().setPlayerName(playerName);
        if (turnResult != null) {
            loader.<ReadyController>getController().setTurnResult(turnResult);
        }

        ControllerClient.showScene();

    }

    public static void initializeTurnView(String playerName, File file, TypeOfMedia typeOfMedia) {
        FXMLLoader loader = ControllerClient.changeScene("resources/turnView.fxml");

        loader.<TurnController>getController().setPlayerName(playerName);

        switch (typeOfMedia) {
            case IMAGE:
                loader.<TurnController>getController().setImage(file);
                break;
            case AUDIO:
                loader.<TurnController>getController().setAudio(file);
                break;
            default:
                break;
        }

        ControllerClient.showScene();
    }

    public static void initializeScoreView(String playerName, TableScore tableScore) {
        FXMLLoader loader = ControllerClient.changeScene("resources/scoreView.fxml");

        loader.<ScoreController>getController().setPlayerName(playerName);
        loader.<ScoreController>getController().setGameScores(tableScore.getPlayers());

        ControllerClient.showScene();

    }

    public static void initializeWaitingStartView(String playerName) {
        FXMLLoader loader = ControllerClient.changeScene("resources/waitingStartView.fxml");

        loader.<WaitingStartController>getController().setPlayerName(playerName);

        ControllerClient.showScene();

    }

    public static void initializeWaitingTurnView(String playerName) {
        FXMLLoader loader = ControllerClient.changeScene("resources/waitingTurnView.fxml");

        loader.<WaitingTurnController>getController().setPlayerName(playerName);

        switch (ControllerClient.getTransport().sendReadyPlayerMessage()) {
            case OK:
                ControllerClient.showScene();
                break;
            case ERROR_SERVER:
                ControllerClient.initializeMainMenuView("Erreur interne du serveur", playerName);
                break;
            case FORBIDDEN:
                ControllerClient.initializeMainMenuView("Opération non autorisée par le serveur", playerName);
                break;
            case NOT_FOUND:
                ControllerClient.initializeMainMenuView("Opération non traitée par le serveur", playerName);
                break;
            default:
                ControllerClient.initializeMainMenuView("Erreur interne", playerName);
                break;
        }



    }

    /**
     * Display the welcome menu
     *
     * @param serverError null if no error
     */
    public static void initializeMainMenuView(String serverError, String playerName) {
        FXMLLoader loader = ControllerClient.changeScene("resources/welcomeView.fxml");

        /**
         *  Set the server error and display it
         */
        if (serverError != null) {
            loader.<RootController>getController().setServerLabel(serverError);
            loader.<RootController>getController().showServerLabel();
        }

        if (playerName != null) {
            loader.<RootController>getController().setPlayerName(playerName);
        }

        if (transport != null) {
            System.out.println("[ClientController] Closing the network");
            transport.closeNetwork();
            transport = null;
        }

        ControllerClient.showScene();
    }

    public static String getIpAddressServer() {
        return ipAddressServer;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("/!\\ MISSING ARGUMENTS : SERVER IP ADDRESS ... /!\\");
            return;
        }

        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        if (!args[0].matches(PATTERN)) {
            return;
        }
        ipAddressServer = args[0];
        System.out.println(ipAddressServer);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerClient.primaryStage = primaryStage;
        ControllerClient.initializeMainMenuView(null, null);
    }
}
