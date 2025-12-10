package com.rgd.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.rgd.roguetrails.RogueTrails;
import com.rgd.roguetrails.b2d.BodyFactory;
import com.rgd.roguetrails.utils.Constants;

import static com.rgd.roguetrails.utils.Constants.PPM;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2025/12/07
 * A screen which extends the BaseScreen to display the game
 */
public class GameScreen extends BaseScreen {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private final OrthographicCamera camera;


    /**
     * The constructor for the GameScreen
     *
     * @param game The Game object which runs the entire game
     */
    public GameScreen(RogueTrails game) {
        super(game);
        // Reset the viewport minimum world size
        viewport.setMinWorldWidth(16);
        viewport.setMinWorldHeight(9);

        // Camera and viewport setup
        camera = game.getCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
        this.viewport.setCamera(camera);
        viewport.apply(true);

        debugRenderer = new Box2DDebugRenderer();
        world = game.getWorld();

        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        // Test boxes
        bodyFactory.makeBox(Gdx.graphics.getWidth() / 2f, 16, Gdx.graphics.getWidth(), 32, BodyDef.BodyType.StaticBody);
        bodyFactory.makeBox(16, (Gdx.graphics.getHeight() - 32) / 2f + 32, 32, Gdx.graphics.getHeight() - 32, BodyDef.BodyType.StaticBody);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.viewport.apply(false);
        debugRenderer.render(world, camera.combined);
        world.step(1 / 60f, 6, 2);
        game.getEcsEngine().update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        super.dispose();
        debugRenderer.dispose();
    }
}
