package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.RandomCoordinates;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.util.Constants;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Optional;

public class ElementFactory {

    public static Element generateElement(ElementType elementType) {
        switch (elementType) {
            case TANK:
                return new Tank(computeCoordinates(elementType));
            case PLANE:
                return new Plane(computeCoordinates(elementType));
            case HELICOPTER:
                return new Helicopter(computeCoordinates(elementType));
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Coordinates computeCoordinates(ElementType elementType) {
        Coordinates coordinates;
        Image image = ImageLoader.getImage(elementType);
        do {
            coordinates = new RandomCoordinates(Constants.GRID_COLUMNS - (int) (image.getWidth() / Constants.ELEMENT_WIDTH), Constants.GRID_LINES - (int) (image.getHeight() / Constants.ELEMENT_HEIGHT));
        } while (coordinatesOverlapAnotherImage(coordinates, CoordinatesCache.getInstance().getCoordinatesInUse()));

        CoordinatesCache.getInstance().getCoordinatesInUse().add(coordinates);

        return coordinates;
    }

    private static boolean coordinatesOverlapAnotherImage(Coordinates coordinates, List<Coordinates> coordinatesInUse) {
        Optional<Coordinates> existingImageAtTheSpecifiedCoordinates = coordinatesInUse.stream().filter(c -> {
            return !((c.getX() - coordinates.getX() >= 3 || c.getX() - coordinates.getX() <= -3)
                    || (c.getY() - coordinates.getY() >= 3 || c.getY() - coordinates.getY() <= -3));
        }).findFirst();

        return existingImageAtTheSpecifiedCoordinates.isPresent();
    }
}
