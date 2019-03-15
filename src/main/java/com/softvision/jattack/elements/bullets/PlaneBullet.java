package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlaneBullet extends Bullet {

    private static final int HEIGHT = 15;
    private static final int WIDTH = 10;

    public PlaneBullet(Coordinates coordinates, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(coordinates, Color.RED, 80, gameEnded, graphicsContext);
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
}
