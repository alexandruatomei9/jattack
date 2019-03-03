package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.paint.Color;

public class PlaneBullet extends Bullet {

    public PlaneBullet(Coordinates coordinates) {
        super(coordinates, Color.RED, 100, 15);
    }

    @Override
    public BulletShape getShape() {
        return BulletShape.RECTANGULAR;
    }
}
