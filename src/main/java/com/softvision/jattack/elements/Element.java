package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.canvas.GraphicsContext;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Element implements Runnable {
    protected AtomicBoolean gameEnded;

    public Element(AtomicBoolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    protected void emptySpace(Coordinates coordinates, GraphicsContext graphicsContext, double width, double height) {
        graphicsContext.clearRect(coordinates.getX(),
                coordinates.getY(),
                width,
                height);
    }
}
