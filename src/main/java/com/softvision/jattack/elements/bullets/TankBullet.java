package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

public class TankBullet extends Bullet {

    private static final int BULLET_DIAMETER = 10;

    public TankBullet(Coordinates coordinates, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(coordinates, Color.GREEN, 30, gameEnded, graphicsContext);
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        //clear old position
        graphicsContext.setFill(Color.ORANGE);
        graphicsContext.fillOval(this.getCoordinates().getX() - 1, this.getCoordinates().getY() - 1, BULLET_DIAMETER + 2, BULLET_DIAMETER + 2); //for some reason there's a border left

        //draw bullet at new position
        this.getCoordinates().setY(this.getCoordinates().getY() + this.getVelocity());
        graphicsContext.setFill(this.getColor());
        graphicsContext.fillOval(this.getCoordinates().getX(), this.getCoordinates().getY(), BULLET_DIAMETER, BULLET_DIAMETER);
    }
}
