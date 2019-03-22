package com.softvision.jattack.manager;

import com.softvision.jattack.elements.Element;

public interface GameManager {

    void choseAndExecuteAction(Element element);

    boolean isAlive(Element element);

    void decrementLife(Element element);

    boolean wasHit(Element element);

    void draw(Element element);

    void drawElementBullets(Element element);

    boolean gameEnded();

    void setGameEnded(boolean gameEnded);

    void clearElement(Element element);

    int getInvadersNumber();
}
