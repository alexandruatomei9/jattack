package com.softvision.jattack;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.FixedCoordinates;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestExample {

    public boolean coordinatesOverlapAnotherImage(Coordinates coordinates, List<Coordinates> coordinatesInUse) {
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

    @Test
    public void testCoordinates() {
        Coordinates fixedCoordinates = new FixedCoordinates(100, 100);
        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(fixedCoordinates);

        Assert.assertFalse(coordinatesOverlapAnotherImage(new FixedCoordinates(0, 0), coordinatesList));
        Assert.assertFalse(coordinatesOverlapAnotherImage(new FixedCoordinates(100, 0), coordinatesList));
        Assert.assertFalse(coordinatesOverlapAnotherImage(new FixedCoordinates(200, 0), coordinatesList));
        Assert.assertFalse(coordinatesOverlapAnotherImage(new FixedCoordinates(0, 100), coordinatesList));
        Assert.assertFalse(coordinatesOverlapAnotherImage(new FixedCoordinates(0, 200), coordinatesList));
        Assert.assertFalse(coordinatesOverlapAnotherImage(new FixedCoordinates(100, 200), coordinatesList));
        Assert.assertFalse(coordinatesOverlapAnotherImage(new FixedCoordinates(200, 100), coordinatesList));
        Assert.assertFalse(coordinatesOverlapAnotherImage(new FixedCoordinates(200, 200), coordinatesList));
        Assert.assertTrue(coordinatesOverlapAnotherImage(new FixedCoordinates(150, 200), coordinatesList));
        Assert.assertTrue(coordinatesOverlapAnotherImage(new FixedCoordinates(0, 50), coordinatesList));
    }
}
