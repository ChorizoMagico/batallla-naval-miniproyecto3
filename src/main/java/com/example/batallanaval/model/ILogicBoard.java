package com.example.batallanaval.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface that defines the core logic for a Battleship-style board.
 * Provides methods for managing boats, handling user interaction,
 * tracking shots, and managing game state.
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface ILogicBoard {

    /**
     * Returns the list of boats that are still available for placement on the board.
     *
     * @return A list of available {@code Boat} objects.
     */
    ArrayList<Boat> getAvailableBoats();

    /**
     * Returns the list of boats that have already been placed on the board.
     *
     * @return A list of placed {@code Boat} objects.
     */
    List<Boat> getPlacedBoats();

    /**
     * Selects a boat by its index in the list of available boats.
     *
     * @param index Index of the boat to select.
     */
    void selectBoat(int index);

    /**
     * Returns the currently selected boat back to the list of available boats.
     */
    void returnBoat();

    /**
     * Removes all segments of the currently selected boat (empties it).
     */
    void emptySelectedBoat();

    /**
     * Checks whether there is currently a selected boat.
     *
     * @return {@code true} if a boat is selected, {@code false} otherwise.
     */
    boolean isBoatSelected();

    /**
     * Gets the currently selected boat.
     *
     * @return The selected {@code Boat}, or {@code null} if none is selected.
     */
    Boat getSelectedBoat();

    /**
     * Attempts to place a boat on the board at the specified coordinates.
     *
     * @param row  The row coordinate for placement.
     * @param col  The column coordinate for placement.
     * @param boat The boat to place.
     * @return {@code true} if the boat was placed successfully, {@code false} otherwise.
     */
    boolean placeBoat(int row, int col, Boat boat);

    /**
     * Shoots at a specific cell on the board.
     *
     * @param row The row to shoot at.
     * @param col The column to shoot at.
     * @return An integer code representing the result (e.g., hit, miss, sunk).
     */
    int shoot(int row, int col);

    /**
     * Finds and returns the boat located at the given coordinates.
     *
     * @param row The row of the target cell.
     * @param col The column of the target cell.
     * @return The {@code Boat} at the specified position, or {@code null} if none is found.
     */
    Boat findBoatAt(int row, int col);

    /**
     * Checks whether a given cell is part of the specified boat.
     *
     * @param boat The boat to check.
     * @param row  The row of the cell.
     * @param col  The column of the cell.
     * @return {@code true} if the cell is part of the boat, {@code false} otherwise.
     */
    boolean isPartOfBoat(Boat boat, int row, int col);

    /**
     * Marks the specified boat as sunk.
     *
     * @param boat The boat to mark as sunk.
     */
    void markBoatAsSunk(Boat boat);

    /**
     * Checks whether the game is over (i.e., all boats have been sunk).
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    boolean isGameOver();

    /**
     * Returns the 2D array representing the current board state.
     *
     * @return A 2D integer array representing the board.
     */
    int[][] getBoard();

    /**
     * Resets the board to its initial empty state.
     */
    void reset();

    /**
     * Returns all boats associated with the board (both placed and unplaced, depending on the implementation).
     *
     * @return A list of all {@code Boat} objects.
     */
    List<Boat> getBoats();

    /**
     * Prints the current board state to the console.
     */
    void printBoard();

    /**
     * Randomly places all boats on the board.
     */
    void aleatorizeBoard();

    /**
     * Performs a random shot on the board.
     *
     * @return An integer code representing the result of the shot.
     */
    int randomShooting();

}
