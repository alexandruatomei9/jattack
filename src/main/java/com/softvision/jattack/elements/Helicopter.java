package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.images.ImageLoader;
import javafx.scene.image.Image;

public class Helicopter extends Element {
    private final Image image = ImageLoader.getImage(ElementType.HELICOPTER);

    public Helicopter(Coordinates coordinates) {
        super(coordinates);
    }

    public Helicopter() {
        System.out.println("Helicopter default constructor was invoked");
    }

    @Override
    public Image getImage() {
        return image;
    }
}
