/**
 * @author Michael Quick
 * @version v1.0.0
 * The main class of Rogue Trails. Handles the screens and managers of the game.
 */

package com.rgd.roguetrails;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.rgd.roguetrails.ecs.ECSEngine;
import com.rgd.roguetrails.screens.MainMenuScreen;

/**
 * {@link com.badlogic.gdx.Game} implementation shared by all platforms.
 */
public class RogueTrails extends Game {
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private GameAssetManager assetManager;
    private OrthographicCamera camera;
    private World world;
    private ECSEngine ecsEngine;

    @Override
    public void create() {

        // Initialize sprite batch
        batch = new SpriteBatch();
        viewport = new ExtendViewport(426, 240);

        camera = new OrthographicCamera();
        // Initialize managers
        assetManager = new GameAssetManager();

        world = new World(Vector2.Zero, true);
        ecsEngine = new ECSEngine(batch, camera, world, this);

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
        world.dispose();
        ecsEngine.removeAllEntities();
        ecsEngine.clearPools();
    }

    /**
     * Allows all screens to use the same SpriteBatch
     *
     * @return the SpriteBatch used in the entire game
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Allows all screens to use the same viewport
     *
     * @return the fit viewport used in the entire game
     */
    public ExtendViewport getViewport() {
        return viewport;
    }

    /**
     * Provides the asset manager
     *
     * @return the asset manager used in the entire game
     */
    public GameAssetManager getAssetManager() {
        return assetManager;
    }

    public ECSEngine getEcsEngine() {
        return ecsEngine;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }
}
