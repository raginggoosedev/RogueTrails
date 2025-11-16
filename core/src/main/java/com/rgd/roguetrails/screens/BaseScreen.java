package com.rgd.roguetrails.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rgd.roguetrails.RogueTrails;

/**
 * A screen class containing the objects required by all screens in the game
 */
public abstract class BaseScreen implements Screen {
    protected final RogueTrails game;
    protected final FitViewport viewport;
    protected final SpriteBatch batch;

    /**
     * Constructs a new base screen with the main game object provided
     * @param game the main game object
     */
    public BaseScreen(RogueTrails game) {
        this.game = game;
        viewport = this.game.getViewport();
        batch = this.game.getBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
