package com.example.batallanaval.controller;

import com.example.batallanaval.model.*;
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

import java.util.ArrayList;

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
        ArrayList<LogicBoard> gameBoards = new ArrayList<>();
        gameBoards.add(playerBoard);
        gameBoards.add(cpuBoard);
        GameState gameState = new GameState(gameBoards);

        plainTextFileHandler.writeToFile("player_data.csv", content);
        serializableFileHandler.serialize("board_data.ser", gameState);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }

    public void setPlayerBoard(Player userPlayer, LogicBoard cpuBoard) {
        this.userPlayer = userPlayer;
        this.playerBoard = userPlayer.getPlayerBoard();
        this.cpuBoard = cpuBoard;
        nameLabel.setText(userPlayer.getNickname());
        initializeGame();
    }


    private void nextTurn() {
        turn++;
    }

}
