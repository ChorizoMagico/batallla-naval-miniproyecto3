package com.example.batallanaval.model;

import java.util.ArrayList;
import java.util.List;

public interface ILogicBoard {


    ArrayList<Boat> getAvailableBoats();

    List<Boat> getPlacedBoats();

    void selectBoat(int index);

    void returnBoat();

    void emptySelectedBoat();

    boolean isBoatSelected();

    Boat getSelectedBoat();

    boolean placeBoat(int row, int col, Boat boat);


    int shoot(int row, int col);


    Boat findBoatAt(int row, int col);


    boolean isPartOfBoat(Boat boat, int row, int col);


    void markBoatAsSunk(Boat boat);


    boolean isGameOver();


    int[][] getBoard();


    void reset();


    List<Boat> getBoats();


    void printBoard();

    void aleatorizeBoard();

    int randomShooting();

}
