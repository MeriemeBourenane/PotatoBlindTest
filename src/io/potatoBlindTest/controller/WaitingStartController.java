package io.potatoBlindTest.controller;

import io.potatoBlindTest.network.communication.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;


public class WaitingStartController implements UIController {

    @FXML
    private Label playerNameLabel;


    @FXML
    private ImageView spinnerImage;

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }


    @Override
    public void handleMessage(Message incomingMessage) {
        // TODO: handle Start game messages
    }

    @FXML
    private void initialize() {
        File file = new File(this.getClass().getResource("resources/potato-spinner.gif").getPath());

        String localUrl = null;
        localUrl = file.toURI().toString();
        Image image = new Image(localUrl);

        spinnerImage.setImage(image);
    }
}
