package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.manager.GameManager;
import com.softvision.jattack.util.Util;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Element implements Runnable {
    private int life;
    protected List<Coordinates> bulletsCoordinates;
    private Coordinates coordinates;
    private Coordinates previousPosition;
    protected GameManager gameManager;

    public Element(Coordinates coordinates, GameManager gameManager, int life) {
        this.coordinates = coordinates;
        this.life = life;
        this.bulletsCoordinates = new ArrayList<>();
        this.gameManager = gameManager;
    }

    public void decrementLife() {
        this.life--;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setPreviousPosition(Coordinates previousPosition) {
        this.previousPosition = previousPosition;
    }

    public abstract Image getImage();

    public abstract int getBulletHeight();

    public abstract int getBulletWidth();

    public abstract int getBulletVelocity();

    public abstract Color getBulletColor();

    public void addBulletCoordinates(Coordinates bulletCoordinates) {
        this.bulletsCoordinates.add(bulletCoordinates);
        CoordinatesCache.getInstance().getEnemyBulletsCoordinates().add(bulletCoordinates);
    }

    public List<Coordinates> getBulletsCoordinates() {
        return bulletsCoordinates;
    }

    public Coordinates getPreviousPosition() {
        return previousPosition;
    }

    public abstract void shoot();

    public void run() {
        while (!gameManager.gameEnded() && gameManager.isAlive(this)) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (Util.lockOn()) {
                if (gameManager.wasHit(this)) {
                    gameManager.decrementLife(this);
                }

                //either move or shoot
                gameManager.choseAndExecuteAction(this);
            }
        }

        gameManager.clearElement(this);
        if(gameManager.getInvadersNumber() == 0) {
            gameManager.setGameEnded(true);
        }
    }
}
