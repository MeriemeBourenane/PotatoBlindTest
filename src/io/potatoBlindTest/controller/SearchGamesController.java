package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Game;
import io.potatoBlindTest.gameEngine.ListGames;
import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.network.communication.Message;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
        Game selectedGame = this.gameTable.getSelectionModel().getSelectedItem();
        NamePlayer namePlayer = ControllerClient.getTransport().sendJoinAsPlayerMessage(playerNameLabel.getText(),
                                                                selectedGame.getIpAddress(),
                                                                selectedGame.getPort());
        if (namePlayer == null) {
            ControllerClient.initializeMainMenuView("Erreur lors de la connexion à la partie",
                                                    playerNameLabel.getText());
            return;
        }

        ControllerClient.initializeWaitingStartView(namePlayer.getName());
    }

    @FXML
    void handleRefresh() {
        ListGames listGames = ControllerClient.getTransport().sendSearchGamesMessage();

        if (listGames == null) {
            ControllerClient.initializeMainMenuView("Erreur lors de la récupération des parties",
                    playerNameLabel.getText());
        } else {
            setGameValue(listGames.getListGames());
        }

    }

    public void setGameValue(List<Game> gameList) {
        ObservableList observableList = FXCollections.observableList(gameList);
        this.gameTable.setItems(observableList);
    }


    @FXML
    private void initialize() {

        gameCreatorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreator()));
        gameNbPlayersColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNbPlayers()).asObject());
        joinButton.disableProperty().bind(Bindings.isEmpty(gameTable.getSelectionModel().getSelectedItems()));

    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
    }

    @Override
    public void handleMessage(Message incomingMessage) {
        return;
    }

    @Override
    public void handleErrorNetwork() {
        return;
    }
}
