package com.softvision.jattack.manager;

import com.softvision.jattack.elements.Element;

import java.util.List;

public interface ElementManager {

    void decrementLife(Element element);

    boolean isAlive(Element element);

    void removeElement(Element element);

    void drawElement(Element element);

    void drawElementBullets(Element element);

    void addElement(Element element);

    List<Element> getElements();

    void move(Element element);

    void emptySpace(Element element);

    void cleanBullets(Element element);
}
