package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Turn;
import io.potatoBlindTest.gameEngine.TurnFile;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WaitingTurnController implements UIController {

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
                TurnFile turnFile = ((MessageAttachment<TurnFile>)incomingMessage).getAttachment();
                File file = null;
                try {
                    file = File.createTempFile("temp", null);
                    byte[] byteArray = turnFile.getFileByteArray();
                    FileOutputStream mediaStream;
                    mediaStream = new FileOutputStream(file);
                    mediaStream.write(byteArray);
                } catch (IOException e) {
                    System.out.println("[WaitingTurnController] Error while retrieving the file");
                }
                File finalFile = file;
                Platform.runLater(() -> {
                    setGif("resources/countdown.gif");
                });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("[WaitingTurnController] Sleep interrupted");
                    return;
                }
                Platform.runLater(() -> {
                    ControllerClient.initializeTurnView(playerNameLabel.getText(), finalFile);
                });

                break;
            default:
                System.out.println("Unwanted message");
                break;
        }
    }

    private void setGif(String path) {
        File file = new File(this.getClass().getResource(path).getPath());

        String localUrl = null;
        localUrl = file.toURI().toString();
        Image image = new Image(localUrl);

        spinnerImage.setImage(image);
    }

    @FXML
    private void initialize() {
        setGif("resources/waiting.gif");
    }


    @Override
    public void handleErrorNetwork() {
        return;
    }
}
