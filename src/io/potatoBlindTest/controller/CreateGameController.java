package io.potatoBlindTest.controller;

import io.potatoBlindTest.network.communication.Message;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class CreateGameController implements UIController {

    @FXML
    private Label playerNameLabel;

    @FXML
    private Button startButton;

    @FXML
    private Label nbPlayersLabel;

    @FXML
    private ListView<String> playersList;

    @FXML
    private void handleStartGame() {
        ControllerClient.initializeReadyView(playerNameLabel.getText(), null);
    }


    @FXML
    private void initialize() {
        ListProperty listProperty = new SimpleListProperty(this.playersList.getItems());
        this.nbPlayersLabel.textProperty().bind(listProperty.sizeProperty().asString());

        Thread thread = new Thread(() -> {

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    this.playersList.getItems().add("Patate");
                });
            }
        });
        thread.start();

    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }

    @Override
    public void handleMessage(Message incomingMessage) {
        // TODO: Handle Player messages
    }
}
