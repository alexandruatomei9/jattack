package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.bullets.PlaneBullet;
import com.softvision.jattack.elements.invaders.ElementType;
import com.softvision.jattack.images.ImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Defender {

    private final Image image = ImageLoader.getImage(ElementType.DEFENDER);
    private Coordinates coordinates;
    private int life;

    public Defender(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.life = 5;
    }

    public void shoot(GraphicsContext graphicsContext) {
        //the x coordinate of the bullet is computed based on the width of the image for the invader and also the bullet width
        PlaneBullet bullet = new PlaneBullet(new FixedCoordinates(coordinates.getX() + 45, coordinates.getY() - 100));
        graphicsContext.setFill(bullet.getColor());
        graphicsContext.fillOval(bullet.getCoordinates().getX(), bullet.getCoordinates().getY(), bullet.getWidth(), bullet.getHeight());
        CoordinatesCache.getInstance().getEnemyBullets().add(bullet);
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
