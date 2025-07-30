package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * Interface for the Preparation View Controller in the Battleship game.
 * <p>
 * Handles user interactions during the preparation phase, such as boat placement,
 * rotation, and transitioning to the game phase.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface IPreparationViewController {

    /**
     * Sets the main scene of the application.
     *
     * @param mainScene the main JavaFX scene
     */
    void setMainScene(Scene mainScene);

    /**
     * Initializes the preparation view. Sets up event handlers and visual components.
     */
    @FXML
    void initialize();


    /**
     * Handles the event triggered when the user clicks the "Return" button.
     * Returns the player to the previous scene.
     *
     * @param actionEvent the event triggered by the button click
     */
    @FXML
    void handleReturn(ActionEvent actionEvent);

    /**
     * Handles the event triggered when the user presses a key to rotate or unselect the boat.
     *
     * @param event the keyboard event
     */
    @FXML
    void handleReturnBoat(KeyEvent event);

    /**
     * Creates the pane on the right side of the UI displaying all available boats.
     */
    void createShowPane();

    /**
     * Reloads the right-side pane with all available boats.
     */
    void loadShowPane();

    /**
     * Displays the currently selected boat in a preview area.
     */
    void loadShowBoatPane();

    /**
     * Handles the event triggered when the user clicks the "Play" button.
     * Proceeds to the game phase.
     *
     * @param actionEvent the event triggered by the button click
     */
    @FXML
    void handlePlay(ActionEvent actionEvent);

    /**
     * Handles the logic when a player selects one of the available boats on the right panel.
     */
    void handleBoatSelection();

    /**
     * Handles events related to clicking cells on the board to place boats.
     */
    void handleLoadBoard();

}
