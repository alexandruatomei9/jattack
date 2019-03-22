package com.softvision.jattack.elements.defender;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DefenderEventHandler implements EventHandler<KeyEvent> {

    private Defender defender;

    public DefenderEventHandler(Defender defender) {
        this.defender = defender;
    }

    @Override
    public void handle(KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
            defender.move(e.getCode());
        }

        if (e.getCode() == KeyCode.SPACE) {
            defender.shoot();
        }
    }
}
