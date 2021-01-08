package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Game;
import io.potatoBlindTest.network.communication.Message;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class SearchGamesController implements UIController {

    @FXML
    private Label playerNameLabel;

    @FXML
    private TableView<Game> gameTable;

    @FXML
    private TableColumn<Game, String> gameCreatorColumn;

    @FXML
    private TableColumn<Game, Integer> gameNbPlayersColumn;

    @FXML
    private Button joinButton;

    @FXML
    void handleJoin() {
        ControllerClient.initializeReadyView(playerNameLabel.getText(), null);
    }

    public void setGameValue(List<Game> gameList) {
        ObservableList observableList = FXCollections.observableList(gameList);
        this.gameTable.setItems(observableList);
    }


    @FXML
    private void initialize() {

        gameCreatorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreator()));
        gameNbPlayersColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNbPlayers()).asObject());

    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }

    @Override
    public void handleMessage(Message incomingMessage) {
        return;
    }
}