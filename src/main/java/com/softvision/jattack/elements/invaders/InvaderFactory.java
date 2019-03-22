package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.elements.Element;
import com.softvision.jattack.images.ImageType;
import com.softvision.jattack.manager.GameManager;
import com.softvision.jattack.util.Util;

public class InvaderFactory {

    public static Element generateElement(ImageType imageType, GameManager gameManager) {
        switch (imageType) {
            case TANK:
                return new Tank(Util.computeCoordinates(), gameManager);
            case PLANE:
                return new Plane(Util.computeCoordinates(), gameManager);
            case HELICOPTER:
                return new Helicopter(Util.computeCoordinates(), gameManager);
            default:
                throw new IllegalArgumentException();
        }
    }
}
