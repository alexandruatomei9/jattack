package com.softvision.jattack.coordinates;

public enum Direction {

    N(0,30),NE(30,30),E(30,0),SE(30,-30),S(0,-30),SW(-30,-30),W(-30,0),NW(-30,30);

    private int x;
    private int y;

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
