package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Turn;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class WaitingTurnView implements UIController {

    @FXML
    private Label playerNameLabel;


    @FXML
    private ImageView spinnerImage;

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }


    @Override
    public void handleMessage(Message incomingMessage) {
        switch (ServerMessageType.valueOfLabel(incomingMessage.getCode())) {
            case TURN_FILE:
                Turn turnMessageAttachment = ((MessageAttachment<Turn>)incomingMessage).getAttachment();
                Platform.runLater(() -> {
                    setGif("resources/countdown.gif");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ControllerClient.initializeTurnView(playerNameLabel.getText());
                });
                break;
            default:
                System.out.println("Unwanted message");
                break;
        }
    }

    private void setGif(String path) {
        File file = new File(this.getClass().getResource("resources/waiting.gif").getPath());

        String localUrl = null;
        localUrl = file.toURI().toString();
        Image image = new Image(localUrl);

        spinnerImage.setImage(image);
    }

    @FXML
    private void initialize() {
        setGif("resources/waiting.gif");
    }

}
