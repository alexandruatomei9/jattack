package com.softvision.jattack.elements.defender;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.elements.bullets.Bullet;
import com.softvision.jattack.elements.bullets.DefenderBullet;
import com.softvision.jattack.elements.invaders.ImageType;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.util.Constants;
import com.softvision.jattack.util.Util;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class Defender extends Element {

    private final Image image = ImageLoader.getImage(ImageType.DEFENDER);
    private Coordinates coordinates;
    private int life;
    private EventHandler<KeyEvent> eventHandler;
    private GraphicsContext graphicsContext;

    public Defender(Coordinates coordinates, GraphicsContext graphicsContext, AtomicBoolean gameEnded) {
        super(gameEnded);
        this.coordinates = coordinates;
        this.life = 5;
        this.graphicsContext = graphicsContext;
        this.eventHandler = new DefenderEventHandler(this);
        this.draw();
    }

    public void shoot(GraphicsContext graphicsContext) {
        //the x coordinate of the bullet is computed based on the width of the image for the defender and also the bullet width
        DefenderBullet bullet = new DefenderBullet(new FixedCoordinates(coordinates.getX() + 45, coordinates.getY() - 30), gameEnded, graphicsContext);
        graphicsContext.setFill(bullet.getColor());
        graphicsContext.fillRect(bullet.getCoordinates().getX(), bullet.getCoordinates().getY(), bullet.getWidth(), bullet.getHeight());
        CoordinatesCache.getInstance().getDefenderBullets().add(bullet);
    }

    public boolean wasHit() {
        boolean wasHit = false;

        Iterator<Bullet> invaderBulletsIterator = CoordinatesCache.getInstance().getEnemyBullets().iterator();
        while (invaderBulletsIterator.hasNext()) {
            Bullet bullet = invaderBulletsIterator.next();
            int bulletX = bullet.getCoordinates().getX();
            int bulletY = bullet.getCoordinates().getY();
            if (this.coordinates.getX() <= bulletX && bulletX <= this.coordinates.getX() + 100) {
                if (this.getCoordinates().getY() - 10 <= bulletY) {
                    invaderBulletsIterator.remove();
                    wasHit = true;
                    break;
                }
            }
        }

        return wasHit;
    }

    public void decreaseLife() {
        this.life--;
    }

    public boolean isDead() {
        return this.life <= 0;
    }

    public void move(KeyCode keyCode) {
        emptySpace(coordinates, graphicsContext, image.getWidth(), image.getHeight());
        if (keyCode.equals(KeyCode.LEFT) && this.getCoordinates().getX() - 10 > 0) {
            this.getCoordinates().setX(this.getCoordinates().getX() - 10);
        } else if (keyCode.equals(KeyCode.RIGHT) && this.getCoordinates().getX() + 10 < Constants.WIDTH - 100) {
            this.getCoordinates().setX(this.getCoordinates().getX() + 10);
        }
        draw();
    }

    public Image getImage() {
        return image;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public EventHandler<KeyEvent> getEventHandler() {
        return eventHandler;
    }

    @Override
    public void run() {
        while (!gameEnded.get()) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (Util.lockOn()) {
                if (this.wasHit()) {
                    this.decreaseLife();
                    if(this.isDead()) {
                        gameEnded.set(true);
                    }
                }
            }
        }
    }

    public void draw() {
        graphicsContext.drawImage(this.getImage(),
                this.getCoordinates().getX(),
                this.getCoordinates().getY(),
                this.getImage().getWidth(),
                this.getImage().getHeight());
    }
}
