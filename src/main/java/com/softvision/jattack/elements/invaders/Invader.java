package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.Direction;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.elements.bullets.Bullet;
import com.softvision.jattack.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Invader extends Element {

    private Coordinates coordinates;
    private int life;
    private GraphicsContext graphicsContext;

    Invader(Coordinates coordinates, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(gameEnded);
        this.coordinates = coordinates;
        this.life = 3;
        this.graphicsContext = graphicsContext;
    }

    public abstract Image getImage();

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean wasHit() {
        boolean wasHit = false;

        Iterator<Bullet> defenderBulletIterator = CoordinatesCache.getInstance().getDefenderBullets().iterator();
        while (defenderBulletIterator.hasNext()) {
            Bullet bullet = defenderBulletIterator.next();
            int bulletX = bullet.getCoordinates().getX();
            int bulletY = bullet.getCoordinates().getY();
            if(this.coordinates.getY() < 0) {
                defenderBulletIterator.remove();
            } else if (this.coordinates.getX() <= bulletX && bulletX <= this.coordinates.getX() + 110) {
                if (this.getCoordinates().getY() + 100 >= bulletY) {
                    defenderBulletIterator.remove();
                    wasHit = true;
                    break;
                }
            }
        }

        return wasHit;
    }

    public void decrementLife() {
        life--;
    }

    public boolean isDead() {
        return life <= 0;
    }

    public abstract void shoot(GraphicsContext graphicsContext);

    void draw() {
        graphicsContext.drawImage(this.getImage(),
                this.getCoordinates().getX(),
                this.getCoordinates().getY(),
                this.getImage().getWidth(),
                this.getImage().getHeight());
    }

    public void move() {
        Direction randomDirection = Util.randomEnum(Direction.class);
        FixedCoordinates fixedCoordinates = new FixedCoordinates(this.coordinates.getX() + randomDirection.getX(), this.coordinates.getY() + randomDirection.getY());
        if (canMoveToDirection(fixedCoordinates)) {
            CoordinatesCache.getInstance().getCoordinatesInUse().remove(this.coordinates);
            emptySpace(this.coordinates, this.graphicsContext, getImage().getWidth(), getImage().getHeight());
            this.coordinates = fixedCoordinates;
            CoordinatesCache.getInstance().getCoordinatesInUse().add(this.coordinates);
        }
    }

    private boolean canMoveToDirection(Coordinates coordinates) {
        List<Coordinates> otherCoordinatesInUse = new ArrayList<>(CoordinatesCache.getInstance().getCoordinatesInUse());
        otherCoordinatesInUse.remove(this.coordinates);
        return Util.coordinatesAreWithinBounds(coordinates) && !Util.coordinatesOverlapAnotherImage(coordinates, otherCoordinatesInUse);
    }

    public void run() {
        boolean invaderIsAlive = true;
        while (!gameEnded.get() && invaderIsAlive) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (Util.lockOn()) {
                if (this.isDead()) {
                    emptySpace(this.coordinates, this.graphicsContext, getImage().getWidth(), getImage().getHeight());
                    CoordinatesCache.getInstance().getCoordinatesInUse().remove(coordinates);
                    if(CoordinatesCache.getInstance().getCoordinatesInUse().size() == 0) {
                        gameEnded.set(true);
                    }
                    invaderIsAlive = false;
                } else {
                    if (this.wasHit()) {
                        this.decrementLife();
                    } else {
                        //either move or shoot
                        boolean shouldShoot = Util.randomBoolean();
                        if (shouldShoot) {
                            this.shoot(graphicsContext);
                        } else {
                            this.move();
                            this.draw();
                        }
                    }
                }
            }
        }
    }
}
