package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Game;
import io.potatoBlindTest.gameEngine.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ControllerClient extends Application {

    private static Stage primaryStage;
    private UIController currentController;

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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loader;
    }

    /**
     * Show the scene
     */
    public static void showScene() {
        ControllerClient.getPrimaryStage().show();
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
        Game game1 = new Game("Ma groffPatate", 42);
        Game game2 = new Game("Ma groffPatate 2", 2);
        loader.<SearchGamesController>getController().setGameValue(List.of(game1, game2));

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
