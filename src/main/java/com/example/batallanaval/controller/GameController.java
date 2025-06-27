package com.example.batallanaval.controller;

import com.example.batallanaval.model.LogicBoard;
import com.example.batallanaval.model.PlainTextFileHandler;
import com.example.batallanaval.model.Player;
import com.example.batallanaval.model.SerializableFileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameController {

    private Scene mainScene;

    private Player userPlayer;

    private LogicBoard playerBoard;

    private LogicBoard cpuBoard;

    private LogicBoard.DrawBoard drawBoard;

    private StackPane[][] playerStack;

    private StackPane[][] cpuStack;

    private int turn;

    private boolean boardShowing;

    private PlainTextFileHandler plainTextFileHandler;

    private SerializableFileHandler serializableFileHandler;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane cpuGrid;

    @FXML
    private Label nameLabel;

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
        drawBoard = playerBoard.new DrawBoard();
        drawBoard.loadGridPaneWithWater(playerGrid, playerStack);
        drawBoard.loadGridPaneWithWater(cpuGrid, cpuStack);
        drawBoard.loadGridPane(playerStack, playerBoard);
        playerBoard.printBoard();
        plainTextFileHandler = new PlainTextFileHandler();
        serializableFileHandler = new SerializableFileHandler();
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

        String content = userPlayer.getNickname()+","+userPlayer.getSinkedBoats();
        plainTextFileHandler.writeToFile("player_data.csv", content);
        serializableFileHandler.serialize("board_data.ser", playerBoard);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }

    public void setPlayerBoard(Player userPlayer) {
        this.userPlayer = userPlayer;
        this.playerBoard = userPlayer.getPlayerBoard();
        nameLabel.setText(userPlayer.getNickname());
        cpuBoard = userPlayer.getPlayerBoard();
        initializeGame();
    }


    private void nextTurn() {
        turn++;
    }

}
