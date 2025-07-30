package com.example.batallanaval.model;


import javafx.scene.Group;
import javafx.scene.Node;

/**
 * Interface that defines the structure and behavior of a boat used in a Battleship-style game.
 * Provides methods for creating, configuring, and interacting with boats on the board.
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public interface IBoat {

    /**
     * Creates a visual segment of the boat based on its index.
     *
     * @param segmentIndex The index of the boat segment to create.
     * @return A {@code Node} representing the visual element of the segment.
     */
    Node createSegment(int segmentIndex);

    /**
     * Returns the size (number of segments) of the boat.
     *
     * @return The size of the boat.
     */
    int getSize();

    /**
     * Returns the current resistance (health) of the boat.
     *
     * @return The boat's remaining resistance.
     */
    int getResistance();

    /**
     * Indicates whether the boat is oriented horizontally.
     *
     * @return {@code true} if the boat is horizontal, {@code false} if vertical.
     */
    boolean getIsHorizontal();

    /**
     * Decreases the resistance (health) of the boat by one.
     */
    void reduceResistance();

    /**
     * Sets the starting position (row and column) of the boat on the board.
     *
     * @param row The row where the boat starts.
     * @param col The column where the boat starts.
     */
    void setPosition(int row, int col);

    /**
     * Returns the current position of the boat on the board.
     *
     * @return An array with two elements: [row, col].
     */
    int[] getPosition();

    /**
     * Configures the boat as an aircraft carrier.
     */
    void createAircraftCarrier();

    /**
     * Configures the boat as a submarine.
     */
    void createSubmarine();

    /**
     * Configures the boat as a destroyer.
     */
    void createDestructor();

    /**
     * Configures the boat as a frigate.
     */
    void createFrigate();

    /**
     * Changes the orientation of the boat (horizontal ↔ vertical).
     */
    void rotateTheBoat();

    /**
     * Returns the visual representation of the entire boat as a JavaFX {@code Group}.
     *
     * @return A {@code Group} node containing the visual elements of the boat.
     */
    Group getShape();

    /**
     * Returns the type of the boat (e.g., "Aircraft Carrier", "Submarine", etc.).
     *
     * @return A string representing the boat type.
     */
    String getType();
}
