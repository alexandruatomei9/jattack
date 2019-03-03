package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.paint.Color;

public abstract class Bullet {

    private Coordinates coordinates;
    private Color color;
    private int velocity; //number of pixels that the bulled will be moved each second

    public Bullet(Coordinates coordinates, Color color, int velocity) {
        this.coordinates = coordinates;
        this.color = color;
        this.velocity = velocity;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Color getColor() {
        return color;
    }

    public int getVelocity() {
        return velocity;
    }

    public abstract BulletShape getShape();
}
