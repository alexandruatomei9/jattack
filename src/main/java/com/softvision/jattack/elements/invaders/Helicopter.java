package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.bullets.HelicopterBullet;
import com.softvision.jattack.images.ImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Helicopter extends Invader {
    private final Image image = ImageLoader.getImage(InvaderType.HELICOPTER);

    public Helicopter(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Image getImage() {
        return image;
    }

    public void shoot(GraphicsContext graphicsContext) {
        HelicopterBullet bullet = new HelicopterBullet(new FixedCoordinates(getCoordinates().getX() + 50, getCoordinates().getY() + 100));
        graphicsContext.setFill(bullet.getColor());
        graphicsContext.fillText(bullet.getBulletShape(), bullet.getCoordinates().getX(), bullet.getCoordinates().getY(), bullet.getBulletSize());
        CoordinatesCache.getInstance().getEnemyBullets().add(bullet);
    }
}
