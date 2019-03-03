package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.bullets.TankBullet;
import com.softvision.jattack.images.ImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tank extends Invader {
    private final Image image = ImageLoader.getImage(InvaderType.TANK);

    public Tank(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Image getImage() {
        return image;
    }

    public void shoot(GraphicsContext graphicsContext) {
        //the x coordinate of the bullet is computed based on the width of the image for the invader and also the bullet width
        TankBullet bullet = new TankBullet(new FixedCoordinates(getCoordinates().getX() + 17, getCoordinates().getY() + 100));
        graphicsContext.setFill(bullet.getColor());
        graphicsContext.fillOval(bullet.getCoordinates().getX(), bullet.getCoordinates().getY(), bullet.getBulletDiameter(), bullet.getBulletDiameter());
        CoordinatesCache.getInstance().getEnemyBullets().add(bullet);
    }
}
