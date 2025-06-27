package com.example.batallanaval.model;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogicBoard implements Serializable {
    private static final int BOARD_SIZE = 10;
    private int[][] board;
    private List<Boat> boats;
    private ArrayList<Boat> availableBoats;
    private Boat selectedBoat;

    private static final int WATER = 0;
    private static final int BOAT = 1;
    private static final int MISS = 2;
    private static final int HIT = 3;
    private static final int SUNK = 4;

    public LogicBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        boats = new ArrayList<>();
        selectedBoat = null;
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

    public List<Boat> getPlacedBoats() {
        return boats;
    }

    public void selectBoat(int index) {
        selectedBoat = availableBoats.get(index);
        availableBoats.remove(index);
    }

    public void returnBoat(){
        availableBoats.add(selectedBoat);
        selectedBoat = null;
    }

    public void emptySelectedBoat(){
        selectedBoat = null;
    }

    public boolean isBoatSelected(){
        if(selectedBoat != null){
            return true;
        }
        else{
            return false;
        }
    }

    public Boat getSelectedBoat() {
        return selectedBoat;
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

        boat.setPosition(row, col);
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


    public Boat findBoatAt(int row, int col) {
        for (Boat boat : boats) {
            if (isPartOfBoat(boat, row, col)) {
                return boat;
            }
        }
        return null;
    }


    private boolean isPartOfBoat(Boat boat, int row, int col) {
        boolean isHorizontal = boat.getIsHorizontal();
        int[] positions = boat.getPosition();
        if(isHorizontal){
            if(row == positions[0] && (col >= positions[1] && col <= (positions[1]+(boat.getSize()-1)))){
                return true;
            }
        }else{
            if(col == positions[1] && (row >= positions[0] && row <= (positions[0]+(boat.getSize())-1) )){
                return true;
            }
        }
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

    public class DrawBoard {

        /**
         * Fills the board of the game with blue stackpanes that behave like water
         */
        public void loadGridPaneWithWater(GridPane boardGrid, StackPane[][] boatsStack) {
            boardGrid.getChildren().clear();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    boatsStack[i][j] = new StackPane();
                    boatsStack[i][j].setStyle("-fx-background-color: dodgerblue; -fx-border-color: black; -fx-border-width: 1;");
                    boatsStack[i][j].setAlignment(Pos.TOP_LEFT);
                    boardGrid.add(boatsStack[i][j], j, i);
                }
            }
        }

        public void loadShootsInGridPane(StackPane[][] boatsStack, LogicBoard playerBoard, boolean clear) {

            if(clear) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        boatsStack[i][j].getChildren().clear();
                    }
                }
            }

            int[][] board= playerBoard.getBoard();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if(board[i][j]==2){
                        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/miss.png"))));
                        boatsStack[i][j].getChildren().add(imageView);
                    } else if (board[i][j]==3) {
                        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/hit.png"))));
                        boatsStack[i][j].getChildren().add(imageView);
                    } else if (board[i][j]==4) {
                        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/sinl.png"))));
                        boatsStack[i][j].getChildren().add(imageView);
                    }
                }
            }
        }

        /**
         * Reloads the board with the boats placed
         */
        public void loadGridPane(StackPane[][] boatsStack, LogicBoard playerBoard) {

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    boatsStack[i][j].getChildren().clear();
                }
            }

            List<Boat> boats = playerBoard.getBoats();
            for (int b = 0; b < boats.size(); b++) {
                Boat boat = boats.get(b);
                int[] position = boat.getPosition();
                int row = position[0];
                int col = position[1];

                for (int i = 0; i < boat.getSize(); i++) {
                    int r;
                    int c;

                    if (boat.getIsHorizontal()) {
                        r = row;
                        c = col + i;
                    } else {
                        r = row + i;
                        c = col;
                    }

                    Node segment = boat.createSegment(i);
                    boatsStack[r][c].getChildren().add(segment);
                }
            }

            loadShootsInGridPane(boatsStack, playerBoard, false);
        }
    }
}