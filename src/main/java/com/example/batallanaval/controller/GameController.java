package com.example.batallanaval.controller;

import com.example.batallanaval.model.DrawBoard;
import com.example.batallanaval.model.LogicBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameController {

    private Scene mainScene;

    private LogicBoard playerBoard;

    private LogicBoard cpuBoard;

    private DrawBoard drawBoard;

    private StackPane[][] playerStack;

    private StackPane[][] cpuStack;

    private int turn;

    private boolean boardShowing;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane cpuGrid;

    @FXML
    private Button showButton;

    @FXML
    private GridPane playerGrid;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    private void initializeGame() {
        turn = 1;
        boardShowing = false;
        playerStack = new StackPane[10][10];
        cpuStack = new StackPane[10][10];
        drawBoard = new DrawBoard();
        drawBoard.loadGridPaneWithWater(playerGrid, playerStack);
        drawBoard.loadGridPaneWithWater(cpuGrid, cpuStack);
        drawBoard.loadGridPane(playerStack, playerBoard);
        playerBoard.printBoard();
    }

    @FXML
    private void handleShow(ActionEvent event) {
        if(boardShowing == false) {
            drawBoard.loadGridPane(cpuStack, cpuBoard);
            showButton.setText("Hide board");
            boardShowing = true;
        } else {
            drawBoard.loadShootsInGridPane(cpuStack, cpuBoard, true);
            showButton.setText("Show board");
            boardShowing = false;
        }
    }


    @FXML
    private void handleReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }

    public void setPlayerBoard(LogicBoard logicBoard) {
        this.playerBoard = logicBoard;
        cpuBoard = logicBoard;
        initializeGame();
    }

    private void nextTurn() {
        turn++;
    }

}
