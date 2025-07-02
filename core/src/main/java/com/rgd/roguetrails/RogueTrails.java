/**
 * @author Michael Quick
 * @version v1.0.0
 * The main class of Rogue Trails. Handles the screens and managers of the game.
 */

package com.rgd.roguetrails;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.Game} implementation shared by all platforms.
 */
public class RogueTrails extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * Allows all screens to use the same SpriteBatch
     * @return the SpriteBatch used in the entire game
     */
    public SpriteBatch getBatch() {
        return batch;
    }
}
