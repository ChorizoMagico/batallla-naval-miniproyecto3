package com.example.batallanaval.controller;

public class ShipsNotPlacedException extends Exception {
    public ShipsNotPlacedException() {
        super("No se han colocado barcos.");
    }
}