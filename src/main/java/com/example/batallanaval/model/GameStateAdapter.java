package com.example.batallanaval.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract base class that implements the {@code IGameState} interface and provides
 * basic support for managing game boards.
 *
 * <p>
 * Implements {@code Serializable} to allow instances to be saved and loaded.
 * </p>
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public abstract class GameStateAdapter implements IGameState, Serializable {

    /**
     * List of {@code LogicBoard} instances representing the game state.
     */
    protected ArrayList<LogicBoard> boards;

    /**
     * Returns the list of logic boards currently managed by this game state.
     *
     * @return An {@code ArrayList} containing the game's {@code LogicBoard} objects.
     */
    @Override
    public ArrayList<LogicBoard> getBoards() {
        return boards;
    }
}
