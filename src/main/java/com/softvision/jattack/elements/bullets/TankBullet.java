package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.paint.Color;

public class TankBullet extends Bullet {

    private static final int BULLET_DIAMETER = 10;

    public TankBullet(Coordinates coordinates) {
        super(coordinates, Color.GREEN, 30);
    }

    @Override
    public BulletShape getShape() {
        return BulletShape.CIRCLE;
    }

    public int getBulletDiameter() {
        return BULLET_DIAMETER;
    }
}
