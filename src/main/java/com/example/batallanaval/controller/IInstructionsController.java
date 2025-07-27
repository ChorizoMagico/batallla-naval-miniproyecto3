package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public interface IInstructionsController {

    void setMainScene(Scene mainScene);

    @FXML
    void handleReturn(ActionEvent actionEvent);
}
