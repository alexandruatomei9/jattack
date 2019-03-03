package com.softvision.jattack.util;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.RandomCoordinates;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Util {

    private static final Object lockObject = new Object();
    private static int tick = 1000;
    private static final Random random = new Random();

    public static Object lockOn() {
        return lockObject;
    }

    public static int getTick() {
        return tick;
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static Coordinates computeCoordinates() {
        Coordinates coordinates;
        do {
            coordinates = new RandomCoordinates(Constants.WIDTH, Constants.HEIGHT);
        } while (coordinatesOverlapAnotherImage(coordinates, CoordinatesCache.getInstance().getCoordinatesInUse()));

        CoordinatesCache.getInstance().getCoordinatesInUse().add(coordinates);

        return coordinates;
    }

    public static boolean coordinatesOverlapAnotherImage(Coordinates coordinates, List<Coordinates> coordinatesInUse) {
        Optional<Coordinates> existingImageAtTheSpecifiedCoordinates = coordinatesInUse.stream().filter(c -> {
            int xAxisDifference = c.getX() - coordinates.getX();
            int yAxisDifference = c.getY() - coordinates.getY();

            return !((xAxisDifference == 0 && Math.abs(yAxisDifference) >= 100) ||
                    (yAxisDifference == 0 && Math.abs(xAxisDifference) >= 100) ||
                    (xAxisDifference >= 100 && Math.abs(yAxisDifference) >= 100) ||
                    (yAxisDifference >= 100 && Math.abs(xAxisDifference) >= 100) ||
                    (Math.abs(xAxisDifference) >= 100 && Math.abs(yAxisDifference) >= 100));
        }).findFirst();

        return existingImageAtTheSpecifiedCoordinates.isPresent();
    }

    public static boolean coordinatesAreWithinBounds(Coordinates coordinates) {
        return coordinates.getX() >= 50 && coordinates.getX() <= Constants.WIDTH - 150
                && coordinates.getY() >= 50 && coordinates.getY() <= Constants.HEIGHT - 150;
    }
}
