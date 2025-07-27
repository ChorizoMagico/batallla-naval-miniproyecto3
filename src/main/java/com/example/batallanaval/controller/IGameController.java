package com.example.batallanaval.controller;

import com.example.batallanaval.model.LogicBoard;
import com.example.batallanaval.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public interface IGameController {

    void setMainScene(Scene mainScene);

    void initializeGame();

    void saveState();

    @FXML
    void handleShow(ActionEvent event);

    @FXML
    void handleReturn(ActionEvent actionEvent);

    void handleAttack();

    @FXML
    void handleTurn(ActionEvent event);

    @FXML
    void keyPressedTurnHandler(KeyEvent event);

    void nextTurn();

    void updateSankBoatsLabel();

    void setPlayerBoard(Player userPlayer, LogicBoard cpuBoard, boolean playerTurn, String customMessageLabel);
}
