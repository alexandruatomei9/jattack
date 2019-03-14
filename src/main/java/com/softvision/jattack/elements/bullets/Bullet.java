package com.softvision.jattack.elements.bullets;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.util.Constants;
import com.softvision.jattack.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Bullet extends Element {

    Coordinates coordinates;
    private Color color;
    private int velocity; //number of pixels that the bulled will be moved each second
    GraphicsContext graphicsContext;
    private AtomicBoolean isBulletInBounds = new AtomicBoolean(true);

    Bullet(Coordinates coordinates, Color color, int velocity, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(gameEnded);
        this.coordinates = coordinates;
        this.color = color;
        this.velocity = velocity;
        this.graphicsContext = graphicsContext;
        Thread bulletThread = new Thread(this);
        bulletThread.start();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Color getColor() {
        return color;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public void draw(GraphicsContext graphicsContext) {
        //clear old position
        graphicsContext.clearRect(coordinates.getX(), coordinates.getY(), getWidth(), getHeight());

        //draw bullet at new position
        this.getCoordinates().setY(coordinates.getY() + velocity);
        graphicsContext.setFill(color);
        graphicsContext.fillRect(coordinates.getX(), coordinates.getY(), getWidth(), getHeight());
    }

    public void run() {
        while (!gameEnded.get() && isBulletInBounds.get()) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (Util.lockOn()) {
                this.draw(graphicsContext);
                if(coordinates.getY() >= Constants.HEIGHT) {
                    //if the enemy bullet is out of bounds we should remove it
                    CoordinatesCache.getInstance().getEnemyBullets().remove(this);
                    isBulletInBounds.set(false);
                }
            }
        }
    }

    public AtomicBoolean isBulletInBounds() {
        return isBulletInBounds;
    }
}
