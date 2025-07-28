package com.example.batallanaval.controller;

import com.example.batallanaval.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GameController {

    private Scene mainScene;

    private Player userPlayer;

    private LogicBoard playerBoard;

    private LogicBoard cpuBoard;

    private LogicBoard.DrawBoard drawBoard;

    private boolean gameOver;

    private StackPane[][] playerStack;

    private StackPane[][] cpuStack;

    private boolean playerTurn;

    private boolean boardShowing;

    private PlainTextFileHandler plainTextFileHandler;

    private SerializableFileHandler serializableFileHandler;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label messageLabel;

    @FXML
    private Button turnButton;

    @FXML
    private GridPane cpuGrid;

    @FXML
    private Label nameLabel;

    @FXML
    private Label sankBoats;

    @FXML
    private Button showButton;

    @FXML
    private GridPane playerGrid;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    private void initializeGame() {
        gameOver = playerBoard.isGameOver() || cpuBoard.isGameOver();

        if(playerTurn){
            turnButton.setDisable(true);
        }

        boardShowing = false;
        playerStack = new StackPane[10][10];
        cpuStack = new StackPane[10][10];
        drawBoard = playerBoard.new DrawBoard();
        drawBoard.loadGridPaneWithWater(playerGrid, playerStack);
        drawBoard.loadGridPaneWithWater(cpuGrid, cpuStack);
        drawBoard.loadGridPane(playerStack, playerBoard);
        drawBoard.loadShootsInGridPane(cpuStack, cpuBoard, true);
        playerBoard.printBoard();
        plainTextFileHandler = new PlainTextFileHandler();
        serializableFileHandler = new SerializableFileHandler();
        handleAttack();
        turnButton.setOnAction(this::handleTurn);
    }

    private void saveState(){
        String content = userPlayer.getNickname()+","+userPlayer.getSankBoats()+","+playerTurn+","+messageLabel.getText();
        ArrayList<LogicBoard> gameBoards = new ArrayList<>();
        gameBoards.add(playerBoard);
        gameBoards.add(cpuBoard);
        GameState gameState = new GameState(gameBoards);

        plainTextFileHandler.writeToFile("player_data.csv", content);
        serializableFileHandler.serialize("board_data.ser", gameState);
    }

    @FXML
    private void handleShow(ActionEvent event) {
        if(boardShowing == false) {
            boardShowing = true;
            drawBoard.loadGridPane(cpuStack, cpuBoard);
            showButton.setText("Hide board");
        } else {
            boardShowing = false;
            drawBoard.loadShootsInGridPane(cpuStack, cpuBoard, true);
            showButton.setText("Show board");
        }
    }


    @FXML
    private void handleReturn(ActionEvent actionEvent) {
        saveState();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }

    private void handleAttack(){
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                final int row = i;
                final int col = j;
                final StackPane stackPane = cpuStack[i][j];
                stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(playerTurn && !gameOver) {
                            int result = cpuBoard.shoot(row,col);
                            if(result == 1) {
                                playerTurn = true;
                                turnButton.setDisable(true);
                                messageLabel.setText("Mensaje: Barco impactado. Sigue disparando!");
                                messageLabel.setStyle("-fx-text-fill: green;");
                            } else if (result == 2) {
                                playerTurn = true;
                                turnButton.setDisable(true);
                                messageLabel.setText("Mensaje: Barco hundido. Dispara a otro barco!");
                                updateSankBoatsLabel();
                                messageLabel.setStyle("-fx-text-fill: green;");
                            } else if (result == 3) {
                                playerTurn = true;
                                turnButton.setDisable(true);
                                messageLabel.setText("Mensaje: Ya se disparó en esta casilla. Intenta de nuevo");
                                messageLabel.setStyle("-fx-text-fill: green;");
                            } else if (result == 0) {
                                playerTurn = false;
                                turnButton.setDisable(false);
                                messageLabel.setText("Mensaje: Fallaste el disparo. Presiona el botón");
                                messageLabel.setStyle("-fx-text-fill: green;");
                            }
                            cpuBoard.printBoard();
                            gameOver = cpuBoard.isGameOver();
                            drawBoard.loadShootsInGridPane(cpuStack, cpuBoard, true);
                            saveState();
                            if(gameOver) {
                                turnButton.setDisable(true);
                                playerTurn = false;
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fin del juego");
                                alert.setHeaderText(null);
                                alert.setContentText("Ganaste :)");
                                alert.showAndWait();
                            };
                        }
                    }
                });
            }
        }
    }

    @FXML
    private void handleTurn(ActionEvent event) {
        nextTurn();
    }

    @FXML
    private void keyPressedTurnHandler(KeyEvent event) {
        if(event.getCode() == KeyCode.N && !gameOver && !playerTurn) {
            nextTurn();
        }
    }

    public void nextTurn(){
        int result = playerBoard.randomShooting();

        if(result == 1) {
            playerTurn = false;
            turnButton.setDisable(false);
            messageLabel.setText("Mensaje: Impactaron tu barco. Presiona el botón");
            messageLabel.setStyle("-fx-text-fill: red;");
        }else if(result == 2) {
            playerTurn = false;
            turnButton.setDisable(false);
            messageLabel.setText("Mensaje: Te destruyeron un barco. Presiona el botón");
            messageLabel.setStyle("-fx-text-fill: red;");
        }else if(result == 0) {
            playerTurn = true;
            turnButton.setDisable(true);
            messageLabel.setText("Mensaje: Fallaron el tiro. Ahora te toca disparar!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
        gameOver = playerBoard.isGameOver();
        drawBoard.loadGridPane(playerStack, playerBoard);
        if(gameOver){
            turnButton.setDisable(true);
            playerTurn = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fin del juego");
            alert.setHeaderText(null);
            alert.setContentText("Perdiste :(");
            alert.showAndWait();
        }
    }

    public void updateSankBoatsLabel() {
        userPlayer.updateSankBoats();
        sankBoats.setText("Barcos hundidos : "+ userPlayer.getSankBoats());
    }

    public void setPlayerBoard(Player userPlayer, LogicBoard cpuBoard, boolean playerTurn, String customMessageLabel) {
        this.userPlayer = userPlayer;
        this.playerBoard = userPlayer.getPlayerBoard();
        this.cpuBoard = cpuBoard;
        this.playerTurn = playerTurn;
        nameLabel.setText(userPlayer.getNickname());
        sankBoats.setText("Barcos hundidos: "+userPlayer.getSankBoats());
        messageLabel.setText(customMessageLabel);
        messageLabel.setStyle(String.format("-fx-text-fill: blue;"));
        initializeGame();
    }


}
