package com.example.batallanaval.model;

import javafx.scene.Group;

public class Boat extends BoatAdapter {

    public Boat(int size, int resistance, boolean isHorizontal) {
        this.size = size;
        this.position = new int[2];
        this.resistance = resistance;
        this.isHorizontal = isHorizontal;
        shape = new Group();
        shape.setPickOnBounds(true);
    }

}