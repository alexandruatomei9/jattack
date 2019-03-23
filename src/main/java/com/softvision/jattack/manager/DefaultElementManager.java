package com.softvision.jattack.manager;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.Direction;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.util.Util;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class DefaultElementManager implements ElementManager {

    private List<Element> elements;

    public DefaultElementManager() {
        this.elements = new ArrayList<>();
    }

    @Override
    public void decrementLife(Element element) {
        element.decrementLife();
    }

    @Override
    public boolean isAlive(Element element) {
        return element.isAlive();
    }

    @Override
    public void removeElement(Element element){
        this.elements.remove(element);
    }

    @Override
    public void addElement(Element element) {
        this.elements.add(element);
    }

    @Override
    public List<Element> getElements() {
        return this.elements;
    }

    @Override
    public void move(Element element) {
        Direction randomDirection = Util.randomEnum(Direction.class);
        FixedCoordinates fixedCoordinates = new FixedCoordinates(element.getCoordinates().getX() + randomDirection.getX(), element.getCoordinates().getY() + randomDirection.getY());
        if (canMoveToDirection(element, fixedCoordinates)) {
            element.setPreviousPosition(element.getCoordinates());
            element.setCoordinates(fixedCoordinates);
        }
    }


    private boolean canMoveToDirection(Element element, Coordinates newCoordinates) {
        List<Coordinates> otherCoordinatesInUse = new ArrayList<>(CoordinatesCache.getInstance().getCoordinatesInUse());
        otherCoordinatesInUse.remove(element.getCoordinates());
        return Util.coordinatesAreWithinBounds(newCoordinates) && !Util.coordinatesOverlapAnotherImage(newCoordinates, otherCoordinatesInUse);
    }
}
