package com.example.batallanaval.model;


import com.example.batallanaval.controller.BoatAdapter;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.Serializable;
import java.util.*;

public class LogicBoard implements Serializable {
    private static final int BOARD_SIZE = 10;
    private int[][] board;
    private ArrayList<Integer> shotPositions;
    private List<IBoat> boats;
    private ArrayList<IBoat> availableBoats;
    private IBoat selectedBoat;

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

        for (int i = 0; i < 10; i++) {
            Boat rawBoat;
            if (i < 1) {
                rawBoat = new Boat(4, 4, false);
                rawBoat.createAircraftCarrier();
            } else if (i < 3) {
                rawBoat = new Boat(3, 3, false);
                rawBoat.createSubmarine();
            } else if (i < 6) {
                rawBoat = new Boat(2, 2, false);
                rawBoat.createDestructor();
            } else {
                rawBoat = new Boat(1, 1, false);
                rawBoat.createFrigate();
            }

            IBoat boat = new BoatAdapter(rawBoat);
            availableBoats.add(boat);
        }

    }

    public ArrayList<IBoat> getAvailableBoats() {
        return availableBoats;
    }

    public List<IBoat> getPlacedBoats() {
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

    public IBoat getSelectedBoat() {
        return selectedBoat;
    }

    public boolean placeBoat(int row, int col, IBoat boat) {
        int size = boat.getSize();
        boolean isHorizontal = boat.getIsHorizontal();

        if (isHorizontal && col + size > BOARD_SIZE) return false;
        if (!isHorizontal && row + size > BOARD_SIZE) return false;

        for (int i = 0; i < size; i++) {
            int r = isHorizontal ? row : row + i;
            int c = isHorizontal ? col + i : col;
            if (board[r][c] != WATER) return false;
        }

        for (int i = 0; i < size; i++) {
            int r = isHorizontal ? row : row + i;
            int c = isHorizontal ? col + i : col;
            board[r][c] = BOAT;
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
            return 3; // Ya se disparó aquí
        }

        // Si es agua
        if (currentState == WATER) {
            board[row][col] = MISS;
            return 0; // Agua
        }

        // Si es un barco
        if (currentState == BOAT) {
            IBoat affectedBoat = findBoatAt(row, col);
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


    public IBoat findBoatAt(int row, int col) {
        for (IBoat boat : boats) {
            if (isPartOfBoat(boat, row, col)) {
                return boat;
            }
        }
        return null;
    }


    private boolean isPartOfBoat(IBoat boat, int row, int col) {
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


    private void markBoatAsSunk(IBoat boat) {
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


    public boolean isGameOver() {
        // Verificar si quedan barcos no hundidos
        for (IBoat boat : boats) {
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


    public List<IBoat> getBoats() {
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

                for (IBoat boat : playerBoard.getBoats()) {
                    if (boat.getResistance() == 0) {
                        int[] position = boat.getPosition();
                        int row = position[0];
                        int col = position[1];

                        for (int i = 0; i < boat.getSize(); i++) {
                            int r, c;
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
                        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/sink.png"))));
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

            List<IBoat> boats = playerBoard.getBoats();
            for (int b = 0; b < boats.size(); b++) {
                IBoat boat = boats.get(b);
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