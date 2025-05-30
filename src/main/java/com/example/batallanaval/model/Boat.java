package com.example.batallanaval.model;

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

    public Node createSegment() {
        StackPane segment = new StackPane();

        // Water background
        Rectangle water = new Rectangle(41, 41);
        water.setFill(Color.DODGERBLUE);
        water.setStroke(Color.BLACK);
        water.setStrokeType(StrokeType.INSIDE);

        // Boat image
        ImageView imgView = new ImageView();
        switch(type) {
            case "Portaviones":
                imgView.setImage(new Image(getClass().getResourceAsStream("/com/example/batallanaval/aircarrier.png")));
                break;
            case "Submarino":
                imgView.setImage(new Image(getClass().getResourceAsStream("/com/example/batallanaval/submarine.png")));
                break;
            case "Destructor":
                imgView.setImage(new Image(getClass().getResourceAsStream("/com/example/batallanaval/destructor.png")));
                break;
            case "Fragata":
                imgView.setImage(new Image(getClass().getResourceAsStream("/com/example/batallanaval/frigate.png")));
                break;
        }

        imgView.setFitWidth(40);
        imgView.setFitHeight(40);

        segment.getChildren().addAll(water, imgView);
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
        shape.setRotate(isHorizontal ? 90 : 0);
    }

    public Group getShape(){
        return shape;
    }

    public String getType(){
        return type;
    }

}