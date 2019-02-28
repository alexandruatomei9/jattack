package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.images.ImageLoader;
import javafx.scene.image.Image;

public class Tank extends Element{
    private final Image image = ImageLoader.getImage(ElementType.TANK);

    public Tank() {
        super();
    }

    public Tank(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Image getImage() {
        return image;
    }
}
