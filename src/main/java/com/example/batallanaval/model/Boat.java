package com.example.batallanaval.model;

import javafx.scene.Group;

/**
 * This class represents a specific implementation of a boat used in the Naval Battle game.
 * It extends {@link BoatAdapter} and initializes a boat's size, resistance, orientation, and graphical shape.
 *
 * Each boat object holds its visual representation (shape), initial resistance (i.e., health),
 * orientation (horizontal or vertical), and a position on the game board.
 *
 * The visual shape is represented as a {@link Group} object, which allows JavaFX rendering of the boat.
 *
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public class Boat extends BoatAdapter {

    /**
     * Constructs a Boat with the specified size, resistance, and orientation.
     *
     * @param size the number of segments the boat occupies
     * @param resistance the number of hits the boat can take before being destroyed
     * @param isHorizontal whether the boat is initially placed horizontally
     */
    public Boat(int size, int resistance, boolean isHorizontal) {
        this.size = size;
        this.position = new int[2];
        this.resistance = resistance;
        this.isHorizontal = isHorizontal;
        shape = new Group();
        shape.setPickOnBounds(true);
    }

}