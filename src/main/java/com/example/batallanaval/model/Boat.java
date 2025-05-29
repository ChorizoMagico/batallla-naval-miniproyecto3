package com.example.batallanaval.model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Boat {
    private final int size;
    private int resistance;
    private boolean isHorizontal;
    private String type;
    private Group shape;


    public Boat(int size, int resistance, boolean isHorizontal) {
        this.size = size;
        this.resistance = resistance;
        this.isHorizontal = isHorizontal;
        shape = new Group();
        shape.setPickOnBounds(true);
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
        type = "aircraft";
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
        type = "submarine";
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
        type = "destructor";
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
        type = "frigate";
    }

    public void rotateTheBoat(){
        if(isHorizontal == true){
            isHorizontal = false;
            shape.setRotate(0);
        } else {
            isHorizontal = true;
            shape.setRotate(90);
        }
    }

    public Group getShape(){
        return shape;
    }

    public String getType(){
        return type;
    }


}