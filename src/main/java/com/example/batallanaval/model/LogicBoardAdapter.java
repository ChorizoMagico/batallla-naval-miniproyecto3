package com.example.batallanaval.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract implementation of the ILogicBoard interface.
 * This class provides base logic for managing the board in the Battleship-style game,
 * including placing boats, tracking hits and misses, and checking for game over.
 *
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */

public abstract class LogicBoardAdapter implements ILogicBoard, Serializable {

    /**
     * Size of the board, for logic reasons
     */
    protected static final int BOARD_SIZE = 10;

    /**
     * A matrix of int, which represents the board
     */
    protected int[][] board;

    /**
     * The boats who are in the board
     */
    protected List<Boat> boats;


    /**
     * The boats who are not in the board
     */
    protected ArrayList<Boat> availableBoats;

    /**
     * Positions shooted
     */
    protected ArrayList<Integer> shotPositions;

    /**
     * The selected boat which will be put on the board
     */
    protected Boat selectedBoat;

    /**
     * A constant which represents that there is water in that cell
     */
    protected static final int WATER = 0;

    /**
     * A constant which represents that there is a boat in that cell
     */
    protected static final int BOAT = 1;

    /**
     * A constant which represents that there is a missed shot in that cell
     */
    protected static final int MISS = 2;

    /**
     * A constant which represents that there is a damaged boat in that cell
     */
    protected static final int HIT = 3;

    /**
     * A constant which represents that there is a sunked boat in that cell
     */
    protected static final int SUNK = 4;

    /**
     * Returns the list of boats that are available to be placed on the board.
     */
    @Override
    public ArrayList<Boat> getAvailableBoats() {
        return availableBoats;
    }

    /**
     * Returns the list of boats that have already been placed on the board.
     */
    @Override
    public List<Boat> getPlacedBoats() {
        return boats;
    }

    /**
     * Selects a boat from the available list by its index and removes it from availability.
     *
     * @param index the index of the boat in the available list
     */
    @Override
    public void selectBoat(int index) {
        selectedBoat = availableBoats.get(index);
        availableBoats.remove(index);
    }

    /**
     * Returns the previously selected boat to the list of available boats.
     */
    @Override
    public void returnBoat() {
        availableBoats.add(selectedBoat);
        selectedBoat = null;
    }

    /**
     * Clears the selected boat without returning it to the available list.
     */
    @Override
    public void emptySelectedBoat() {
        selectedBoat = null;
    }

    /**
     * Checks if a boat is currently selected.
     *
     * @return true if a boat is selected, false otherwise
     */
    @Override
    public boolean isBoatSelected() {
        return selectedBoat != null;
    }

    /**
     * Returns the currently selected boat.
     *
     * @return the selected boat
     */
    @Override
    public Boat getSelectedBoat() {
        return selectedBoat;
    }

    /**
     * Attempts to place a boat at the specified position on the board.
     *
     * @param row  the starting row
     * @param col  the starting column
     * @param boat the boat to place
     * @return true if the boat was placed successfully, false if placement is invalid
     */
    @Override
    public boolean placeBoat(int row, int col, Boat boat) {
        int size = boat.getSize();
        boolean isHorizontal = boat.getIsHorizontal();

        if ((isHorizontal && col + size > BOARD_SIZE) ||
                (!isHorizontal && row + size > BOARD_SIZE)) {
            return false;
        }

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

    /**
     * Shoots at the specified position on the board.
     *
     * @param row the target row
     * @param col the target column
     * @return 0 = miss, 1 = hit, 2 = sunk, 3 = already shot
     */
    @Override
    public int shoot(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return 0;
        }

        int currentState = board[row][col];

        if (currentState == MISS || currentState == HIT || currentState == SUNK) {
            return 3;
        }

        if (currentState == WATER) {
            board[row][col] = MISS;
            return 0;
        }

        if (currentState == BOAT) {
            Boat affectedBoat = findBoatAt(row, col);
            if (affectedBoat != null) {
                affectedBoat.reduceResistance();
                if (affectedBoat.getResistance() <= 0) {
                    markBoatAsSunk(affectedBoat);
                    return 2;
                } else {
                    board[row][col] = HIT;
                    return 1;
                }
            }
        }

        return 0;
    }

