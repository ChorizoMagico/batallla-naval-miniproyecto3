package com.example.batallanaval.controller;

import com.example.batallanaval.model.LogicBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController {

    private Scene mainScene;

    private LogicBoard playerBoard;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane boardGrid;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    @FXML
    private void handleReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }

    public void setBoardGrid(LogicBoard logicBoard) {
        this.playerBoard = logicBoard;
    }
}
