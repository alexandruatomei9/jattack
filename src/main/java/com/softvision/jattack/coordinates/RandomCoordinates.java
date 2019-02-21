package com.softvision.jattack.coordinates;

import java.util.Objects;
import java.util.Random;

public class RandomCoordinates implements Coordinates {

    private int x;
    private int y;

    public RandomCoordinates() {}

    public RandomCoordinates(int maxWidth, int maxHeight) {
        x = 1 + new Random().nextInt(maxWidth - 1);
        y = 1 + new Random().nextInt(maxHeight - 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Coordinates that = (Coordinates) o;
        return x == that.getX() &&
                y == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "RandomCoordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
