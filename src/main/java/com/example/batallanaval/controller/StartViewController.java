package com.example.batallanaval.controller;

import com.example.batallanaval.model.LogicBoard;
import com.example.batallanaval.model.PlainTextFileHandler;
import com.example.batallanaval.model.Player;
import com.example.batallanaval.model.SerializableFileHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartViewController {

    @FXML
    private Label titlleLabel;

    @FXML
    private Button startPlayButton;


    @FXML
    private Button continuePlayButton;

    @FXML
    private Button instructionsButton;

    @FXML
    private Button exitPlayButton;

    private PlainTextFileHandler plainTextFileHandler;
    private SerializableFileHandler serializableFileHandler;


    @FXML
    private void initialize() {
        plainTextFileHandler = new PlainTextFileHandler();
        serializableFileHandler = new SerializableFileHandler();
        startPlayButton.setOnAction(this::handlePlay);
        continuePlayButton.setOnAction(this::handleContinue);
        instructionsButton.setOnAction(this::handleInstructions);
        exitPlayButton.setOnAction(this::handleExit);
    }


    private void handlePlay(ActionEvent actionEvent) {
        // Cierra el fxml actual y abre el fxml del juego
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/preparation-view.fxml"));
            Parent root = fxmlLoader.load();

            // Cerramos la scene
            PreparationViewController preparationViewController = fxmlLoader.getController();
            Stage currentStage = (Stage) startPlayButton.getScene().getWindow();
            preparationViewController.setMainScene(currentStage.getScene());

            // Mostramos la neuva scene
            Scene gameScene = new Scene(root);
            currentStage.setScene(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Se termino la excepcion.");
        }
    }

    private void handleContinue(ActionEvent actionEvent) {
        String[] data = plainTextFileHandler.readFromFile("player_data.csv");
        if(data.length == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No hay datos guardados");
            alert.showAndWait();
        }else{
            try{
                String nickname = data[0];

                LogicBoard userBoard = (LogicBoard) serializableFileHandler.deserialize("board_data.ser");

                int sinkedBoats = Integer.parseInt(data[1]);
                Player userPlayer = new Player(nickname, userBoard, sinkedBoats);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/game-view.fxml"));
                Parent root = fxmlLoader.load();

                GameController gameController = fxmlLoader.getController();
                gameController.setPlayerBoard(userPlayer);
                Stage currentStage = (Stage) continuePlayButton.getScene().getWindow();
                gameController.setMainScene(currentStage.getScene());

                Scene gameScene = new Scene(root);
                currentStage.setScene(gameScene);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Se termino la excepcion.");
            }
        }
    }

    private void handleInstructions (ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/instructions-view.fxml"));
            Parent root = fxmlLoader.load();

            // Accedemos al controlador y le pasamos la escena anterior para poder volver
            InstructionsController instructionsController = fxmlLoader.getController();
            Stage currentStage = (Stage) instructionsButton.getScene().getWindow();
            instructionsController.setMainScene(currentStage.getScene());

            //Mostramos la nueva scene
            Scene instructionScene = new Scene(root);
            currentStage.setScene(instructionScene);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Se termino la excepcion.");
        }
    }
    // Cierra la aplicacion
    private void handleExit (ActionEvent actionEvent) {
        Platform.exit();
    }
}
