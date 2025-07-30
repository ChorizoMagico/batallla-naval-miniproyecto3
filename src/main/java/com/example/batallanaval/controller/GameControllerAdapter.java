package com.example.batallanaval.controller;

import com.example.batallanaval.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Abstract adapter class that implements the {@link IGameController} interface.
 * <p>
 * This class provides the main game logic, including board setup, attack handling,
 * turn switching, game state saving, and UI management.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public abstract class GameControllerAdapter implements IGameController {

    /**
     * The main application scene to return to after exiting the game view.
     */
    protected Scene mainScene;

    /**
     * The player object representing the user.
     */
    protected Player userPlayer;

    /**
     * The logic board for the user player.
     */
    protected LogicBoard playerBoard;

    /**
     * The logic board for the computer opponent.
     */
    protected LogicBoard cpuBoard;

    /**
     * Drawing utility for rendering boards and shots.
     */
    protected LogicBoard.DrawBoard drawBoard;

    /**
     * Indicates whether the game is over.
     */
    protected boolean gameOver;

    /**
     * Grid representation for the player.
     */
    protected StackPane[][] playerStack;

    /**
     * Grid representation for the CPU.
     */
    protected StackPane[][] cpuStack;

    /**
     * Indicates whether it is the player's turn.
     */
    protected boolean playerTurn;

    /**
     * Flag to indicate if the full CPU board is currently visible.
     */
    protected boolean boardShowing;

    /**
     * Utility for handling plain text file operations.
     */
    protected PlainTextFileHandler plainTextFileHandler;

    /**
     * Utility for handling serialized file operations.
     */
    protected SerializableFileHandler serializableFileHandler;


    @FXML
    protected Label messageLabel;

    @FXML
    protected Button turnButton;

    @FXML
    protected GridPane cpuGrid;

    @FXML
    protected Label nameLabel;

    @FXML
    protected Label sankBoats;

    @FXML
    protected Button showButton;

    @FXML
    protected GridPane playerGrid;

    /**
     * Sets the main scene for navigation.
     *
     * @param mainScene the scene to return to
     */
    @Override
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Initializes the game, setting up the boards, event handlers,
     * and user interface elements.
     */
    @Override
    public void initializeGame() {
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

    /**
     * Saves the current game state to external files.
     */
    @Override
    public void saveState(){
        String content = userPlayer.getNickname()+","+userPlayer.getSankBoats()+","+playerTurn+","+messageLabel.getText();
        ArrayList<LogicBoard> gameBoards = new ArrayList<>();
        gameBoards.add(playerBoard);
        gameBoards.add(cpuBoard);
        GameState gameState = new GameState(gameBoards);

        plainTextFileHandler.writeToFile("player_data.csv", content);
        serializableFileHandler.serialize("board_data.ser", gameState);
    }

    /**
     * Handles showing or hiding the full CPU board when toggled.
     *
     * @param event the user action event
     */
    @Override
    @FXML
    public void handleShow(ActionEvent event) {
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

    /**
     * Handles the return to the main scene, saving the state beforehand.
     *
     * @param actionEvent the action event triggered by user
     */
    @Override
    @FXML
    public void handleReturn(ActionEvent actionEvent) {
        saveState(); // Guarda el estado del juego si es necesario

        // Obtener el Stage actual
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Cargar start-view.fxml directamente
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/start-view.fxml"));
            Parent root = loader.load();
            Scene startScene = new Scene(root);
            stage.setScene(startScene);
            stage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar el error adecuadamente
        }
    }

    /**
     * Sets up event listeners on the CPU board grid for attack interactions.
     */
    @Override
    public void handleAttack(){
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

                            drawBoard.loadShootsInGridPane(cpuStack, cpuBoard, !boardShowing);

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

    /**
     * Proceeds with the turn when the turn button is pressed.
     *
     * @param event the action event triggered by user
     */
    @FXML
    public void handleTurn(ActionEvent event) {
        nextTurn();
    }

    /**
     * Handles key press events related to turn switching.
     *
     * @param event the key event triggered
     */
    @FXML
    public void keyPressedTurnHandler(KeyEvent event) {
        if(event.getCode() == KeyCode.N && !gameOver && !playerTurn) {
            nextTurn();
        }
    }

    /**
     * Executes the logic for the CPU's turn and updates the UI accordingly.
     */
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

    /**
     * Updates the label displaying how many boats the player has sunk.
     */
    public void updateSankBoatsLabel() {
        userPlayer.updateSankBoats();
        sankBoats.setText("Barcos hundidos : "+ userPlayer.getSankBoats());
    }

    /**
     * Sets the initial state of the game using player data and prepares the game scene.
     *
     * @param userPlayer the human player
     * @param cpuBoard the CPU opponent's board
     * @param playerTurn whether the human goes first
     * @param customMessageLabel the starting message to show in the UI
     */
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
