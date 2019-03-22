package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.images.ImageType;
import com.softvision.jattack.manager.GameManager;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Plane extends Element {
    private final Image image = ImageLoader.getImage(ImageType.PLANE);

    Plane(Coordinates coordinates, GameManager gameManager) {
        super(coordinates, gameManager, 3);
    }

    @Override
    public Image getImage() {
        return image;
    }

    public void shoot() {
        //the x coordinate of the bullet is computed based on the width of the image for the invader and also the bullet width
        addBulletCoordinates(new FixedCoordinates(getCoordinates().getX() + 45, getCoordinates().getY() + 100));
    }

    @Override
    public int getBulletHeight() {
        return 10;
    }

    @Override
    public int getBulletWidth() {
        return 5;
    }

    @Override
    public int getBulletVelocity() {
        return 80;
    }

    @Override
    public Color getBulletColor() {
        return Color.RED;
    }
}
