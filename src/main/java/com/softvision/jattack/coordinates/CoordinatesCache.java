package com.softvision.jattack.coordinates;

import com.softvision.jattack.elements.bullets.Bullet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoordinatesCache {

    private List<Coordinates> coordinatesInUse;
    private List<Bullet> enemyBullets;
    private List<Bullet> defenderBullets;

    private CoordinatesCache() {
        coordinatesInUse = Collections.synchronizedList(new ArrayList<>());
        enemyBullets = Collections.synchronizedList(new ArrayList<>());
        defenderBullets = Collections.synchronizedList(new ArrayList<>());
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

    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public List<Bullet> getDefenderBullets() {
        return defenderBullets;
    }
}
