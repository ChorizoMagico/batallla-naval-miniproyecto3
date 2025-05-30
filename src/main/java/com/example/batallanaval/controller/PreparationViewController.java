package com.example.batallanaval.controller;

import com.example.batallanaval.model.Boat;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class PreparationViewController {

    private Scene mainScene;
    private StackPane[][] boatsStack;
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
    private Button returnButton;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    @FXML
    private void initialize(){
        returnButton.setOnAction(this::handleReturn);
        playButton.setOnAction(this::handlePlay);
        anchorPane.setOnKeyPressed(this::handleReturnBoat);
        playerBoard = new LogicBoard();
        createShowPane();
        loadShowBoatPane();
        loadGridPaneWithWater();
        handleLoadBoard();
    }

    @FXML
    private void handleReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }

    @FXML
    void handleReturnBoat(KeyEvent event) {
        if(playerBoard.isBoatSelected()) {
            if (event.getCode() == KeyCode.Z && event.isControlDown()) {
                playerBoard.returnBoat();
                loadShowPane();
                loadShowBoatPane();
            }

            if (event.getCode() == KeyCode.R) {
                playerBoard.getSelectedBoat().rotateTheBoat();
            }
        }
    }

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

    private void loadShowPane(){
        int size = playerBoard.getAvailableBoats().size();
        for(int i = 0; i < 10; i++){
            boatsStackAvailable[i].getChildren().clear();
            if(i < size){
                boatsStackAvailable[i].getChildren().add(playerBoard.getAvailableBoats().get(i).getShape());
            }
        }
    }

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

    @FXML
    private void handlePlay(ActionEvent actionEvent) {
        // Cierra el fxml actual y abre el fxml del juego
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/game-view.fxml"));
            Parent root = fxmlLoader.load();

            // Cerramos la scene
            GameController gameController = fxmlLoader.getController();
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

    private void handleBoatSelection(){
        for (int i = 0; i < boatsStackAvailable.length; i++) {
            final int index = i;
            boatsStackAvailable[i].setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    if(!boatsStackAvailable[index].getChildren().isEmpty()){
                        playerBoard.selectBoat(index);
                        loadShowPane();
                        loadShowBoatPane();
                        errorLabel.setText("Barco seleccionado con éxito!");
                    }
                }
            });
        }
    }

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
                            loadGridPane();
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

    private void loadGridPane() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                boatsStack[i][j].getChildren().clear();
            }
        }

        List<Boat> boats = playerBoard.getBoats();
        for (int b = 0; b < boats.size(); b++) {
            Boat boat = boats.get(b);
            int[] position = boat.getPosition();
            int row = position[0];
            int col = position[1];

            for (int i = 0; i < boat.getSize(); i++) {
                int r;
                int c;

                if (boat.getIsHorizontal()) {
                    r = row;
                    c = col + i;
                } else {
                    r = row + i;
                    c = col;
                }

                Node segment = boat.createSegment(i);
                boatsStack[r][c].getChildren().add(segment);
            }
        }
    }


    private void loadGridPaneWithWater() {
        boardGrid.getChildren().clear();
        boatsStack = new StackPane[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                boatsStack[i][j] = new StackPane();
                boatsStack[i][j].setStyle("-fx-background-color: dodgerblue; -fx-border-color: black; -fx-border-width: 1;");
                boatsStack[i][j].setAlignment(Pos.TOP_LEFT);
                boardGrid.add(boatsStack[i][j], j, i);
            }
        }
    }
}
