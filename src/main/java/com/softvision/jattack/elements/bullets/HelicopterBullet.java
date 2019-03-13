package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HelicopterBullet extends Bullet {

    private static final String BULLET_SHAPE = "¤";
    public static final int BULLET_SIZE = 25;

    public HelicopterBullet(Coordinates coordinates) {
        super(coordinates, Color.BLACK, 50);
    }

    @Override
    public BulletShape getShape() {
        return BulletShape.CHAR;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        this.getCoordinates().setY(this.getCoordinates().getY() + this.getVelocity());
        graphicsContext.setFill(this.getColor());
        graphicsContext.setFont(new Font("Arial Bold", this.getBulletSize()));
        graphicsContext.fillText(this.getBulletShape(), this.getCoordinates().getX(), this.getCoordinates().getY());
    }

    public String getBulletShape() {
        return BULLET_SHAPE;
    }

    public int getBulletSize() {
        return BULLET_SIZE;
    }
}
