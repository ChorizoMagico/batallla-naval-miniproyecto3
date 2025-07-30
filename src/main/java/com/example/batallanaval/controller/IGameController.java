package com.example.batallanaval.controller;

import com.example.batallanaval.model.LogicBoard;
import com.example.batallanaval.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * Interface for the main game controller.
 * <p>
 * Defines the essential methods for managing the game's behavior, UI interactions,
 * and player turns.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface IGameController {

    /**
     * Sets the main scene to allow returning from the game view.
     *
     * @param mainScene the main application scene
     */
    void setMainScene(Scene mainScene);

    /**
     * Initializes the game components and sets up the initial state.
     */
    void initializeGame();

    /**
     * Saves the current state of the game.
     */
    void saveState();

    /**
     * Handles the action of showing additional game information (e.g., instructions or stats).
     *
     * @param event the event triggered by the user
     */
    @FXML
    void handleShow(ActionEvent event);

    /**
     * Handles the return to the previous scene or main menu.
     *
     * @param actionEvent the event triggered by the user
     */
    @FXML
    void handleReturn(ActionEvent actionEvent);

    /**
     * Handles the logic when a player performs an attack.
     */
    void handleAttack();

    /**
     * Handles the logic for ending the current turn and switching to the next player.
     *
     * @param event the event triggered by the user
     */
    @FXML
    void handleTurn(ActionEvent event);

    /**
     * Handles key press events for turn-based actions.
     *
     * @param event the key event triggered by the user
     */
    @FXML
    void keyPressedTurnHandler(KeyEvent event);

    /**
     * Advances the game to the next turn.
     */
    void nextTurn();

    /**
     * Updates the UI label showing the number of sunk boats.
     */
    void updateSankBoatsLabel();

    /**
     * Sets up the player’s and CPU’s boards, determines whose turn it is,
     * and displays a custom message.
     *
     * @param userPlayer the human player
     * @param cpuBoard the computer’s board
     * @param playerTurn whether it is the human player's turn
     * @param customMessageLabel a custom message to display on the game UI
     */
    void setPlayerBoard(Player userPlayer, LogicBoard cpuBoard, boolean playerTurn, String customMessageLabel);
}
