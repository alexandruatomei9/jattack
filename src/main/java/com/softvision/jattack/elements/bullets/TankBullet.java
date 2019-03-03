package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.paint.Color;

public class TankBullet extends Bullet {

    public TankBullet(Coordinates coordinates) {
        super(coordinates, Color.GREEN, 30, 10);
    }

    @Override
    public BulletShape getShape() {
        return BulletShape.OVAL;
    }
}
