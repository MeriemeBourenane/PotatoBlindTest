package io.potatoBlindTest.controller;

import io.potatoBlindTest.network.communication.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ReadyController implements UIController {

    @FXML
    private Label playerNameLabel;

    @FXML
    private Button readyButton;

    @FXML
    private Label wonTurnLabel;


    @FXML
    private void handleReady() {
        ControllerClient.initializeTurnView(playerNameLabel.getText());
    }


    @FXML
    private void initialize() {

    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }

    public void setTurnWinner(String turnWinner) {
        this.wonTurnLabel.setText(turnWinner);
        this.wonTurnLabel.setVisible(true);
    }

    @Override
    public void handleMessage(Message incomingMessage) {
        return;
    }
}
