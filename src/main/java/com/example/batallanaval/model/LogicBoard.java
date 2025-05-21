package com.example.batallanaval.model;


import java.util.ArrayList;
import java.util.List;

public class LogicBoard {
    private static final int BOARD_SIZE = 10;
    private int[][] board;
    private List<Boat> boats;
    private ArrayList<Boat> availableBoats;

    private static final int WATER = 0;
    private static final int BOAT = 1;
    private static final int MISS = 2;
    private static final int HIT = 3;
    private static final int SUNK = 4;

    public LogicBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        boats = new ArrayList<>();
        availableBoats = new ArrayList<>();

        for(int i =0; i < 10; i++ ){
            if(i < 1){
                availableBoats.add(new Boat(4, 4, false));
                availableBoats.get(i).createAircraftCarrier();
            }
            else if(i < 3){
                availableBoats.add(new Boat(3, 3, false));
                availableBoats.get(i).createSubmarine();}
            else if (i < 6) {
                availableBoats.add(new Boat(2, 2, false));
                availableBoats.get(i).createDestructor();}
            else if (i < 10) {
                availableBoats.add(new Boat(1, 1, false));
                availableBoats.get(i).createFrigate();}
        }
    }

    public ArrayList<Boat> getAvailableBoats() {
        return availableBoats;
    }

    public boolean placeBoat(int row, int col, Boat boat) {
        int size = boat.getSize();
        boolean isHorizontal = boat.getIsHorizontal();


        if (isHorizontal)
        {
            if (col + size > BOARD_SIZE) return false;
        } else {
            if (row + size > BOARD_SIZE) return false;
        }

        if (isHorizontal) {
            for (int c = col; c < col + size; c++) {
                if (board[row][c] != WATER) return false;
            }
        } else {
            for (int r = row; r < row + size; r++) {
                if (board[r][col] != WATER) return false;
            }
        }

        if (isHorizontal) {
            for (int c = col; c < col + size; c++) {
                board[row][c] = BOAT;
            }
        } else {
            for (int r = row; r < row + size; r++) {
                board[r][col] = BOAT;
            }
        }

        boats.add(boat);
        return true;
    }


    public int shoot(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return 0; // Disparo fuera del tablero, descachooo!
        }


        int currentState = board[row][col];

        //si ya le dispararon xd
        if (currentState == MISS || currentState == HIT || currentState == SUNK) {
            return 0; // Ya se disparó aquí
        }

        // Si es agua
        if (currentState == WATER) {
            board[row][col] = MISS;
            return 0; // Agua
        }

        // Si es un barco
        if (currentState == BOAT) {
            board[row][col] = HIT;

            // Buscar el barco afectado
            Boat affectedBoat = findBoatAt(row, col);
            if (affectedBoat != null) {
                affectedBoat.reduceResistance();

                if (affectedBoat.getResistance() <= 0) {
                    // Marcar todas las partes del barco como hundidas
                    markBoatAsSunk(affectedBoat);
                    return 2;
                }
            }
            return 1; // Impacto
        }

        return 0; // Por defecto, considerar como agua
    }


    private Boat findBoatAt(int row, int col) {
        for (Boat boat : boats) {
            if (isPartOfBoat(boat, row, col)) {
                return boat;
            }
        }
        return null;
    }


    private boolean isPartOfBoat(Boat boat, int row, int col) {
        // Implementación simplificada - en una versión real deberíamos rastrear las posiciones exactas
        return board[row][col] == BOAT || board[row][col] == HIT || board[row][col] == SUNK;
    }


    private void markBoatAsSunk(Boat boat) {
        // Buscar todas las celdas que pertenecen a este barco
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (board[r][c] == HIT && isPartOfBoat(boat, r, c)) {
                    board[r][c] = SUNK;
                }
            }
        }
    }


    public boolean isGameOver() {
        // Verificar si quedan barcos no hundidos
        for (Boat boat : boats) {
            if (boat.getResistance() > 0) {
                return false;
            }
        }
        return true;
    }


    public int[][] getBoard() {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
        for (int r = 0; r < BOARD_SIZE; r++) {
            System.arraycopy(board[r], 0, copy[r], 0, BOARD_SIZE);
        }
        return copy;
    }


    public void reset() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        boats = new ArrayList<>();
    }


    public List<Boat> getBoats() {
        return new ArrayList<>(boats);
    }

    //pa probar
    public void printBoard() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }
}