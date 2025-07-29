package com.example.batallanaval.controller;

import com.example.batallanaval.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class StartViewControllerAdapter implements IStartViewController {

    @FXML
    protected Button startPlayButton;


    @FXML
    protected Button continuePlayButton;

    @FXML
    protected Button instructionsButton;

    @FXML
    protected Button exitPlayButton;

    protected PlainTextFileHandler plainTextFileHandler;
    protected SerializableFileHandler serializableFileHandler;

    @Override
    @FXML
    public void initialize() {
        plainTextFileHandler = new PlainTextFileHandler();
        serializableFileHandler = new SerializableFileHandler();
        startPlayButton.setOnAction(this::handlePlay);
        continuePlayButton.setOnAction(this::handleContinue);
        instructionsButton.setOnAction(this::handleInstructions);
        exitPlayButton.setOnAction(this::handleExit);
    }

    @Override
    public void handlePlay(ActionEvent actionEvent) {
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
            currentStage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Se termino la excepcion.");
        }
    }

    @Override
    public void handleContinue(ActionEvent actionEvent) {

        File file = new File("player_data.csv");

        if (!file.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No hay archivo de datos guardado.");
            alert.showAndWait();
        }else{
            try{
                String[] data = plainTextFileHandler.readFromFile("player_data.csv");
                String nickname = data[0];

                File boardFile = new File("board_data.ser");
                if (!boardFile.exists() || boardFile.length() == 0) {
                    throw new IOException("Archivo de tablero no existe o está vacío.");
                }

                Object obj = serializableFileHandler.deserialize("board_data.ser");
                if (!(obj instanceof GameState state)) {
                    throw new IOException("Archivo de tablero corrupto o inválido.");
                }

                ArrayList<LogicBoard> gameBoards = state.getBoards();
                if (gameBoards == null || gameBoards.size() < 2) {
                    throw new IOException("Tableros de juego no válidos.");
                }


                int sankBoats = Integer.parseInt(data[1]);
                boolean playerTurn = Boolean.parseBoolean(data[2]);
                String messageLabel = data[3];

                Player userPlayer = new Player(nickname, gameBoards.get(0), sankBoats);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/batallanaval/FXML/game-view.fxml"));
                Parent root = fxmlLoader.load();

                GameController gameController = fxmlLoader.getController();
                gameController.setPlayerBoard(userPlayer, gameBoards.get(1), playerTurn, messageLabel);
                Stage currentStage = (Stage) continuePlayButton.getScene().getWindow();
                gameController.setMainScene(currentStage.getScene());

                Scene gameScene = new Scene(root);
                currentStage.setScene(gameScene);
                currentStage.setFullScreen(true);
            } catch (IOException | ClassCastException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo cargar la partida guardada: " + e.getMessage());
                alert.showAndWait();
            } finally {
                System.out.println("Se termino la excepcion.");
            }
        }
    }

    @Override
    public void handleInstructions (ActionEvent actionEvent) {
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
            currentStage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Se termino la excepcion.");
        }
    }


    // Cierra la aplicacion
    @Override
    public void handleExit (ActionEvent actionEvent) {
        Platform.exit();
    }
}
