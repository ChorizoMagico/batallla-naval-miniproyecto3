package com.example.batallanaval.model;

public class Player extends PlayerAdapter{

    public Player(String nickname, LogicBoard playerBoard, int sankBoats) {
        this.nickname = nickname;
        this.playerBoard = playerBoard;
        this.sankBoats = sankBoats;
    }
}
