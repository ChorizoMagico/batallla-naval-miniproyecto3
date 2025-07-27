package com.example.batallanaval.model;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.Serializable;
import java.util.*;

/**
 * LogicBoard represents the main logic structure of the game board.
 * It initializes a 10x10 board and sets up the available fleet.
 * The class also contains an inner class responsible for rendering the board visually.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 */
public class LogicBoard extends LogicBoardAdapter {

    /**
     * Constructs a LogicBoard with a 10x10 board and initializes the fleet:
     * 1 Aircraft Carrier, 2 Submarines, 3 Destroyers, and 4 Frigates.
     */
    public LogicBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        boats = new ArrayList<>();
        selectedBoat = null;
        availableBoats = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            if (i < 1) {
                availableBoats.add(new Boat(4, 4, false));
                availableBoats.get(i).createAircraftCarrier();
            } else if (i < 3) {
                availableBoats.add(new Boat(3, 3, false));
                availableBoats.get(i).createSubmarine();
            } else if (i < 6) {
                availableBoats.add(new Boat(2, 2, false));
                availableBoats.get(i).createDestructor();
            } else if (i < 10) {
                availableBoats.add(new Boat(1, 1, false));
                availableBoats.get(i).createFrigate();
            }
        }
    }

    /**
     * DrawBoard is a helper inner class responsible for visualizing
     * the current state of the board in a GridPane using JavaFX.
     */
    public class DrawBoard {

        /**
         * Fills the given GridPane with blue StackPanes representing water cells.
         *
         * @param boardGrid  The GridPane where the board is rendered.
         * @param boatsStack A 2D array of StackPanes to be populated with water visuals.
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

        /**
         * Loads shot icons (miss, hit, sunk) and optionally clears the board.
         * Also displays sunk boats on the grid.
         *
         * @param boatsStack   The StackPane grid to update.
         * @param playerBoard  The LogicBoard containing shot and boat information.
         * @param clear        Whether to clear all StackPane children before loading shots.
         */
        public void loadShootsInGridPane(StackPane[][] boatsStack, LogicBoard playerBoard, boolean clear) {

            if (clear) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        boatsStack[i][j].getChildren().clear();
                    }
                }

                for (Boat boat : playerBoard.getBoats()) {
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

            int[][] board = playerBoard.getBoard();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (board[i][j] == 2) {
                        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/miss.png"))));
                        boatsStack[i][j].getChildren().add(imageView);
                    } else if (board[i][j] == 3) {
                        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/hit.png"))));
                        boatsStack[i][j].getChildren().add(imageView);
                    } else if (board[i][j] == 4) {
                        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/sink.png"))));
                        boatsStack[i][j].getChildren().add(imageView);
                    }
                }
            }
        }

        /**
         * Loads boats placed by the player on the grid and updates shots.
         *
         * @param boatsStack   The grid where the boats are drawn.
         * @param playerBoard  The LogicBoard that contains boat positions.
         */
        public void loadGridPane(StackPane[][] boatsStack, LogicBoard playerBoard) {

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    boatsStack[i][j].getChildren().clear();
                }
            }

            List<Boat> boats = playerBoard.getBoats();
            for (Boat boat : boats) {
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

            loadShootsInGridPane(boatsStack, playerBoard, false);
        }
    }
}