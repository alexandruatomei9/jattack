package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.bullets.Bullet;
import com.softvision.jattack.elements.bullets.PlaneBullet;
import com.softvision.jattack.images.ImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Plane extends Invader {
    private final Image image = ImageLoader.getImage(InvaderType.PLANE);

    public Plane(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Image getImage() {
        return image;
    }

    public void shoot(GraphicsContext graphicsContext) {
        Bullet bullet = new PlaneBullet(new FixedCoordinates(getCoordinates().getX() + 50, getCoordinates().getY() + 100));
        graphicsContext.setFill(bullet.getColor());
        graphicsContext.fillRect(bullet.getCoordinates().getX(), bullet.getCoordinates().getY(), bullet.getBulletSize(), bullet.getBulletSize());
        CoordinatesCache.getInstance().getEnemyBullets().add(bullet);
    }
}
