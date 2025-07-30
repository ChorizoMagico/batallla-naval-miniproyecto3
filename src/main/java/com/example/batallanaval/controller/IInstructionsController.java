package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

/**
 * Interface for the Instructions view controller.
 * <p>
 * Defines the behavior for managing the return action and setting the main scene.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface IInstructionsController {

    /**
     * Sets the main scene to return to when exiting the instructions view.
     *
     * @param mainScene the main application scene
     */
    void setMainScene(Scene mainScene);

    /**
     * Handles the action of returning from the instructions view to the main scene.
     *
     * @param actionEvent the event triggered by the user action (e.g., clicking the return button)
     */
    @FXML
    void handleReturn(ActionEvent actionEvent);
}
