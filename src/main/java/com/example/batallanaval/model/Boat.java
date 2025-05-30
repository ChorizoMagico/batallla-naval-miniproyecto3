package com.example.batallanaval.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Boat {
    private final int size;
    private int resistance;
    private boolean isHorizontal;
    private String type;
    private int[] position;
    private Group shape;


    public Boat(int size, int resistance, boolean isHorizontal) {
        this.size = size;
        this.position = new int[2];
        this.resistance = resistance;
        this.isHorizontal = isHorizontal;
        shape = new Group();
        shape.setPickOnBounds(true);
    }

    public Node createSegment(int segmentIndex) {
        StackPane segment = new StackPane();
        Image image = null;

        switch (type) {
            case "Portaviones":
                image = new Image(getClass().getResourceAsStream("/com/example/batallanaval/aircarrier.png"));
                break;
            case "Submarino":
                image = new Image(getClass().getResourceAsStream("/com/example/batallanaval/submarine.png"));
                break;
            case "Destructor":
                image = new Image(getClass().getResourceAsStream("/com/example/batallanaval/destructor.png"));
                break;
            case "Fragata":
                image = new Image(getClass().getResourceAsStream("/com/example/batallanaval/frigate.png"));
                break;
        }

        ImageView imgView = new ImageView(image);

        int width = 40;
        int height = 40;

        if (isHorizontal) {
            imgView.setRotate(90);
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

    public int getSize() {
        return size;
    }

    public int getResistance() {
        return resistance;
    }

    public boolean getIsHorizontal() {
        return isHorizontal;
    }

    public void reduceResistance() {
        if (resistance > 0) {
            resistance--;
        }
    }

    public void setPosition(int row, int col) {
        position[0] = row;
        position[1] = col;
    }

    public int[] getPosition() {
        return position;
    }

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

    public void rotateTheBoat(){
        isHorizontal = !isHorizontal;
        int rotation;
        if (isHorizontal) {
            rotation = 90;
        }
        else {
            rotation = 0;
        }
        shape.setRotate(rotation);
    }

    public Group getShape(){
        return shape;
    }

    public String getType(){
        return type;
    }

}