package com.example.batallanaval.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private ArrayList<LogicBoard> boards;

    public GameState(ArrayList<LogicBoard> boards) {
        this.boards = boards;
    }

    public ArrayList<LogicBoard> getBoards() {
        return boards;
    }
}
