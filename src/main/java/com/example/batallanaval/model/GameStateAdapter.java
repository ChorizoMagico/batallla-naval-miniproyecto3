package com.example.batallanaval.model;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameStateAdapter implements IGameState, Serializable {

    protected ArrayList<LogicBoard> boards;

    @Override
    public ArrayList<LogicBoard> getBoards() {
        return boards;
    }
}
