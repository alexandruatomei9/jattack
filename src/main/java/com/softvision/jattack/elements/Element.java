package com.softvision.jattack.elements;

import com.softvision.jattack.coordinates.Coordinates;
import javafx.scene.image.Image;

public abstract class Element {

    private Coordinates coordinates;

    public Element(){
        System.out.println("Element default constructor was invoked");
    }

    public Element(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public abstract Image getImage();

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
