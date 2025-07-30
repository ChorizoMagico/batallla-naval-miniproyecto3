package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Abstract class that serves as an adapter for the Instructions view controller.
 * <p>
 * Provides default implementations for setting the main scene and handling the return action.
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public abstract class InstructionsControllerAdapter implements IInstructionsController {

    /**
     * The scene to return to when exiting the instructions view.
     */
    protected Scene mainScene;

    /**
     * Sets the main scene to be restored when the user leaves the instructions view.
     *
     * @param mainScene the main scene of the application
     */
    @Override
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Handles the return button action by restoring the main scene and enabling full-screen mode.
     *
     * @param actionEvent the event triggered by clicking the return button
     */
    @Override
    @FXML
    public void handleReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setFullScreen(true);
        stage.setScene(mainScene);
    }
}
