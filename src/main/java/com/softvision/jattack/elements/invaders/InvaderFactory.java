package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.images.ImageType;
import com.softvision.jattack.util.Util;
import javafx.scene.canvas.GraphicsContext;

import java.util.concurrent.atomic.AtomicBoolean;

public class InvaderFactory {

    public static Invader generateElement(ImageType imageType, AtomicBoolean gameEnded, GraphicsContext graphicsContext) {
        switch (imageType) {
            case TANK:
                return new Tank(Util.computeCoordinates(), gameEnded, graphicsContext);
            case PLANE:
                return new Plane(Util.computeCoordinates(), gameEnded, graphicsContext);
            case HELICOPTER:
                return new Helicopter(Util.computeCoordinates(), gameEnded, graphicsContext);
            default:
                throw new IllegalArgumentException();
        }
    }
}
