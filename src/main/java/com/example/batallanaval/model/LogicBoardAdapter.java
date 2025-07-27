package com.example.batallanaval.model;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class LogicBoardAdapter implements ILogicBoard, Serializable {

    protected static final int BOARD_SIZE = 10;
    protected int[][] board;
    protected List<Boat> boats;
    protected ArrayList<Boat> availableBoats;
    protected ArrayList<Integer> shotPositions;
    protected Boat selectedBoat;

    protected static final int WATER = 0;
    protected static final int BOAT = 1;
    protected static final int MISS = 2;
    protected static final int HIT = 3;
    protected static final int SUNK = 4;

    @Override
    public ArrayList<Boat> getAvailableBoats() {
        return availableBoats;
    }

    @Override
    public List<Boat> getPlacedBoats() {
        return boats;
    }

    @Override
    public void selectBoat(int index) {
        selectedBoat = availableBoats.get(index);
        availableBoats.remove(index);
    }

    @Override
    public void returnBoat(){
        availableBoats.add(selectedBoat);
        selectedBoat = null;
    }

    @Override
    public void emptySelectedBoat(){
        selectedBoat = null;
    }

    @Override
    public boolean isBoatSelected(){
        if(selectedBoat != null){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Boat getSelectedBoat() {
        return selectedBoat;
    }

    @Override
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

        boat.setPosition(row, col);
        boats.add(boat);
        return true;
    }


    @Override
    public int shoot(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return 0; // Disparo fuera del tablero, descachooo!
        }


        int currentState = board[row][col];

        //si ya le dispararon xd
        if (currentState == MISS || currentState == HIT || currentState == SUNK) {
            return 3; // Ya se disparó aquí
        }

        // Si es agua
        if (currentState == WATER) {
            board[row][col] = MISS;
            return 0; // Agua
        }

        // Si es un barco
        if (currentState == BOAT) {
            Boat affectedBoat = findBoatAt(row, col);
            if (affectedBoat != null) {
                affectedBoat.reduceResistance();
                if (affectedBoat.getResistance() <= 0) {
                    markBoatAsSunk(affectedBoat);
                    return 2; // Hundido
                } else {
                    board[row][col] = HIT;
                    return 1; // Impacto
                }
            }
        }

        return 0; // Por defecto, considerar como agua
    }


    @Override
    public Boat findBoatAt(int row, int col) {
        for (Boat boat : boats) {
            if (isPartOfBoat(boat, row, col)) {
                return boat;
            }
        }
        return null;
    }


    @Override
    public boolean isPartOfBoat(Boat boat, int row, int col) {
        int[] positions = boat.getPosition();
        int boatRow = positions[0];
        int boatCol = positions[1];
        int size = boat.getSize();
        boolean isHorizontal = boat.getIsHorizontal();

        if (isHorizontal) {
            return row == boatRow && col >= boatCol && col < boatCol + size;
        } else {
            return col == boatCol && row >= boatRow && row < boatRow + size;
        }
    }

    @Override
    public void markBoatAsSunk(Boat boat) {
        // Buscar todas las celdas que pertenecen a este barco
        int[] position = boat.getPosition();
        int row = position[0];
        int col = position[1];
        int size = boat.getSize();
        boolean isHorizontal = boat.getIsHorizontal();

        for (int i = 0; i < size; i++) {
            int r = isHorizontal ? row : row + i;
            int c = isHorizontal ? col + i : col;
            board[r][c] = SUNK;
        }
    }

    @Override
    public boolean isGameOver() {
        // Verificar si quedan barcos no hundidos
        for (Boat boat : boats) {
            if (boat.getResistance() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int[][] getBoard() {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
        for (int r = 0; r < BOARD_SIZE; r++) {
            System.arraycopy(board[r], 0, copy[r], 0, BOARD_SIZE);
        }
        return copy;
    }

    @Override
    public void reset() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        boats = new ArrayList<>();
    }

    @Override
    public List<Boat> getBoats() {
        return new ArrayList<>(boats);
    }

    @Override
    public void printBoard() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void aleatorizeBoard(){
        ArrayList<Integer> positions = new ArrayList<>();
        for(int p = 1; p <= 100; p++){
            positions.add(p);
        }


        int row, col, actualPosition;
        do{
            Collections.shuffle(positions);
            actualPosition = positions.get(0);
            selectBoat(0);

            row = (actualPosition - 1) / 10 + 1;
            col = (actualPosition - 1) % 10 + 1;

            int random = (int)(Math.random() * 2) + 1;
            if(random == 1){
                selectedBoat.rotateTheBoat();
            }

            boolean placedBoat = placeBoat(row-1, col-1, selectedBoat);

            if(!placedBoat){
                selectedBoat.rotateTheBoat();
                placedBoat = placeBoat(row-1, col-1, selectedBoat);
            }

            if(placedBoat){
                int counter = 0;
                if(selectedBoat.getIsHorizontal()){
                    for(int i = 0; i < selectedBoat.getSize(); i++){
                        positions.remove(Integer.valueOf(actualPosition+counter));
                        counter++;
                    }
                }else{
                    for(int i = 0; i < selectedBoat.getSize(); i++){
                        positions.remove(Integer.valueOf(actualPosition+counter));
                        counter = counter + 10;
                    }
                }
            }else{
                positions.remove(Integer.valueOf(actualPosition));
                returnBoat();
            }
        }while(!availableBoats.isEmpty() && !positions.isEmpty());
    }

    @Override
    public int randomShooting(){
        shotPositions = new ArrayList<>();
        for(int p = 1; p <= 100; p++){
            shotPositions.add(p);
        }

        Collections.shuffle(shotPositions);
        int actualPosition = shotPositions.get(0);
        int row = (actualPosition - 1) / 10 + 1;
        int col = (actualPosition - 1) % 10 + 1;

        int result = shoot(row, col);
        if(result == 1 || result == 2){
            shotPositions.remove(Integer.valueOf(actualPosition));
        }

        return result;
    }
}
