package com.example.batallanaval.model;

import java.util.ArrayList;

/**
 * Interface that defines the structure for accessing the current state of the game.
 * Provides methods to retrieve the game boards involved in the current session.
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface IGameState {

    /**
     * Returns the list of logic boards used in the current game state.
     *
     * @return An {@code ArrayList} containing the game's {@code LogicBoard} instances.
     */
    ArrayList<LogicBoard> getBoards();
}
