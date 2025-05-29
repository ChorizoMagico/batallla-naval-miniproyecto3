package com.example.batallanaval.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StartViewController {

    String userName = "";

    @FXML
    private Label titlleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label statusNameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button startPlayButton;

    @FXML
    private Button continuePlayButton;

    @FXML
    private Button instructionsButton;

    @FXML
    private Button exitPlayButton;

    @FXML
    private Button nameButton;

    @FXML
    private void initialize() {
        startPlayButton.setOnAction(this::handlePlay);
        continuePlayButton.setOnAction(this::handleContinue);
        instructionsButton.setOnAction(this::handleInstructions);
        exitPlayButton.setOnAction(this::handleExit);
        nameButton.setOnAction(this::handleName);
    }

    private void handlePlay(ActionEvent actionEvent) {
        // Si el jugador no puso nada, el nombre sera invitado
        if(userName.isEmpty()) {
            userName = "invitado";
        }

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
            System.out.println("usuario : " + userName);
        }
    }

    private void handleContinue(ActionEvent actionEvent) {
        /**
         * 1- Chequea si existe una partida sin termianr
         * En caso que haya una partida disponible, el boton estara disponible,
         * en caso contrario, no estara disponible
         * 2- Carga la partida*/
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

    // Creacion de una excepcion propia
    private void handleName (ActionEvent actionEvent) {
        String name = nameTextField.getText();
        try {
            if(name.isEmpty()) {
                statusNameLabel.setText("El nombre no puede ser vacio.");
                throw new IllegalArgumentException("El username no puede estar vacia.");
            }

            // Lo que pasa cuando el nombre es diferente
            /**
             * La idea es que con esa variable se muestre en game-view.fxml
             * Y tambien como atributo de jugador.*/
            userName = name; // Se guarda en una variable local

            /**
             * Se actualiza el label
             * Estaria bueno una animcaion que se desapareciera el mensaje (opcional)*/
            statusNameLabel.setText("Se guardo el nombre.");

            /**
             * Cosas que podriamos implementar:
             * - que cuando le de al boton, el textField quede bloqeuado
             * - que cuando le de al boton, se borre el contenido de textField
             */
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("ususario :" + userName);
        }
    }
}
