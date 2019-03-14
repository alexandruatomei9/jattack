package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

public class TankBullet extends Bullet {

    private static final int BULLET_SIZE = 10;

    public TankBullet(Coordinates coordinates, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(coordinates, Color.GREEN, 30, gameEnded, graphicsContext);
    }

    @Override
    public int getWidth() {
        return BULLET_SIZE;
    }

    @Override
    public int getHeight() {
        return BULLET_SIZE;
    }
}
