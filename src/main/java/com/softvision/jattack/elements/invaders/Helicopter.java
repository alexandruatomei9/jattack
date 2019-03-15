package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.bullets.HelicopterBullet;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.images.ImageType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.util.concurrent.atomic.AtomicBoolean;

public class Helicopter extends Invader {
    private final Image image = ImageLoader.getImage(ImageType.HELICOPTER);

    Helicopter(Coordinates coordinates, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        super(coordinates, gameEnded, graphicsContext);
        this.draw();
    }

    @Override
    public Image getImage() {
        return image;
    }

    public void shoot(GraphicsContext graphicsContext) {
        //the x coordinate of the bullet is computed based on the width of the image for the invader and also the bullet width
        HelicopterBullet bullet = new HelicopterBullet(new FixedCoordinates(getCoordinates().getX() + 35, getCoordinates().getY() + 100), gameEnded, graphicsContext);
        bullet.draw(graphicsContext);
        CoordinatesCache.getInstance().getEnemyBullets().add(bullet);
    }
}
