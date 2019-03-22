package com.softvision.jattack.manager;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.elements.defender.Defender;
import com.softvision.jattack.util.Util;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultGameManager implements GameManager {

    private ElementManager elementsManager;
    private AtomicBoolean gameEnded;

    public DefaultGameManager(ElementManager elementsManager) {
        this.elementsManager = elementsManager;
        this.gameEnded = new AtomicBoolean(false);
    }

    public void choseAndExecuteAction(Element element) {
        boolean shouldShoot = Util.randomBoolean();
        if (shouldShoot) {
            element.shoot();
        } else {
            elementsManager.move(element);
        }

        elementsManager.drawElement(element);
        elementsManager.drawElementBullets(element);
    }

    public boolean isAlive(Element element) {
        if (!elementsManager.isAlive(element)) {
            elementsManager.removeElement(element);
        }

        return elementsManager.isAlive(element);
    }

    public void decrementLife(Element element) {
        elementsManager.decrementLife(element);
        if(!isAlive(element)) {
            elementsManager.removeElement(element);
        }
    }

    public boolean wasHit(Element element) {
        boolean wasHit = false;

        if(element instanceof Defender) {
            Iterator<Coordinates> invaderBulletsCoordinatesIterator = CoordinatesCache.getInstance().getEnemyBulletsCoordinates().iterator();
            while (invaderBulletsCoordinatesIterator.hasNext()) {
                Coordinates bulletCoordinates = invaderBulletsCoordinatesIterator.next();
                int bulletX = bulletCoordinates.getX();
                int bulletY = bulletCoordinates.getY();
                if (element.getCoordinates().getX() <= bulletX && bulletX <= element.getCoordinates().getX() + 100) {
                    if (element.getCoordinates().getY() - 10 <= bulletY) {
                        invaderBulletsCoordinatesIterator.remove();
                        wasHit = true;
                        break;
                    }
                }
            }
        } else {
            Iterator<Coordinates> defenderBulletCoordinatesIterator = CoordinatesCache.getInstance().getDefenderBulletsCoordinates().iterator();
            while (defenderBulletCoordinatesIterator.hasNext()) {
                Coordinates bulletCoordinates = defenderBulletCoordinatesIterator.next();
                int bulletX = bulletCoordinates.getX();
                int bulletY = bulletCoordinates.getY();
                if (element.getCoordinates().getY() < 0) {
                    defenderBulletCoordinatesIterator.remove();
                } else if (element.getCoordinates().getX() <= bulletX && bulletX <= element.getCoordinates().getX() + 110) {
                    if (element.getCoordinates().getY() + 100 >= bulletY) {
                        defenderBulletCoordinatesIterator.remove();
                        wasHit = true;
                        break;
                    }
                }
            }
        }
        return wasHit;
    }

    public void draw(Element element) {
        elementsManager.drawElement(element);
    }

    public void drawElementBullets(Element element) {
        elementsManager.drawElementBullets(element);
    }

    public boolean gameEnded() {
        return gameEnded.get();
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded.set(gameEnded);
    }

    public void clearElement(Element element) {
        element.setPreviousPosition(element.getCoordinates());
        elementsManager.emptySpace(element);
        elementsManager.cleanBullets(element);
        elementsManager.removeElement(element);
    }

    public int getInvadersNumber() {
        return elementsManager.getElements().size();
    }
}
