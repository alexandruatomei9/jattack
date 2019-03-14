package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.concurrent.atomic.AtomicBoolean;

public class HelicopterBullet extends Bullet {
    private static final int BULLET_SIZE = 5;

    public HelicopterBullet(Coordinates coordinates, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(coordinates, Color.BLACK, 50, gameEnded, graphicsContext);
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
