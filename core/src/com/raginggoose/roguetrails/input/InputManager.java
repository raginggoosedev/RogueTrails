package com.raginggoose.roguetrails.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;

/**
 * Handles input for the game
 */
public class InputManager implements InputProcessor {
    private final GameKeys[] keyMapping;
    private final boolean[] keyState;
    private final Array<GameInputListener> listeners;

    public InputManager() {
        // Map each key to the keyMapping array
        keyMapping = new GameKeys[256];
        for (GameKeys keys : GameKeys.values()) {
            for (int code : keys.getKeyCode()) {
                keyMapping[code] = keys;
            }
        }

        keyState = new boolean[GameKeys.values().length];
        listeners = new Array<>();
    }

    public void addInputListener(GameInputListener listener) {
        listeners.add(listener);
        Controllers.addListener(listener);
    }

    public void removeInputListener(GameInputListener listener) {
        listeners.removeValue(listener, true);
        Controllers.removeListener(listener);
    }

    @Override
    public boolean keyDown(int keycode) {
        GameKeys gameKey = keyMapping[keycode];
        if (gameKey == null) {
            // no mapping --> nothing to do
            return false;
        }

        notifyKeyDown(gameKey);

        return false;
    }

    private void notifyKeyDown(GameKeys gameKey) {
        keyState[gameKey.ordinal()] = true;
        for (GameInputListener listener : new Array.ArrayIterator<>(listeners)) {
            listener.keyPressed(this, gameKey);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        GameKeys gameKey = keyMapping[keycode];
        if (gameKey == null) {
            // no mapping --> nothing to do
            return false;
        }

        notifyKeyUp(gameKey);

        return false;
    }

    private void notifyKeyUp(GameKeys gameKey) {
        keyState[gameKey.ordinal()] = false;
        for (GameInputListener listener : new Array.ArrayIterator<>(listeners)) {
            listener.keyUp(this, gameKey);
        }
    }

    public boolean isKeyPressed(GameKeys gameKey) {
        return keyState[gameKey.ordinal()];
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
