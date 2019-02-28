package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.images.ImageLoader;
import javafx.scene.image.Image;

public class Plane extends Element {
    private final Image image = ImageLoader.getImage(ElementType.PLANE);

    public Plane() {
        super();
    }

    public Plane(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Image getImage() {
        return image;
    }
}
