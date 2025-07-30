package com.example.batallanaval.model;

import exceptions.ImageNotFoundException;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Abstract base class that implements the {@code IBoat} interface and provides
 * default behaviors and properties for different types of boats used in the Battleship-style game.
 *
 * <p>This class also implements {@code Serializable} to allow saving and restoring boat state.
 * The {@code shape} field is marked {@code transient} as it is a JavaFX node that should not be serialized.</p>
 * @author Juan Esteban Arias
 * @author Junior Lasprilla Prada
 * @author Steven Fernando Aragón
 * @version 1.0
 */
public abstract class BoatAdapter implements IBoat, Serializable {

    /**
     * Number of segments that make up the boat.
     */
    protected int size;

    /**
     * Remaining resistance (health) of the boat.
     */
    protected int resistance;

    /**
     * Orientation of the boat: {@code true} if horizontal, {@code false} if vertical.
     */
    protected boolean isHorizontal;

    /**
     * The type of boat (e.g., "Portaviones", "Submarino", etc.).
     */
    protected String type;

    /**
     * The position of the boat on the board, as a [row, col] pair.
     */
    protected int[] position;

    /**
     * Visual representation of the boat.
     * Marked as {@code transient} since nodes are not serializable.
     */
    protected transient Group shape;

    /**
     * Creates a visual segment of the boat at the specified index.
     * Loads the appropriate image depending on the boat type and orientation.
     *
     * @param segmentIndex The index of the segment within the boat.
     * @return A {@code Node} representing the segment.
     */
    @Override
    public Node createSegment(int segmentIndex) {
        StackPane segment = new StackPane();
        Image image = null;
        InputStream imageStream = null;

        switch (type) {
            case "Portaviones":
                imageStream = getClass().getResourceAsStream("/com/example/batallanaval/aircarrier.png");
                if (imageStream == null) {
                    throw new ImageNotFoundException("No se encontró la imagen para 'Portaviones'");
                }
                image = new Image(imageStream);
                break;

            case "Submarino":
                imageStream = getClass().getResourceAsStream("/com/example/batallanaval/submarine.png");
                if (imageStream == null) {
                    throw new ImageNotFoundException("No se encontró la imagen para 'Submarino'");
                }
                image = new Image(imageStream);
                break;

            case "Destructor":
                imageStream = getClass().getResourceAsStream("/com/example/batallanaval/destructor.png");
                if (imageStream == null) {
                    throw new ImageNotFoundException("No se encontró la imagen para 'Destructor'");
                }
                image = new Image(imageStream);
                break;

            case "Fragata":
                imageStream = getClass().getResourceAsStream("/com/example/batallanaval/frigate.png");
                if (imageStream == null) {
                    throw new ImageNotFoundException("No se encontró la imagen para 'Fragata'");
                }
                image = new Image(imageStream);
                break;

            default:
                throw new IllegalArgumentException("Tipo de barco no válido: " + type);
        }

        ImageView imgView = new ImageView(image);

        int width = 40;
        int height = 40;

        if (isHorizontal) {
            imgView.setRotate(270);
            imgView.setViewport(new Rectangle2D(0, segmentIndex * height, width, height));

        } else {
            imgView.setViewport(new Rectangle2D(0, segmentIndex * height, width, height));
        }

        imgView.setFitWidth(40);
        imgView.setFitHeight(40);
        imgView.setPreserveRatio(false);

        segment.getChildren().add(imgView);
        return segment;
    }

    /**
     * Returns the size (number of segments) of the boat.
     *
     * @return The boat's size.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Returns the current resistance (health) of the boat.
     *
     * @return The remaining resistance value.
     */
    @Override
    public int getResistance() {
        return resistance;
    }

    /**
     * Returns the current orientation of the boat.
     *
     * @return {@code true} if the boat is horizontal; {@code false} otherwise.
     */
    @Override
    public boolean getIsHorizontal() {
        return isHorizontal;
    }

    /**
     * Decreases the boat's resistance by 1, if it is greater than 0.
     */
    @Override
    public void reduceResistance() {
        if (resistance > 0) {
            resistance--;
        }
    }

    /**
     * Sets the position of the boat on the board.
     *
     * @param row The row index.
     * @param col The column index.
     */
    @Override
    public void setPosition(int row, int col) {
        position[0] = row;
        position[1] = col;
    }

    /**
     * Returns the current position of the boat on the board.
     *
     * @return An array containing the row and column coordinates.
     */
    @Override
    public int[] getPosition() {
        return position;
    }

    /**
     * Configures this boat as an aircraft carrier, with a specific image and shape.
     */
    @Override
    public void createAircraftCarrier() {

        // Mar
        Rectangle water = new Rectangle(41.0, 164.0);
        water.setArcWidth(5.0);
        water.setArcHeight(5.0);
        water.setFill(Color.DODGERBLUE);
        water.setStroke(Color.BLACK);
        water.setStrokeType(StrokeType.INSIDE);
        water.setStrokeWidth(1.0);

        //Cuerpo
        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/aircarrier.png"))));

        shape.getChildren().addAll(water, imageView);
        type = "Portaviones";
    }

    /**
     * Configures this boat as a submarine, with a specific image and shape.
     */
    @Override
    public void createSubmarine() {

        Rectangle water = new Rectangle(41.0, 123.0);
        water.setArcWidth(5.0);
        water.setArcHeight(5.0);
        water.setFill(Color.DODGERBLUE);
        water.setStroke(Color.BLACK);
        water.setStrokeType(StrokeType.INSIDE);
        water.setStrokeWidth(1.0);

        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/submarine.png"))));

        shape.getChildren().addAll(water, imageView);
        type = "Submarino";
    }

    /**
     * Configures this boat as a destroyer, with a specific image and shape.
     */
    @Override
    public void createDestructor(){
        Rectangle water = new Rectangle(41.0, 82.0);
        water.setArcWidth(5.0);
        water.setArcHeight(5.0);
        water.setFill(Color.DODGERBLUE);
        water.setStroke(Color.BLACK);
        water.setStrokeType(StrokeType.INSIDE);
        water.setStrokeWidth(1.0);

        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/destructor.png"))));

        shape.getChildren().addAll(water, imageView);
        type = "Destructor";
    }

    /**
     * Configures this boat as a frigate, with a specific image and shape.
     */
    @Override
    public void createFrigate(){
        Rectangle water = new Rectangle(41.0, 41.0);
        water.setArcWidth(5.0);
        water.setArcHeight(5.0);
        water.setFill(Color.DODGERBLUE);
        water.setStroke(Color.BLACK);
        water.setStrokeType(StrokeType.INSIDE);
        water.setStrokeWidth(1.0);

        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/com/example/batallanaval/frigate.png"))));

        shape.getChildren().addAll(water, imageView);
        type = "Fragata";
    }

    /**
     * Rotates the boat's orientation and updates the visual representation.
     */
    @Override
    public void rotateTheBoat(){
        isHorizontal = !isHorizontal;
        int rotation;
        if (isHorizontal) {
            rotation = 270;
        }
        else {
            rotation = 0;
        }
        shape.setRotate(rotation);
    }

    /**
     * Returns the group that visually represents the boat.
     *
     * @return The boat's shape as a {@code Group}.
     */
    @Override
    public Group getShape(){
        return shape;
    }

    /**
     * Returns the type of the boat.
     *
     * @return A string representing the boat type (e.g., "Fragata").
     */
    @Override
    public String getType(){
        return type;
    }
}
