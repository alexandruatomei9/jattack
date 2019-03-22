package com.softvision.jattack.elements.defender;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.images.ImageType;
import com.softvision.jattack.manager.GameManager;
import com.softvision.jattack.util.Constants;
import com.softvision.jattack.util.Util;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.List;

public class Defender extends Element {

    private final Image image = ImageLoader.getImage(ImageType.DEFENDER);
    private EventHandler<KeyEvent> eventHandler;

    public Defender(Coordinates coordinates, GameManager gameManager) {
        super(coordinates, gameManager, 5);
        this.eventHandler = new DefenderEventHandler(this);
        gameManager.draw(this);
    }

    public void shoot() {
        //the x coordinate of the bullet is computed based on the width of the image for the defender and also the bullet width
        Coordinates bulletCoordinates = new FixedCoordinates(getCoordinates().getX() + 45, getCoordinates().getY() - 30);
        bulletsCoordinates.add(bulletCoordinates);
        CoordinatesCache.getInstance().getDefenderBulletsCoordinates().add(bulletCoordinates);
    }

    void move(KeyCode keyCode) {
        setPreviousPosition(getCoordinates());
        if (keyCode.equals(KeyCode.LEFT) && this.getCoordinates().getX() - 10 > 0) {
            this.getCoordinates().setX(this.getCoordinates().getX() - 10);
        } else if (keyCode.equals(KeyCode.RIGHT) && this.getCoordinates().getX() + 10 < Constants.WIDTH - 100) {
            this.getCoordinates().setX(this.getCoordinates().getX() + 10);
        }
        gameManager.draw(this);
    }

    public Image getImage() {
        return image;
    }

    @Override
    public int getBulletHeight() {
        return 10;
    }

    @Override
    public int getBulletWidth() {
        return 7;
    }

    @Override
    public int getBulletVelocity() {
        return -80;
    }

    @Override
    public Color getBulletColor() {
        return Color.AQUAMARINE;
    }

    public EventHandler<KeyEvent> getEventHandler() {
        return eventHandler;
    }

    public List<Coordinates> getBulletsCoordinates() {
        return bulletsCoordinates;
    }

    @Override
    public void run() {
        while (!gameManager.gameEnded()) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (Util.lockOn()) {
                if (gameManager.wasHit(this)) {
                    gameManager.decrementLife(this);
                    if (!gameManager.isAlive(this)) {
                        gameManager.setGameEnded(true);
                    }
                }

                gameManager.drawElementBullets(this);
            }
        }
    }
}
