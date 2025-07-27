package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class InstructionsControllerAdapter implements IInstructionsController {
    protected Scene mainScene;

    @Override
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    @Override
    @FXML
    public void handleReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
    }
}
