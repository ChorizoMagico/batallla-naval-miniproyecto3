package com.example.batallanaval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;


public interface IPreparationViewController {

    void setMainScene(Scene mainScene);

    /**
     * Runs all the events methods
     */
    @FXML
    void initialize();


    /**
     * Handle the exit button functionality, reutirning the player to the previous scene
     * @param actionEvent the event which starts when someone clicks the exit button
     */
    @FXML
    void handleReturn(ActionEvent actionEvent);

    /**
     * Handle the rotate the boat and unselect boat functionality
     * @param event event which fires when someone tries to rotate the boat or unselect it
     */
    @FXML
    void handleReturnBoat(KeyEvent event);

    /**
     * Creates the right grid with all the available boats
     */
    void createShowPane();

    /**
     * Reload the right grid with all the available boats
     */
    void loadShowPane();

    /**
     * Shows the selected boat in a small rectangle
     */
    void loadShowBoatPane();

    /**
     * Goes to the next stage, the game one
     * @param actionEvent event which fires when someone clicks the play button
     */
    @FXML
    void handlePlay(ActionEvent actionEvent);

    /**
     * Handle the event when someone selects one of the available boats in the right
     */
    void handleBoatSelection();

    /**
     * Handle the events when someone clicks the cells in which the boat will be placed
     */
    void handleLoadBoard();

}
