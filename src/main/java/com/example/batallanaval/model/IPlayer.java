package com.example.batallanaval.model;

/**
 * Class who defines the Player of the battleship game with his methods
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */

public interface IPlayer {

    /**
     * Returns the nickname of the player.
     *
     * @return the player's nickname
     */
    String getNickname();

    /**
     * Returns the player's current game board.
     *
     * @return the player's LogicBoard
     */
    LogicBoard getPlayerBoard();

    /**
     * Returns the number of boats the player has sunk.
     *
     * @return the number of sunk boats
     */
    int getSankBoats();

    /**
     * Sets the player's game board.
     *
     * @param playerBoard the LogicBoard to assign
     */
    void setPlayerBoard(LogicBoard playerBoard);

    /**
     * Increments the count of sunk boats by one.
     */
    void updateSankBoats();
}
