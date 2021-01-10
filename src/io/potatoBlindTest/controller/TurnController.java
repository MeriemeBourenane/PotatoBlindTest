package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.TableScore;
import io.potatoBlindTest.gameEngine.TurnResult;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TurnController implements UIController {

    @FXML
    private Label playerNameLabel;

    @FXML
    private TextField answerField;

    @FXML
    private ImageView imageView;

    @FXML
    private MediaView audioView;

    @FXML
    void handleKeyPressed(KeyEvent event) {
        this.answerField.getStyleClass().remove("wrong");

        switch (event.getCode()) {
            case ENTER:
                Boolean response = ControllerClient.getTransport().sendAnswerMessage(this.answerField.getText());
                if (response) {
                    this.answerField.getStyleClass().add("right");
                } else {
                    this.answerField.getStyleClass().add("wrong");
                    this.answerField.clear();
                }
            default:
                break;

        }
    }


    @FXML
    private void initialize() {
    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }


    public void setImage(File file) {
        String localUrl = null;
        localUrl = file.toURI().toString();
        Image image = new Image(localUrl);

        imageView.setImage(image);
        imageView.setVisible(true);
    }

    public void setAudio(File file) {
        String localUrl = null;
        localUrl = file.toURI().toString();
        MediaPlayer audioPlayer = new MediaPlayer(new Media(localUrl));
        audioPlayer.setAutoPlay(true);

        audioView.setMediaPlayer(audioPlayer);
        setImage(new File(ControllerClient.class.getResource("resources/potato-music-player.gif").getPath()));
    }

    @Override
    public void handleMessage(Message incomingMessage) {
        // TODO: Handle EndTurn and EndGame messages

        switch (ServerMessageType.valueOfLabel(incomingMessage.getCode())) {
            case TURN_RESULT:
                System.out.println("[DEBUG] Received end of turn");
                TurnResult turnResult = ((MessageAttachment<TurnResult>) incomingMessage).getAttachment();
                Platform.runLater(() -> {
                    if (this.audioView.getMediaPlayer() != null) {
                        this.audioView.getMediaPlayer().stop();
                    }
                    ControllerClient.initializeReadyView(playerNameLabel.getText(), turnResult);
                });
                break;
            case END_GAME_RESULTS:
                System.out.println("[DEBUG] Received end of game");
                TableScore tableScore = ((MessageAttachment<TableScore>) incomingMessage).getAttachment();
                Platform.runLater(() -> {
                    if (this.audioView.getMediaPlayer() != null) {
                        this.audioView.getMediaPlayer().stop();
                    }
                    ControllerClient.initializeScoreView(playerNameLabel.getText(), tableScore);
                });
                break;
            default:
                System.out.println("Unwanted message");
                break;
        }
    }

    @Override
    public void handleErrorNetwork() {
        return;
    }
}
