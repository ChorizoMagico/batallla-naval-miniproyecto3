package com.example.batallanaval.controller;

import com.example.batallanaval.model.*;
import exceptions.ShipsNotPlacedException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Abstract controller class for the Preparation View in the Battleship game.
 * <p>
 * This class implements the {@link IPreparationViewController} interface and defines
 * the main logic for interacting with the game preparation UI. It manages boat placement,
 * grid interaction, input validation, and transitions to the next game state.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public abstract class PreparationViewControllerAdapter implements IPreparationViewController {

    /**
     * The main scene to return to.
     */
    protected Scene mainScene;

    /**
     * Grid used to track boat placements.
     */
    protected StackPane[][] boatsStack;

    /**
     * Responsible for drawing the game board.
     */
    protected LogicBoard.DrawBoard drawBoard;

    /**
     * UI slots for showing the available boats to place.
     */
    protected StackPane[] boatsStackAvailable;

    /**
     * Player's game board.
     */
    protected LogicBoard playerBoard;

    /**
     * Handles plain text file read/write operations.
     */
    protected PlainTextFileHandler plainTextFileHandler;

    /**
     * Handles object serialization and deserialization.
     */
    protected SerializableFileHandler serializableFileHandler;

    @FXML
    protected GridPane showPane;

    @FXML
    protected GridPane boardGrid;

    @FXML
    protected AnchorPane boatPane;

    @FXML
    protected Label boatLabel;

    @FXML
    protected AnchorPane anchorPane;

    @FXML
    protected Label errorLabel;

    @FXML
    protected Button playButton;

    @FXML
    protected TextField nameField;

    @FXML
    protected Button returnButton;

    @FXML
    protected Label boatCountLabel;

    /**
     * Sets the main scene to allow returning to it.
     *
     * @param mainScene the main scene
     */
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Initializes the controller and prepares the UI components and event handlers.
     */
    @FXML
    public void initialize(){

        returnButton.setOnAction(this::handleReturn);
        playButton.setOnAction(this::handlePlay);
        anchorPane.setOnKeyPressed(this::handleReturnBoat);
        playerBoard = new LogicBoard();
        drawBoard = playerBoard.new DrawBoard();
        boatsStack = new StackPane[10][10];
        createShowPane();
        loadShowBoatPane();
        drawBoard.loadGridPaneWithWater(boardGrid, boatsStack);
        handleLoadBoard();
        plainTextFileHandler = new PlainTextFileHandler();
        serializableFileHandler = new SerializableFileHandler();

        try {
            validateShipsPlaced();
            playButton.setDisable(false);
        } catch (ShipsNotPlacedException e) {
            playButton.setDisable(true);
        }
    }

    @FXML
    /**
     * Handles the unfocus event by requesting focus on the main anchor pane.
     * This ensures that other UI components lose focus when clicking on empty space.
     *
     * @param event the mouse event that triggered the unfocus action
     */
    private void handleUnfocus(MouseEvent event) {
        anchorPane.requestFocus();
    }

    /**
     * Handle the exit button functionality, reutirning the player to the previous scene
     * @param actionEvent the event which starts when someone clicks the exit button
     */
    @Override
    @FXML
    public void handleReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.setFullScreen(true);
    }

    /**
     * Handles keyboard input for boat rotation and deselection.
     *
     * @param event key event triggered by the user
     */
    @Override
    @FXML
    public void handleReturnBoat(KeyEvent event) {
        if(playerBoard.isBoatSelected()) {
            if (event.getCode() == KeyCode.Z && event.isControlDown() & !playerBoard.getSelectedBoat().getIsHorizontal()) {
                playerBoard.returnBoat();
                loadShowPane();
                loadShowBoatPane();
            }

            if (event.getCode() == KeyCode.R ) {
                playerBoard.getSelectedBoat().rotateTheBoat();
            }
        }
    }

    /**
     * Creates and initializes the pane showing the available boats.
     */
    public void createShowPane(){
        showPane.getChildren().clear();
        boatsStackAvailable = new StackPane[10];
        for (int i = 0; i < 10; i++) {
            boatsStackAvailable[i] = new StackPane();
            boatsStackAvailable[i].setStyle("-fx-border-color: black; -fx-border-width: 1;");
            boatsStackAvailable[i].setAlignment(Pos.CENTER);
            showPane.add(boatsStackAvailable[i], i % 5, i / 5);
        }
        loadShowPane();
        handleBoatSelection();
    }

    /**
     * Reloads the pane with the current list of available boats.
     */
    @Override
    public void loadShowPane(){
        int size = playerBoard.getAvailableBoats().size();
        for(int i = 0; i < 10; i++){
            boatsStackAvailable[i].getChildren().clear();
            if(i < size){
                boatsStackAvailable[i].getChildren().add(playerBoard.getAvailableBoats().get(i).getShape());
            }
        }
    }

    /**
     * Displays the selected boat and its type in the UI.
     */
    @Override
    public void loadShowBoatPane(){
        boatPane.getChildren().clear();
        if(playerBoard.isBoatSelected()){
            boatPane.getChildren().add(playerBoard.getSelectedBoat().getShape());
            boatLabel.setText(playerBoard.getSelectedBoat().getType());
        }
        else{
            boatLabel.setText("Barco sin seleccionar");
        }
    }

    /**
     * Starts the game by saving the state and transitioning to the game view.
     *
     * @param actionEvent event triggered by clicking the play button
     */
    @Override
    @FXML
    public void handlePlay(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/game-view.fxml"));
            Parent root = fxmlLoader.load();

            Player userPlayer = new Player(nameField.getText(), playerBoard, 0);
            String content = userPlayer.getNickname() + "," + userPlayer.getSankBoats() + "," + true + "," + "Mensaje: Dispara!";

            LogicBoard cpuBoard = new LogicBoard();
            cpuBoard.aleatorizeBoard();

            ArrayList<LogicBoard> gameBoards = new ArrayList<>();
            gameBoards.add(playerBoard);
            gameBoards.add(cpuBoard);

            GameState gameState = new GameState(gameBoards);

            serializableFileHandler.serialize("board_data.ser", gameState);
            plainTextFileHandler.writeToFile("player_data.csv", content);

            GameController gameController = fxmlLoader.getController();
            gameController.setPlayerBoard(userPlayer, cpuBoard, true, "Mensaje: Dispara!");
            Stage currentStage = (Stage) playButton.getScene().getWindow();
            gameController.setMainScene(currentStage.getScene());

            Scene gameScene = new Scene(root);
            currentStage.setScene(gameScene);
            currentStage.setFullScreen(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up mouse click event handlers for selecting boats.
     */
    @Override
    public void handleBoatSelection(){
        for (int i = 0; i < boatsStackAvailable.length; i++) {
            final int index = i;
            boatsStackAvailable[i].setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    if(!boatsStackAvailable[index].getChildren().isEmpty() & !playerBoard.isBoatSelected()){
                        playerBoard.selectBoat(index);
                        loadShowPane();
                        loadShowBoatPane();
                        errorLabel.setText("Barco seleccionado con éxito!");
                    }
                }
            });
        }
    }

    /**
     * Sets up mouse click event handlers for placing selected boats on the grid.
     */
    @Override
    public void handleLoadBoard() {
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++){
                final StackPane stackPane = boatsStack[i][j];
                final int index = i;
                final int index2 = j;
                stackPane.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        if (playerBoard.isBoatSelected() && playerBoard.placeBoat(index,index2, playerBoard.getSelectedBoat())){
                            playerBoard.emptySelectedBoat();
                            loadShowBoatPane();
                            drawBoard.loadGridPane(boatsStack, playerBoard);
                            updateBoatCountLabel();
                            errorLabel.setText("Barco colocado con éxito!");
                        }else{
                            if(playerBoard.isBoatSelected()){
                                errorLabel.setText("Error al colocar el barco :(");
                            }
                        }
                    }
                });
            }
        }
    }
    /**
     * Updates the label displaying the number of boats placed by the player.
     * Enables the play button only when all 10 boats have been placed.
     */
    public void updateBoatCountLabel() {
        int placed = playerBoard.getNumberOfShipsPlaced();
        boatCountLabel.setText("Barcos colocados: " + placed + "/10");
        playButton.setDisable(placed < 10);
    }
    /**
     * Validates whether all required ships have been placed on the player's board.
     *
     * @throws ShipsNotPlacedException if fewer than 10 ships have been placed.
     */
    public void validateShipsPlaced() throws ShipsNotPlacedException {
        if (playerBoard.getNumberOfShipsPlaced() < 10) {
            throw new ShipsNotPlacedException();
        }
    }
}
