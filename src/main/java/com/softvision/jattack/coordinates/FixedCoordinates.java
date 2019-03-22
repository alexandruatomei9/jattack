package com.softvision.jattack.coordinates;

public class FixedCoordinates implements Coordinates {
    private int x;
    private int y;

    public FixedCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "FixedCoordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
