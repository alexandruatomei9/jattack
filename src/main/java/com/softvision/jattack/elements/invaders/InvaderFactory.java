package com.softvision.jattack.elements.invaders;

import com.softvision.jattack.util.Util;

public class InvaderFactory {

    public static Invader generateElement(ElementType elementType) {
        switch (elementType) {
            case TANK:
                return new Tank(Util.computeCoordinates());
            case PLANE:
                return new Plane(Util.computeCoordinates());
            case HELICOPTER:
                return new Helicopter(Util.computeCoordinates());
            default:
                throw new IllegalArgumentException();
        }
    }
}
