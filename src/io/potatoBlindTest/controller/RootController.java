package io.potatoBlindTest.controller;

import io.potatoBlindTest.network.communication.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Random;

public class RootController implements UIController {

    @FXML
    private TextField playerField;

    @FXML
    private Button createGameButton;

    @FXML
    private Button searchGamesButton;

    @FXML
    private Label serverLabel;

    @FXML
    private void handleCreateGame() {
        setPlayerNameIfEmpty();
        ControllerClient.initializeCreateGameView(playerField.getText());
    }

    @FXML
    private void handleSearchGames() {
        setPlayerNameIfEmpty();
        ControllerClient.initializeSearchGamesView(playerField.getText());
    }

    private void setPlayerNameIfEmpty() {
        if (this.playerField.getText().isEmpty()) {
            /**
             * Set the playerName
             */
            Random random = new Random();
            playerField.setText("GroffPatate-" + random.nextInt(10000));
        }
    }

    public void setServerLabel(String serverError) {
        this.serverLabel.setText(serverError);
    }

    public void showServerLabel() {
        this.serverLabel.setVisible(true);
    }

    public void setPlayerName(String playerName) {
        playerField.setText(playerName);
    }

    @FXML
    private void initialize() {

    }

    @Override
    public void handleMessage(Message incomingMessage) {
        return;
    }
}
