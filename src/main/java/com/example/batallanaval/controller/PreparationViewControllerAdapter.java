package com.example.batallanaval.controller;

import com.example.batallanaval.model.*;
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

public abstract class PreparationViewControllerAdapter implements IPreparationViewController {

    protected Scene mainScene;
    protected StackPane[][] boatsStack;
    protected LogicBoard.DrawBoard drawBoard;
    protected StackPane[] boatsStackAvailable;
    protected LogicBoard playerBoard;
    protected PlainTextFileHandler plainTextFileHandler;
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
    
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Runs all the events methods
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
    }

    /**
     * Handle the rotate the botate and unselect boat functionality
     * @param event event which fires when someone tries to rotate the boat or unselect it
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
     * Creates the right grid with all the available boats
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
     * Reload the right grid with all the available boats
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
     * Shows the selected boat in a small rectangle
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
     * Goes to the next stage, the game one
     * @param actionEvent event which fires when someone clicks the play button
     */
    @Override
    @FXML
    public void handlePlay(ActionEvent actionEvent) {
        // Cierra el fxml actual y abre el fxml del juego
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/game-view.fxml"));
            Parent root = fxmlLoader.load();

            Player userPlayer = new Player(nameField.getText(), playerBoard, 0);
            String content = userPlayer.getNickname()+","+userPlayer.getSankBoats()+","+true+","+"Mensaje: Dispara!";

            LogicBoard cpuBoard = new LogicBoard();
            cpuBoard.aleatorizeBoard();

            ArrayList<LogicBoard> gameBoards = new ArrayList<>();
            gameBoards.add(playerBoard);
            gameBoards.add(cpuBoard);

            GameState gameState = new GameState(gameBoards);

            serializableFileHandler.serialize("board_data.ser", gameState);
            plainTextFileHandler.writeToFile("player_data.csv", content);

            // Cerramos la scene
            GameController gameController = fxmlLoader.getController();
            gameController.setPlayerBoard(userPlayer, cpuBoard, true, "Mensaje: Dispara!");
            Stage currentStage = (Stage) playButton.getScene().getWindow();
            gameController.setMainScene(currentStage.getScene());

            // Mostramos la neuva scene
            Scene gameScene = new Scene(root);
            currentStage.setScene(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Se termino la excepcion.");
        }
    }

    /**
     * Handle the event when someone selects one of the available boats in the right
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
     * Handle the events when someone clicks the cells in which the boat will be placed
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
}
