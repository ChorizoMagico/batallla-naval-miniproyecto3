package com.example.batallanaval.model;

public abstract class PlayerAdapter implements IPlayer {
    protected String nickname;
    protected int sankBoats;
    protected LogicBoard playerBoard;

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public LogicBoard getPlayerBoard() {
        return playerBoard;
    }

    @Override
    public int getSankBoats() {
        return sankBoats;
    }

    @Override
    public void setPlayerBoard(LogicBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    @Override
    public void updateSankBoats() {
        sankBoats++;
    }
}
