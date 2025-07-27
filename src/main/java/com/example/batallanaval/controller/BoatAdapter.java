package com.example.batallanaval.controller;


import com.example.batallanaval.model.Boat;
import com.example.batallanaval.model.IBoat;
import javafx.scene.Group;
import javafx.scene.Node;

import java.io.Serializable;

public class BoatAdapter implements IBoat, Serializable {

    private static final long serialVersionUID = 1L;

    private final Boat boat;

    public BoatAdapter(Boat boat) {
        this.boat = boat;
    }

    @Override public int getSize() { return boat.getSize(); }
    @Override public int getResistance() { return boat.getResistance(); }
    @Override public void reduceResistance() { boat.reduceResistance(); }
    @Override public boolean getIsHorizontal() { return boat.getIsHorizontal(); }
    @Override public void setPosition(int row, int col) { boat.setPosition(row, col); }
    @Override public int[] getPosition() { return boat.getPosition(); }
    @Override public void rotateTheBoat() { boat.rotateTheBoat(); }
    @Override public Node createSegment(int segmentIndex) { return boat.createSegment(segmentIndex); }
    @Override public Group getShape() { return boat.getShape(); }
    @Override public String getType() { return boat.getType(); }
}
