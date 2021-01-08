package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.TableScore;
import io.potatoBlindTest.gameEngine.TurnResult;
import io.potatoBlindTest.network.communication.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

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
    void handleKeyPressed(KeyEvent event) {
        this.answerField.getStyleClass().remove("wrong");

        switch (event.getCode()) {
            case ENTER:
                Player scorePlayer1 = new Player(playerNameLabel.getText(), 12, 2, null);
                Player scorePlayer2 = new Player("MagroffPatate", 11, 1, null);
                TableScore tableScore = new TableScore(new CopyOnWriteArrayList(List.of(scorePlayer2, scorePlayer1)));
                ControllerClient.initializeScoreView(playerNameLabel.getText(), tableScore);
                break;
            case ESCAPE:
                ControllerClient.initializeReadyView(playerNameLabel.getText(), new TurnResult("Patate", "MagroffPatate"));
                break;
            case C:
                this.answerField.getStyleClass().add("wrong");
                this.answerField.clear();
                break;
            default:
                break;

        }
    }


    @FXML
    private void initialize() {
        File file = new File(this.getClass().getResource("resources/potato.png").getPath());

        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);

            imageView.setImage(image);
        } catch (MalformedURLException e) {
            System.err.println("Erreur");
        }


    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }


    @Override
    public void handleMessage(Message incomingMessage) {
        // TODO: Handle EndTurn and EndGame messages
    }
}
