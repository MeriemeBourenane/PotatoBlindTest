package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.TurnResult;
import io.potatoBlindTest.network.communication.Message;
import javafx.application.Platform;
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
        ControllerClient.initializeWaitingTurnView(playerNameLabel.getText());

    }


    @FXML
    private void initialize() {

    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }

    @Override
    public void handleMessage(Message incomingMessage) {

        System.out.println("[ReadyController] Unwanted message");
        return;
    }

    public void setTurnResult(TurnResult turnResult) {
        if (playerNameLabel.getText().equals(turnResult.getTurnWinner())) {
            this.wonTurnLabel.setText("Félicitation, vous avez remporté(e) le tour !");
        } else {
            this.wonTurnLabel.setText("La réponse était " + turnResult.getAnswer() +
                    " et c'est " + turnResult.getTurnWinner() + " qui l'a trouvé !");
        }
        this.wonTurnLabel.setVisible(true);
    }

    @Override
    public void handleErrorNetwork() {
        Platform.runLater(() -> {
            ControllerClient.initializeMainMenuView("Erreur de serveur", playerNameLabel.getText());
        });
    }
}
