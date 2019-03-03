package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.paint.Color;

public class PlaneBullet extends Bullet {

    private static final int HEIGHT = 15;
    private static final int WIDTH = 10;

    public PlaneBullet(Coordinates coordinates) {
        super(coordinates, Color.RED, 100);
    }

    @Override
    public BulletShape getShape() {
        return BulletShape.OVAL;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
}
