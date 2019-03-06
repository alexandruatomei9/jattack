package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.paint.Color;

public class DefenderBullet extends Bullet {
    private static final int HEIGHT = 20;
    private static final int WIDTH = 5;


    public DefenderBullet(Coordinates coordinates) {
        super(coordinates, Color.AQUAMARINE, -100);
    }

    @Override
    public BulletShape getShape() {
        return BulletShape.RECTANGULAR;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
}
