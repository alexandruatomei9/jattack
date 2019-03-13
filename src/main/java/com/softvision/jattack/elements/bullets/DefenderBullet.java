package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.util.Constants;
import com.softvision.jattack.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

public class DefenderBullet extends Bullet {
    private static final int HEIGHT = 20;
    private static final int WIDTH = 5;

    public DefenderBullet(Coordinates coordinates, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(coordinates, Color.AQUAMARINE, -100, gameEnded, graphicsContext);
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        //clear old position
        graphicsContext.setFill(Color.ORANGE);
        graphicsContext.fillRect(this.getCoordinates().getX(), this.getCoordinates().getY(), this.getWidth(), this.getHeight());

        //draw bullet at new position
        this.getCoordinates().setY(this.getCoordinates().getY() + this.getVelocity());
        graphicsContext.setFill(this.getColor());
        graphicsContext.fillRect(this.getCoordinates().getX(), this.getCoordinates().getY(), this.getWidth(), this.getHeight());
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    @Override
    public void run() {
        boolean isBulletInBounds = true;
        while (isBulletInBounds) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (Util.lockOn()) {
                this.draw(graphicsContext);
                if(coordinates.getY() <= 0) {
                    //if the enemy bullet is out of bounds we exit the loop
                    isBulletInBounds = false;
                }
            }
        }
    }
}
