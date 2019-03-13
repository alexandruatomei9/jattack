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

    @Override
    public void draw(GraphicsContext graphicsContext) {
        //clear old position
        graphicsContext.setFill(Color.ORANGE);
        graphicsContext.fillOval(this.getCoordinates().getX() - 1, this.getCoordinates().getY() - 1, this.getWidth() + 2, this.getHeight() + 2); //for some reason there's a border left

        //draw bullet at new position
        this.getCoordinates().setY(this.getCoordinates().getY() + this.getVelocity());
        graphicsContext.setFill(this.getColor());
        graphicsContext.fillOval(this.getCoordinates().getX(), this.getCoordinates().getY(), this.getWidth(), this.getHeight());
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
}
