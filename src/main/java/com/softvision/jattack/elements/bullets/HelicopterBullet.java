package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.concurrent.atomic.AtomicBoolean;

public class HelicopterBullet extends Bullet {

    private static final String BULLET_SHAPE = "¤";
    private static final int BULLET_SIZE = 25;

    public HelicopterBullet(Coordinates coordinates, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(coordinates, Color.BLACK, 50, gameEnded, graphicsContext);
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        //clear old position
        graphicsContext.setFill(Color.ORANGE);
        graphicsContext.setFont(new Font("Arial Bold", BULLET_SIZE + 1));
        graphicsContext.fillText(BULLET_SHAPE, this.getCoordinates().getX(), this.getCoordinates().getY());

        //draw bullet at new position
        this.getCoordinates().setY(this.getCoordinates().getY() + this.getVelocity());
        graphicsContext.setFill(this.getColor());
        graphicsContext.setFont(new Font("Arial Bold", BULLET_SIZE));
        graphicsContext.fillText(BULLET_SHAPE, this.getCoordinates().getX(), this.getCoordinates().getY());
    }
}
