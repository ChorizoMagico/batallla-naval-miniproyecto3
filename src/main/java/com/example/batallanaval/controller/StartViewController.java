package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    @FXML
    private void initialize() {
        startPlayButton.setOnAction(this::handlePlay);
        continuePlayButton.setOnAction(this::handleContinue);
        instructionsButton.setOnAction(this::handleInstructions);
        exitPlayButton.setOnAction(this::handleExit);
    }

    private void handlePlay(ActionEvent actionEvent) {
        // Cierra el fxml actual y abre el fxml del juego
    }

    private void handleContinue(ActionEvent actionEvent) {

    }

    private void handleInstructions (ActionEvent actionEvent) {

    }

    private void handleExit (ActionEvent actionEvent) {

    }
}
