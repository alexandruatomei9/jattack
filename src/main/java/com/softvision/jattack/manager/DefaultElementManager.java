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
    private GraphicsContext graphicsContext;

    public DefaultElementManager(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
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
    public void drawElement(Element element) {
        emptySpace(element);
        graphicsContext.drawImage(element.getImage(),
                element.getCoordinates().getX(),
                element.getCoordinates().getY(),
                element.getImage().getWidth(),
                element.getImage().getHeight());
    }

    @Override
    public void drawElementBullets(Element element){
        element.getBulletsCoordinates().forEach(c ->  {
            graphicsContext.clearRect(c.getX(), c.getY(), element.getBulletWidth(), element.getBulletHeight());
            graphicsContext.setFill(element.getBulletColor());
            c.setY(c.getY() + element.getBulletVelocity());
            graphicsContext.fillRect(c.getX(), c.getY(), element.getBulletWidth(), element.getBulletHeight());
        });
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

    @Override
    public void emptySpace(Element element) {
        if(element.getPreviousPosition() != null) {
            graphicsContext.clearRect(element.getPreviousPosition().getX(),
                    element.getPreviousPosition().getY(),
                    element.getImage().getWidth(),
                    element.getImage().getHeight());
        }
    }

    @Override
    public void cleanBullets(Element element) {
        element.getBulletsCoordinates().forEach(b -> graphicsContext.clearRect(b.getX(), b.getY(), element.getBulletWidth(), element.getBulletHeight()));
    }

    private boolean canMoveToDirection(Element element, Coordinates newCoordinates) {
        List<Coordinates> otherCoordinatesInUse = new ArrayList<>(CoordinatesCache.getInstance().getCoordinatesInUse());
        otherCoordinatesInUse.remove(element.getCoordinates());
        return Util.coordinatesAreWithinBounds(newCoordinates) && !Util.coordinatesOverlapAnotherImage(newCoordinates, otherCoordinatesInUse);
    }
}
