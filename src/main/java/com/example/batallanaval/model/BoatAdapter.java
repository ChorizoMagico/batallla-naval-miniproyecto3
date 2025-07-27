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

import java.io.Serializable;

public abstract class BoatAdapter implements IBoat, Serializable {

    protected int size;
    protected int resistance;
    protected boolean isHorizontal;
    protected String type;
    protected int[] position;
    protected transient Group shape;

    
    @Override
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

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getResistance() {
        return resistance;
    }

    @Override
    public boolean getIsHorizontal() {
        return isHorizontal;
    }

    @Override
    public void reduceResistance() {
        if (resistance > 0) {
            resistance--;
        }
    }

    @Override
    public void setPosition(int row, int col) {
        position[0] = row;
        position[1] = col;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

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

    @Override
    public Group getShape(){
        return shape;
    }

    @Override
    public String getType(){
        return type;
    }
}
