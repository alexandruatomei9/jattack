package com.softvision.jattack.coordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoordinatesCache {

    private List<Coordinates> coordinatesInUse;
    private List<Coordinates> enemyBulletsCoordinates;
    private List<Coordinates> defenderBulletsCoordinates;

    private CoordinatesCache() {
        coordinatesInUse = Collections.synchronizedList(new ArrayList<>());
        enemyBulletsCoordinates = Collections.synchronizedList(new ArrayList<>());
        defenderBulletsCoordinates = Collections.synchronizedList(new ArrayList<>());
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

    public List<Coordinates> getEnemyBulletsCoordinates() {
        return enemyBulletsCoordinates;
    }

    public List<Coordinates> getDefenderBulletsCoordinates() {
        return defenderBulletsCoordinates;
    }
}
