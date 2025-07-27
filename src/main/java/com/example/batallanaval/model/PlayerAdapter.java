package com.example.batallanaval.model;

/**
 * Abstract adapter class that partially implements the IPlayer interface.
 *
 * Provides default implementations for managing the player's nickname, board, and sunk boats.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */

public abstract class PlayerAdapter implements IPlayer {

    /**
     * The nickname of the player.
     */
    protected String nickname;

    /**
     * The number of enemy boats sunk by the player.
     */
    protected int sankBoats;


    /**
     * The player's game board.
     */
    protected LogicBoard playerBoard;


    /**
     * Returns the nickname of the player.
     *
     * @return the player's nickname
     */
    @Override
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the player's current game board.
     *
     * @return the player's LogicBoard
     */
    @Override
    public LogicBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Returns the number of boats the player has sunk.
     *
     * @return the number of sunk boats
     */
    @Override
    public int getSankBoats() {
        return sankBoats;
    }

    /**
     * Sets the player's game board.
     *
     * @param playerBoard the LogicBoard to assign
     */
    @Override
    public void setPlayerBoard(LogicBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    /**
     * Increments the count of sunk boats by one.
     */
    @Override
    public void updateSankBoats() {
        sankBoats++;
    }
}
