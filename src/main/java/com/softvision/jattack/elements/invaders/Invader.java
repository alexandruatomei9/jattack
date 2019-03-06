package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.Direction;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.bullets.Bullet;
import com.softvision.jattack.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Invader {

    private Coordinates coordinates;
    private int life;

    public Invader(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.life = 3;
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
            if(this.coordinates.getX() <= bulletX && bulletX <= this.coordinates.getX() + 110) {
                if(this.getCoordinates().getY() + 100 >= bulletY) {
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
        return life == 0;
    }

    public abstract void shoot(GraphicsContext graphicsContext);

    public void move() {
        Direction randomDirection = Util.randomEnum(Direction.class);
        FixedCoordinates fixedCoordinates = new FixedCoordinates(this.coordinates.getX() + randomDirection.getX(), this.coordinates.getY() + randomDirection.getY());
        if(canMoveToDirection(fixedCoordinates)) {
            CoordinatesCache.getInstance().getCoordinatesInUse().remove(this.coordinates);
            this.coordinates = fixedCoordinates;
            CoordinatesCache.getInstance().getCoordinatesInUse().add(this.coordinates);
        }
    }

    private boolean canMoveToDirection(Coordinates coordinates) {
        List<Coordinates> otherCoordinatesInUse = new ArrayList<>(CoordinatesCache.getInstance().getCoordinatesInUse());
        otherCoordinatesInUse.remove(this.coordinates);
        return Util.coordinatesAreWithinBounds(coordinates) && !Util.coordinatesOverlapAnotherImage(coordinates, otherCoordinatesInUse);
    }
}
