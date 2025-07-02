package com.example.batallanaval.model;

public class Player{

    private String nickname;
    private int sankBoats;
    private LogicBoard playerBoard;

    public Player(String nickname, LogicBoard playerBoard, int sankBoats) {
        this.nickname = nickname;
        this.playerBoard = playerBoard;
        this.sankBoats = sankBoats;
    }

    public String getNickname() {
        return nickname;
    }

    public LogicBoard getPlayerBoard() {
        return playerBoard;
    }

    public int getSankBoats() {
        return sankBoats;
    }

    public void setPlayerBoard(LogicBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void updateSankBoats() {
        sankBoats++;
    }
}
