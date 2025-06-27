package com.example.batallanaval.model;

import java.io.Serializable;

public class Player{

    private String nickname;
    private int sinkedBoats;
    private LogicBoard playerBoard;

    public Player(String nickname, LogicBoard playerBoard, int sinkedBoats) {
        this.nickname = nickname;
        this.playerBoard = playerBoard;
        this.sinkedBoats = sinkedBoats;
    }

    public String getNickname() {
        return nickname;
    }

    public LogicBoard getPlayerBoard() {
        return playerBoard;
    }

    public int getSinkedBoats() {
        return sinkedBoats;
    }

    public void setPlayerBoard(LogicBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void updateSinkedBoats() {
        sinkedBoats++;
    }
}
