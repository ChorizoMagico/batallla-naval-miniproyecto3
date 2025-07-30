package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Interface for the Start View Controller of the Battleship game.
 *
 * Defines the basic contract for initializing the start view and handling
 * user interactions such as starting a new game, continuing a saved game,
 * viewing instructions, and exiting the application.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface IStartViewController {

    /**
     * Initializes the start view. Typically sets up button handlers and file handlers.
     */
    @FXML
    void initialize();

    /**
     * Handles the event triggered when the user clicks the "Start Game" button.
     *
     * @param actionEvent the event triggered by the button click
     */
    void handlePlay(ActionEvent actionEvent);

    /**
     * Handles the event triggered when the user clicks the "Continue" button.
     * Loads saved game state if available.
     *
     * @param actionEvent the event triggered by the button click
     */
    void handleContinue(ActionEvent actionEvent);

    /**
     * Handles the event triggered when the user clicks the "Instructions" button.
     * Navigates to the instructions view.
     *
     * @param actionEvent the event triggered by the button click
     */
    void handleInstructions (ActionEvent actionEvent);

    /**
     * Handles the event triggered when the user clicks the "Exit" button.
     * Closes the application.
     *
     * @param actionEvent the event triggered by the button click
     */
    void handleExit (ActionEvent actionEvent);
}
