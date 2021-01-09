package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.NameCreator;
import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
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
        switch (ControllerClient.getTransport().sendStartGameMessage()) {
            case OK:
                ControllerClient.initializeReadyView(playerNameLabel.getText(), null);
                break;
            case ERROR_SERVER:
                ControllerClient.initializeMainMenuView("Erreur interne du serveur", playerNameLabel.getText());
                break;
            case FORBIDDEN:
                ControllerClient.initializeMainMenuView("Opération non autorisée par le serveur", playerNameLabel.getText());
                break;
            case NOT_FOUND:
                ControllerClient.initializeMainMenuView("Opération non traitée par le serveur", playerNameLabel.getText());
                break;
            default:
                ControllerClient.initializeMainMenuView("Erreur interne", playerNameLabel.getText());
                break;
        }
    }


    @FXML
    private void initialize() {
        ListProperty listProperty = new SimpleListProperty(this.playersList.getItems());
        this.nbPlayersLabel.textProperty().bind(listProperty.sizeProperty().asString());

    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }

    @Override
    public void handleMessage(Message incomingMessage) {
        // TODO: Handle Player messages
        switch (ServerMessageType.valueOfLabel(incomingMessage.getCode())) {
            case NEW_PLAYER_IN_GAME:
                NamePlayer newPlayerName = ((MessageAttachment<NamePlayer>) incomingMessage).getAttachment();
                Platform.runLater(() -> {
                    this.playersList.getItems().add(newPlayerName.getName());
                });
                break;
            default:
                System.out.println("Unwanted message");
                break;
        }
    }
}
