package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.paint.Color;

public class HelicopterBullet extends Bullet {

    private static final String BULLET_SHAPE = "¤";

    public HelicopterBullet(Coordinates coordinates) {
        super(coordinates, Color.AZURE, 50, 25);
    }

    @Override
    public BulletShape getShape() {
        return BulletShape.CHAR;
    }

    public String getBulletShape() {
        return BULLET_SHAPE;
    }
}
