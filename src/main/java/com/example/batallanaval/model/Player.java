package com.example.batallanaval.model;

/**
 * Class who inherits PlayerAdapter with a constructor
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */

public class Player extends PlayerAdapter{
    /**
     * Creates a player with his data
     * @param nickname Nickname of the player
     * @param playerBoard Logic Board of this player
     * @param sankBoats Number of sank boats
     */
    public Player(String nickname, LogicBoard playerBoard, int sankBoats) {
        this.nickname = nickname;
        this.playerBoard = playerBoard;
        this.sankBoats = sankBoats;
    }
}
