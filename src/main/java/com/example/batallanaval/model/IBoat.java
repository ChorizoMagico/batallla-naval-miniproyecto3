package com.example.batallanaval.model;


import javafx.scene.Group;
import javafx.scene.Node;


public interface IBoat {

    Node createSegment(int segmentIndex);

    int getSize();

    int getResistance();

    boolean getIsHorizontal();

    void reduceResistance();

    void setPosition(int row, int col);

    int[] getPosition();

    void createAircraftCarrier();

    void createSubmarine();

    void createDestructor();

    void createFrigate();

    void rotateTheBoat();

    Group getShape();

    String getType();
}
