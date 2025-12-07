package com.rgd.roguetrails.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rgd.roguetrails.RogueTrails;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2025/12/07
 * A screen class containing the objects required by all screens in the game
 */
public abstract class BaseScreen implements Screen {
    protected final RogueTrails game;
    protected final ExtendViewport viewport;
    protected final SpriteBatch batch;

    /**
     * Constructs a new base screen with the main game object provided
     *
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
        // Clear the screen to a blank, black screen
        ScreenUtils.clear(0, 0, 0, 1);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
