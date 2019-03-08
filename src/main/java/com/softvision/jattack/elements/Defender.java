package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.bullets.Bullet;
import com.softvision.jattack.elements.bullets.DefenderBullet;
import com.softvision.jattack.elements.invaders.ImageType;
import com.softvision.jattack.images.ImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Iterator;

public class Defender {

    private final Image image = ImageLoader.getImage(ImageType.DEFENDER);
    private Coordinates coordinates;
    private int life;

    public Defender(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.life = 5;
    }

    public void shoot(GraphicsContext graphicsContext) {
        //the x coordinate of the bullet is computed based on the width of the image for the invader and also the bullet width
        DefenderBullet bullet = new DefenderBullet(new FixedCoordinates(coordinates.getX() + 45, coordinates.getY() - 30));
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
            if(this.coordinates.getX() <= bulletX && bulletX <= this.coordinates.getX() + 100) {
                if(this.getCoordinates().getY() - 10 <= bulletY) {
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
        if(keyCode.equals(KeyCode.LEFT)) {
            this.getCoordinates().setX(this.getCoordinates().getX() - 10);
        } else if(keyCode.equals(KeyCode.RIGHT)) {
            this.getCoordinates().setX(this.getCoordinates().getX() + 10);
        }
    }

    public Image getImage() {
        return image;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
