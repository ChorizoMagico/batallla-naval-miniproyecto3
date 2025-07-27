package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public interface IStartViewController {


    @FXML
    void initialize();

    void handlePlay(ActionEvent actionEvent);

    void handleContinue(ActionEvent actionEvent);

    void handleInstructions (ActionEvent actionEvent);

    void handleExit (ActionEvent actionEvent);
}
