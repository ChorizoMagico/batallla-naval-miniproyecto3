package com.example.batallanaval.model;

import java.util.ArrayList;

public class GameState extends GameStateAdapter {
    public GameState(ArrayList<LogicBoard> boards) {
        this.boards = boards;
    }
}
