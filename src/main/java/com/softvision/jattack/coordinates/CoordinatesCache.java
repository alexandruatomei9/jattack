package com.softvision.jattack.coordinates;

import java.util.ArrayList;
import java.util.List;

public class CoordinatesCache {

    private List<Coordinates> coordinatesInUse;
    private CoordinatesCache(){
        coordinatesInUse = new ArrayList<>();
    }

    private static class SingletonHelper{
        private static final CoordinatesCache INSTANCE = new CoordinatesCache();
    }

    public static CoordinatesCache getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public List<Coordinates> getCoordinatesInUse() {
        return coordinatesInUse;
    }
}
