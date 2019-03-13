package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlaneBullet extends Bullet {

    private static final int HEIGHT = 15;
    private static final int WIDTH = 10;

    public PlaneBullet(Coordinates coordinates) {
        super(coordinates, Color.RED, 80);
    }

    @Override
    public BulletShape getShape() {
        return BulletShape.OVAL;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
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
