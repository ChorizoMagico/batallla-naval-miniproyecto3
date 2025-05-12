package com.example.batallanaval.model;

public class Boat {
    private final int size;
    private int resistance;
    private final boolean isHorizontal;

    public Boat(int size, int resistance, boolean isHorizontal) {
        this.size = size;
        this.resistance = resistance;
        this.isHorizontal = isHorizontal;
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

}