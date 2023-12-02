package com.raginggoose.roguetrails.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;

/**
 * Listens for input from the user
 */
public interface GameInputListener extends ControllerListener {
    public abstract void keyPressed(InputManager inputManager, GameKeys gameKey);

    public abstract void keyUp(InputManager inputManager, GameKeys gameKey);

    @Override
    public void connected(Controller controller);

    @Override
    public void disconnected(Controller controller);

    @Override
    public boolean buttonDown(Controller controller, int buttonCode);

    @Override
    public boolean buttonUp(Controller controller, int buttonCode);

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value);
}
