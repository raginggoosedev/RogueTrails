package com.raginggoose.roguetrails.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class MenuInputListener implements GameInputListener {
    private final Array<TextButton> buttons;
    int currentButton = 0;

    public MenuInputListener(Array<TextButton> buttons) {
        this.buttons = buttons;
    }

    @Override
    public void keyPressed(InputManager inputManager, GameKeys gameKey) {
        switch (gameKey) {
            case UP:
            case DOWN:
                unselectButton();

                selectButton(gameKey);
                break;
            case ENTER:
                pressButton();
        }
    }

    @Override
    public void keyUp(InputManager inputManager, GameKeys gameKey) {
        if (gameKey == GameKeys.ENTER)
            releaseButton();
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    private void pressButton() {
        InputEvent e = Pools.obtain(InputEvent.class);
        e.setType(InputEvent.Type.touchDown);
        e.setButton(Input.Buttons.LEFT);

        buttons.get(currentButton).fire(e);
        Pools.free(e);
    }

    private void releaseButton() {
        InputEvent e = Pools.obtain(InputEvent.class);
        e.setType(InputEvent.Type.touchUp);
        e.setButton(Input.Buttons.LEFT);

        buttons.get(currentButton).fire(e);
        Pools.free(e);
    }

    private void unselectButton() {
        InputEvent e = Pools.obtain(InputEvent.class);
        e.setType(InputEvent.Type.exit);
        buttons.get(currentButton).fire(e);
        Pools.free(e);
    }

    private void selectButton(GameKeys gameKey) {
        unselectButton();

        if (gameKey == GameKeys.DOWN)
            currentButton++;
        else currentButton--;

        currentButton = currentButton % buttons.size;
        if(currentButton < 0) currentButton = buttons.size - 1;

        InputEvent e = Pools.obtain(InputEvent.class);
        e.setType(InputEvent.Type.enter);
        buttons.get(currentButton).fire(e);
        Pools.free(e);
    }
}
