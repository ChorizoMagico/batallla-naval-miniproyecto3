package com.example.batallanaval.controller;

import com.example.batallanaval.model.LogicBoard;
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


public class PreparationViewController {

    private Scene mainScene;
    private StackPane[][] boatsStack;
    private LogicBoard.DrawBoard drawBoard;
    private StackPane[] boatsStackAvailable;
    private LogicBoard playerBoard;

    @FXML
    private GridPane showPane;

    @FXML
    private GridPane boardGrid;

    @FXML
    private AnchorPane boatPane;

    @FXML
    private Label boatLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label errorLabel;

    @FXML
    private Button playButton;

    @FXML
    private TextField nameField;

    @FXML
    private Button returnButton;

    /**
     * Sets the actual scene as the main scene
     * @param mainScene the main scene
     */
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Runs all the events methods
     */
    @FXML
    private void initialize(){
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
    }

    /**
     * Handle the exit button functionality, reutirning the player to the previous scene
     * @param actionEvent the event which starts when someone clicks the exit button
     */
    @FXML
    private void handleReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }

    /**
     * Handle the rotate the botate and unselect boat functionality
     * @param event event which fires when someone tries to rotate the boat or unselect it
     */
    @FXML
    void handleReturnBoat(KeyEvent event) {
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
    private void createShowPane(){
        showPane.getChildren().clear();
        boatsStackAvailable = new StackPane[10];
        for (int i = 0; i < 10; i++) {
            boatsStackAvailable[i] = new StackPane();
            boatsStackAvailable[i].setStyle("-fx-border-color: black; -fx-border-width: 1;");
            boatsStackAvailable[i].setAlignment(Pos.TOP_LEFT);
            showPane.add(boatsStackAvailable[i], i % 5, i / 5);
        }
        loadShowPane();
        handleBoatSelection();
    }

    /**
     * Reload the right grid with all the available boats
     */
    private void loadShowPane(){
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
    private void loadShowBoatPane(){
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
    @FXML
    private void handlePlay(ActionEvent actionEvent) {
        // Cierra el fxml actual y abre el fxml del juego
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/game-view.fxml"));
            Parent root = fxmlLoader.load();

            String nickname = nameField.getText();
            // Cerramos la scene
            GameController gameController = fxmlLoader.getController();
            gameController.setPlayerBoard(playerBoard, nickname);
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
    private void handleBoatSelection(){
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
    private void handleLoadBoard() {
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
