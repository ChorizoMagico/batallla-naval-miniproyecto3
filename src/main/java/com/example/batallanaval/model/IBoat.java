package com.example.batallanaval.model;

import javafx.scene.Group;
import javafx.scene.Node;

public interface IBoat {
    int getSize();
    int getResistance();
    void reduceResistance();
    boolean getIsHorizontal();
    void setPosition(int row, int col);
    int[] getPosition();
    void rotateTheBoat();
    Node createSegment(int segmentIndex);
    Group getShape();
    String getType();
}
