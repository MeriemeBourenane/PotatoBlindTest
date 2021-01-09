package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Game;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.TableScore;
import io.potatoBlindTest.gameEngine.TurnResult;
import io.potatoBlindTest.network.ClientNetwork;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ControllerClient extends Application {

    private static Stage primaryStage;
    private static UIController currentController;
    private static ClientNetwork transport;

    public static ClientNetwork getTransport() {
        return transport;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Load a new scene
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Change the controller
         */
        currentController = loader.<UIController>getController();

        return loader;
    }

    /**
     * Show the scene
     */
    public static void showScene() {
        ControllerClient.getPrimaryStage().show();
    }

    public static void shutdown() {
        // TODO: Close all resources
        System.out.println("[DEBUG] Application closed");
    }

    public static void initializeCreateGameView(String playerName) {
        FXMLLoader loader = ControllerClient.changeScene("resources/createGameView.fxml");

        loader.<CreateGameController>getController().setPlayerName(playerName);

        ControllerClient.showScene();

    }

    public static void initializeSearchGamesView(String playerName) {
        FXMLLoader loader = ControllerClient.changeScene("resources/joinGameView.fxml");

        loader.<SearchGamesController>getController().setPlayerName(playerName);
        /**
         * TODO: Replace those values
         */
        Game game1 = new Game("Ma groffPatate", 42, "192.168.1.2", 5000);
        Game game2 = new Game("Ma groffPatate 2", 2,"192.168.1.2", 5000);
        loader.<SearchGamesController>getController().setGameValue(List.of(game1, game2));

        ControllerClient.showScene();

    }

    public static void initializeReadyView(String playerName, TurnResult turnResult) {

        FXMLLoader loader = ControllerClient.changeScene("resources/readyView.fxml");

        loader.<ReadyController>getController().setPlayerName(playerName);
        if (turnResult != null) {
            loader.<ReadyController>getController().setTurnResult(turnResult);
        }

        ControllerClient.showScene();

    }

    public static void initializeTurnView(String playerName) {
        FXMLLoader loader = ControllerClient.changeScene("resources/turnView.fxml");

        loader.<TurnController>getController().setPlayerName(playerName);

        ControllerClient.showScene();
    }

    public static void initializeScoreView(String playerName, TableScore tableScore) {
        FXMLLoader loader = ControllerClient.changeScene("resources/scoreView.fxml");

        loader.<ScoreController>getController().setPlayerName(playerName);
        /**
         * TODO: Replace those values
         */
        loader.<ScoreController>getController().setGameScores(tableScore.getPlayers());

        ControllerClient.showScene();

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

        ControllerClient.showScene();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerClient.primaryStage = primaryStage;
        ControllerClient.initializeMainMenuView(null, null);
    }
}
