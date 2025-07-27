package com.example.batallanaval.model;

public interface IPlayer {

    String getNickname();

    LogicBoard getPlayerBoard();

    int getSankBoats();

    void setPlayerBoard(LogicBoard playerBoard);

    void updateSankBoats();
}