    /**
     * Finds and returns the boat located at a specific cell.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the boat found, or null if none found
     */
    @Override
    public Boat findBoatAt(int row, int col) {
        for (Boat boat : boats) {
            if (isPartOfBoat(boat, row, col)) {
                return boat;
            }
        }
        return null;
    }

    /**
     * Checks if the given cell is part of the specified boat.
     *
     * @param boat the boat
     * @param row  the row
     * @param col  the column
     * @return true if the cell is part of the boat, false otherwise
     */
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

    /**
     * Marks all cells of the given boat as sunk.
     *
     * @param boat the boat to mark as sunk
     */
    @Override
    public void markBoatAsSunk(Boat boat) {
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

    /**
     * Checks if all boats on the board have been sunk.
     *
     * @return true if the game is over, false otherwise
     */
    @Override
    public boolean isGameOver() {
        for (Boat boat : boats) {
            if (boat.getResistance() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a copy of the board.
     *
     * @return a 2D array representing the board state
     */
    @Override
    public int[][] getBoard() {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
        for (int r = 0; r < BOARD_SIZE; r++) {
            System.arraycopy(board[r], 0, copy[r], 0, BOARD_SIZE);
        }
        return copy;
    }

    /**
     * Resets the board and clears all placed boats.
     */
    @Override
    public void reset() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        boats = new ArrayList<>();
    }

    /**
     * Returns all boats currently placed on the board.
     *
     * @return a list of boats
     */
    @Override
    public List<Boat> getBoats() {
        return new ArrayList<>(boats);
    }

    /**
     * Prints the current state of the board to the console.
     */
    @Override
    public void printBoard() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Automatically places all available boats on the board in random positions.
     */
    @Override
    public void aleatorizeBoard() {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int p = 1; p <= 100; p++) {
            positions.add(p);
        }

        int row, col, actualPosition;
        do {
            Collections.shuffle(positions);
            actualPosition = positions.get(0);
            selectBoat(0);

            row = (actualPosition - 1) / 10 + 1;
            col = (actualPosition - 1) % 10 + 1;

            if ((int) (Math.random() * 2) + 1 == 1) {
                selectedBoat.rotateTheBoat();
            }

            boolean placed = placeBoat(row - 1, col - 1, selectedBoat);
            if (!placed) {
                selectedBoat.rotateTheBoat();
                placed = placeBoat(row - 1, col - 1, selectedBoat);
            }

            if (placed) {
                int counter = 0;
                if (selectedBoat.getIsHorizontal()) {
                    for (int i = 0; i < selectedBoat.getSize(); i++) {
                        positions.remove(Integer.valueOf(actualPosition + counter));
                        counter++;
                    }
                } else {
                    for (int i = 0; i < selectedBoat.getSize(); i++) {
                        positions.remove(Integer.valueOf(actualPosition + counter));
                        counter += 10;
                    }
                }
            } else {
                positions.remove(Integer.valueOf(actualPosition));
                returnBoat();
            }

        } while (!availableBoats.isEmpty() && !positions.isEmpty());
    }

    /**
     * Shoots a random cell on the board.
     *
     * @return the result of the shot: 0 = miss, 1 = hit, 2 = sunk
     */
    @Override
    public int randomShooting() {

        while (!shotPositions.isEmpty()) {
            int actualPosition = shotPositions.remove(0);
            int row = actualPosition / 10;
            int col = actualPosition % 10;

            int result = shoot(row, col);
            if (result != 3) { // 3 means already shot, so skip those
                return result;
            }
        }

        return 0; // fallback if everything was already shot
    }
}
