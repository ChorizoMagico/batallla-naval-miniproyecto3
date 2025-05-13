package com.example.batallanaval.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

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
        Rectangle sea = new Rectangle(1.0, -5.0, 115.0, 325.0);
        sea.setFill(Color.DODGERBLUE);
        sea.setArcWidth(5);
        sea.setArcHeight(5);
        sea.setStroke(Color.BLACK);

        // Cubierta principal (madera)
        Rectangle principal = new Rectangle(15.0, 71.0, 88.0, 241.0);
        principal.setFill(Color.web("#8c691b"));
        principal.setArcWidth(5);
        principal.setArcHeight(5);
        principal.setStroke(Color.BLACK);

        // Pista selvática
        Rectangle pista = new Rectangle(27.0, 93.0, 65.0, 200.0);
        pista.setFill(Color.web("#68ae27"));
        pista.setArcWidth(5);
        pista.setArcHeight(5);
        pista.setStroke(Color.BLACK);

        // Aviones verticales
        Polygon airplane1 = new Polygon(-68.0, 1.0, -68.0, -40.0, -45.0, -40.0, -45.0, 1.0);
        airplane1.setLayoutX(104.0);
        airplane1.setLayoutY(155.0);
        airplane1.setFill(Color.web("#514b10"));
        airplane1.setStroke(Color.BLACK);

        Polygon airplane2 = new Polygon(-68.0, 1.0, -68.0, -40.0, -45.0, -40.0, -45.0, 1.0);
        airplane2.setLayoutX(129.0);
        airplane2.setLayoutY(268.0);
        airplane2.setFill(Color.web("#514b10"));
        airplane2.setStroke(Color.BLACK);

        Polygon airplane3 = new Polygon(-68.0, 1.0, -68.0, -40.0, -45.0, -40.0, -45.0, 1.0);
        airplane3.setLayoutX(116.0);
        airplane3.setLayoutY(212.0);
        airplane3.setFill(Color.web("#514b10"));
        airplane3.setStroke(Color.BLACK);

        // Torre de mando inclinada
        Polygon tower = new Polygon(-89.0, 17.0, -73.0, -34.0, -17.0, -34.0, -3.0, 16.0);
        tower.setLayoutX(105.0);
        tower.setLayoutY(55.0);
        tower.setFill(Color.web("#4f3d10"));
        tower.setStroke(Color.BLACK);

        // Agregar todo al grupo
        shape.getChildren().addAll(sea, principal, pista, airplane1, airplane2, airplane3, tower);
        type = "aircraft";
    }

    public void createSubmarine() {

        // Fondo oceánico
        Rectangle water = new Rectangle(100, 250);
        water.setFill(Color.DODGERBLUE);
        water.setArcHeight(5);
        water.setArcWidth(5);
        water.setStroke(Color.BLACK);

        // Estructura del submarino (camuflaje selvático)
        Rectangle baseBody = new Rectangle(75, 180);
        baseBody.setLayoutX(12);
        baseBody.setLayoutY(45);
        baseBody.setFill(Color.web("#5a4b1d")); // Marrón oscuro
        baseBody.setStroke(Color.BLACK);
        baseBody.setArcHeight(5);
        baseBody.setArcWidth(5);

        Rectangle camuflage = new Rectangle(55, 150);
        camuflage.setLayoutX(22);
        camuflage.setLayoutY(60);
        camuflage.setFill(Color.web("#417d22")); // Verde selva
        camuflage.setStroke(Color.BLACK);
        camuflage.setArcHeight(5);
        camuflage.setArcWidth(5);

        // Torre del submarino
        Polygon tower = new Polygon(
                0.0, 0.0,
                0.0, -25.0,
                20.0, -25.0,
                20.0, 0.0
        );
        tower.setLayoutX(40);
        tower.setLayoutY(60);
        tower.setFill(Color.web("#4a3d15"));
        tower.setStroke(Color.BLACK);

        // Detalle en forma de hélice
        Polygon helix = new Polygon(
                0.0, 0.0,
                7.0, -10.0,
                -7.0, -10.0
        );
        helix.setLayoutX(50);
        helix.setLayoutY(220);
        helix.setFill(Color.web("#2f2b12"));
        helix.setStroke(Color.BLACK);

        shape.getChildren().addAll(water, baseBody, camuflage, tower, helix);

        type = "submarine";
    }

    public void createDestructor(){
        // Fondo azul del mar
        Rectangle sea = new Rectangle(100, 170);
        sea.setFill(Color.DODGERBLUE);
        sea.setArcHeight(5);
        sea.setArcWidth(5);
        sea.setStroke(Color.BLACK);

        // Cuerpo base del destructor (madera marrón claro)
        Rectangle base = new Rectangle(70, 120);
        base.setLayoutX(15);
        base.setLayoutY(30);
        base.setFill(Color.web("#8c691b")); // Marrón claro selvático
        base.setStroke(Color.BLACK);
        base.setArcWidth(5);
        base.setArcHeight(5);

        // Camuflaje verde encima del cuerpo
        Rectangle camuflage = new Rectangle(50, 90);
        camuflage.setLayoutX(25);
        camuflage.setLayoutY(40);
        camuflage.setFill(Color.web("#5d9e1c")); // Verde intenso
        camuflage.setStroke(Color.BLACK);
        camuflage.setArcWidth(5);
        camuflage.setArcHeight(5);

        // Torre vigía (detalle)
        Polygon tower = new Polygon(
                0.0, 0.0,
                0.0, -20.0,
                15.0, -20.0,
                15.0, 0.0
        );
        tower.setLayoutX(42.0);
        tower.setLayoutY(40.0);
        tower.setFill(Color.web("#514b10"));
        tower.setStroke(Color.BLACK);

        // Detalle: cañón frontal
        Polygon canon = new Polygon(
                0.0, 0.0,
                4.0, -15.0,
                -4.0, -15.0
        );
        canon.setLayoutX(50.0);
        canon.setLayoutY(125.0);
        canon.setFill(Color.web("#3c3610"));
        canon.setStroke(Color.BLACK);

        shape.getChildren().addAll(sea, base, camuflage, tower, canon);
        type = "destructor";
    }

    public void createFrigate(){
        // Fondo azul que representa el mar
        Rectangle sea = new Rectangle(100, 85);
        sea.setFill(Color.DODGERBLUE);
        sea.setArcHeight(5);
        sea.setArcWidth(5);
        sea.setStroke(Color.BLACK);

        // Cuerpo base de la fragata
        Rectangle base = new Rectangle(65, 50);
        base.setLayoutX(17.5);
        base.setLayoutY(20);
        base.setFill(Color.web("#8c691b")); // Marrón madera
        base.setStroke(Color.BLACK);
        base.setArcWidth(5);
        base.setArcHeight(5);

        // Camuflaje verde encima del cuerpo
        Rectangle camuflage = new Rectangle(45, 35);
        camuflage.setLayoutX(27.5);
        camuflage.setLayoutY(25);
        camuflage.setFill(Color.web("#7dac3d")); // Verde selvático más suave
        camuflage.setStroke(Color.BLACK);
        camuflage.setArcWidth(5);
        camuflage.setArcHeight(5);

        // Detalle: radar o torre simple
        Polygon tower = new Polygon(
                0.0, 0.0,
                -4.0, -15.0,
                4.0, -15.0
        );
        tower.setLayoutX(50.0);
        tower.setLayoutY(25.0);
        tower.setFill(Color.web("#514b10"));
        tower.setStroke(Color.BLACK);

        shape.getChildren().addAll(sea, base, camuflage, tower);
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