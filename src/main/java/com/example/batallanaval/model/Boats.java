package com.example.batallanaval.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Boats {

    private Group aircraftCarrier;
    private ArrayList<Group> submarines;
    private ArrayList<Group> destructors;
    private ArrayList<Group> frigates;

    public void createAircraftCarrier(){
        aircraftCarrier = new Group();

        Rectangle body = new Rectangle(0, 0, 50, 200);
        body.setArcWidth(20);
        body.setArcHeight(20);
        body.setFill(Color.SADDLEBROWN);
        body.setStroke(Color.BLACK);

        Rectangle insideBody = new Rectangle(10, 10, 30, 180);
        insideBody.setArcWidth(3);
        insideBody.setArcHeight(3);
        insideBody.setFill(Color.SADDLEBROWN);
        insideBody.setStroke(Color.BLACK);

        aircraftCarrier.getChildren().addAll(body, insideBody);

        for(int i = 0; i < 6; i++){
            {
                Rectangle window = new Rectangle(15+(60*i), 15+((i/3)*i), 50, 50);
            }
        }
    }

}
