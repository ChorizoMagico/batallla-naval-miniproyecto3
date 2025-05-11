package com.example.batallanaval.model;

import javafx.scene.Group;

import java.util.ArrayList;

public class Boats {

    private Group aircraftCarrier;
    private ArrayList<Group> submarines;
    private ArrayList<Group> destructors;
    private ArrayList<Group> frigates;

    public void createAircraftCarrier(){
        aircraftCarrier = new Group();
    }

}
