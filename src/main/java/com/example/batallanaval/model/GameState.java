package com.example.batallanaval.model;

import java.util.ArrayList;

/**
 * Concrete implementation of {@code GameStateAdapter} that initializes the game state
 * with a given list of logic boards.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public class GameState extends GameStateAdapter {
    /**
     * Constructs a new {@code GameState} with the specified list of boards.
     *
     * @param boards An {@code ArrayList} of {@code LogicBoard} instances representing the game boards.
     */
    public GameState(ArrayList<LogicBoard> boards) {
        this.boards = boards;
    }
}
