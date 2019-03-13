package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;
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

    @Override
    public void draw(GraphicsContext graphicsContext) {
        this.getCoordinates().setY(this.getCoordinates().getY() + this.getVelocity());
        graphicsContext.setFill(this.getColor());
        graphicsContext.fillOval(this.getCoordinates().getX(), this.getCoordinates().getY(), this.getBulletDiameter(), this.getBulletDiameter());
    }

    public int getBulletDiameter() {
        return BULLET_DIAMETER;
    }
}
