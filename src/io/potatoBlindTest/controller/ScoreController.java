package io.potatoBlindTest.controller;

import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.network.communication.Message;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.List;

public class ScoreController implements UIController {

    public static String playerName = "";
    @FXML
    private Label playerNameLabel;
    @FXML
    private TableView<Player> scoreTable;
    @FXML
    private TableColumn<Player, Integer> rankColumn;
    @FXML
    private TableColumn<Player, String> playerColumn;
    @FXML
    private TableColumn<Player, Integer> scoreColumn;
    @FXML
    private Button mainMenuButton;

    @FXML
    private void handleMainMenu() {
        ControllerClient.initializeMainMenuView("Fin de Game", playerNameLabel.getText());
    }

    @FXML
    private void initialize() {
        rankColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRank()).asObject());
        playerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        scoreColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());
        this.scoreTable.setRowFactory(new Callback<TableView<Player>, TableRow<Player>>() {
            @Override
            public TableRow<Player> call(TableView<Player> scorePlayerTableView) {

                final TableRow<Player> row = new TableRow<Player>() {
                    @Override
                    protected void updateItem(Player person, boolean empty) {
                        super.updateItem(person, empty);
                        if (isEmpty()) {
                            return;
                        }
                        if (getIndex() == 0) {
                            if (!getStyleClass().contains("first-row")) {
                                getStyleClass().add("first-row");
                            }
                        }
                        if (person.getName().equals(ScoreController.playerName)) {
                            if (!getStyleClass().contains("player-row")) {
                                getStyleClass().add("player-row");
                            }
                        }
                    }
                };
                return row;
            }
        });
    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);
        ScoreController.playerName = playerName;
    }

    public void setGameScores(List<Player> gameScores) {
        ObservableList observableList = FXCollections.observableList(gameScores);
        this.scoreTable.setItems(observableList);
    }

    @Override
    public void handleMessage(Message incomingMessage) {
        return;
    }
}
