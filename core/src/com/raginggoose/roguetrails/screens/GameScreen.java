package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.audio.AudioType;
import com.raginggoose.roguetrails.b2d.WorldContactListener;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.ECSEngine;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.ItemComponent;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.systems.PlayerMovementSystem;
import com.raginggoose.roguetrails.ecs.systems.RenderingSystem;
import com.raginggoose.roguetrails.hud.HUD;
import com.raginggoose.roguetrails.hud.Menu;
import com.raginggoose.roguetrails.inventory.Inventory;
import com.raginggoose.roguetrails.loader.AssetLoader;
import com.raginggoose.roguetrails.room.Cell;
import com.raginggoose.roguetrails.room.Hallway;
import com.raginggoose.roguetrails.room.Orientation;
import com.raginggoose.roguetrails.room.Room;

import static com.raginggoose.roguetrails.Constants.*;

public class GameScreen implements Screen {
    private final RogueTrails game;
    private final SpriteBatch batch;
    private final ECSEngine ecsEngine;
    private final AssetManager assetManager;
    private final World world;
    private final WorldContactListener worldContactListener;
    private final AssetLoader assetLoader;
    private final ShapeRenderer shape;
    private final OrthographicCamera cam;
    private final HUD hud;
    private final Skin skin;
    private final Stage stage;
    private final Inventory inventory;
    private final PlayerComponent playerComponent;
    private final Menu menu;
    private final Box2DDebugRenderer debugRenderer;
    private final ExtendViewport viewport;
    public Dungeon dun;
    private boolean paused;

    /**
     * Create a new game screen to display and play the game
     *
     * @param game the parent game class
     */
    public GameScreen(RogueTrails game) {
        Box2D.init();
        this.game = game;
        this.batch = game.getBatch();
        assetManager = game.getAssetManager().manager;
        assetLoader = game.getAssetManager();
        shape = new ShapeRenderer();

        if (game.getPreferences().isMusicEnabled())
            game.getAudioManager().playAudio(AudioType.BACKGROUND);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);

        viewport = new ExtendViewport(16, 9, cam);

        // Create new Box2D world with no gravity
        world = new World(Vector2.Zero, true);
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);


        debugRenderer = new Box2DDebugRenderer();
        ecsEngine = new ECSEngine(batch, cam, assetLoader, world, game);
        ecsEngine.createPlayer(32, 32, 32, 32, 0);

        dun = makeDungeon();

        spawnItems(dun.getStart(), 200, 100);

        PlayerMovementSystem playerMovementSystem = new PlayerMovementSystem();
        game.getInputManager().addInputListener(playerMovementSystem);
        ecsEngine.addSystem(playerMovementSystem);

        skin = new Skin(Gdx.files.internal("skin.json"));

        playerComponent = Mapper.PLAYER_MAPPER.get(ecsEngine.getPlayer());
        inventory = playerComponent.inventory;

        stage = new Stage(new ScreenViewport());


        hud = new HUD(inventory, skin, playerComponent.health, stage);
        menu = new Menu(stage, skin, game);

        paused = false;

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(game.getInputManager(), stage));
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));

        if (paused) {
            resume();
        }
    }

    public Dungeon makeDungeon() {
        Cell start = new Cell(10 * TILE_SIZE, 10 * TILE_SIZE, ecsEngine, world);
        Hallway hall1 = new Hallway(10 * TILE_SIZE, 2 * TILE_SIZE, Orientation.HORIZONTAL, world);
        Cell cellA = new Cell(10 * TILE_SIZE, 10 * TILE_SIZE, ecsEngine, world);
        Hallway hall2 = new Hallway(TILE_SIZE * 2, 10 * TILE_SIZE, Orientation.VERTICAL, world);
        Hallway hall3 = new Hallway(10 * TILE_SIZE, TILE_SIZE * 2, Orientation.HORIZONTAL, world);
        Cell cellB = new Cell(10 * TILE_SIZE, 10 * TILE_SIZE, ecsEngine, world);
        Cell cellD = new Cell(10 * TILE_SIZE, 100, ecsEngine, world);
        Hallway hall4 = new Hallway(10 * TILE_SIZE, TILE_SIZE * 2, Orientation.HORIZONTAL, world);
        Cell cellC = new Cell(20 * TILE_SIZE, 20 * TILE_SIZE, ecsEngine, world);
        Cell cellE = new Cell(TILE_SIZE * 2, TILE_SIZE * 2, ecsEngine, world);

        Dungeon dungeon = new Dungeon(start, null, world);

        start.setEast(hall1);
        hall1.setEast(cellA);
        cellA.setSouth(hall2);
        hall2.setSouth(cellE);
        cellE.setEast(hall3);
        hall3.setEast(cellC);
        cellE.setWest(hall4);
        hall4.setWest(cellB);
        cellB.setSouth(cellD);

        dungeon.createCollisionBoxes();

        return dungeon;

    }

    //temporary method to test spawning items
    //will rewrite more elegantly
    public void spawnItems(Room room, int x, int y) {
        int rx = room.getX();
        int ry = room.getY();
        ecsEngine.createItem(rx + 200, ry + 100, 32, 32, 1, ItemComponent.SHORT_SWORD);
        ecsEngine.createItem(rx + 250, ry + 250, 32, 32, 1, ItemComponent.BROAD_SWORD);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.75f, 0.5f, 1);
        viewport.apply(false);

        if (!paused) {
            if (Gdx.input.isKeyPressed(Input.Keys.TAB))
                dun.addEnemies();

            // Game is not paused, logic and rendering should be done
            updateGameLogic(delta);
            drawGame();

        } else {
            // Game is paused, only rendering should be done
            drawPausedGame(delta);
        }


        processInput();

        // Stage is drawn regardless
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
        stage.getViewport().update(width, height, true);
        hud.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
        menu.show();
    }

    @Override
    public void resume() {
        paused = false;
        menu.hide();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shape.dispose();
        stage.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

    private void processInput() {
        // If escape is pressed, the game is paused
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !paused) {
            // Wait until the menu is closed before player can open it
            if (!menu.isClosing())
                pause();
        } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && paused) {
            // Wait until the menu is opened before player can close it
            if (!menu.isOpening())
                resume();
        }
    }

    private void updateGameLogic(float delta) {
        // Update entities and the physics world
        ecsEngine.update(delta);
        world.step(TIME_STEP, 6, 2);

        hud.updateInventory(inventory);
        hud.updateHealth(playerComponent.health);
    }

    private void drawGame() {
        // Draw a small rectangle
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        assetLoader.queueAssets();

        dun.draw(shape);

        shape.end();

        // Set up projection matrix for rendering system
        batch.setProjectionMatrix(cam.combined);

        if (RogueTrails.DEBUG) {
            // Show debug rendering if the game is run in debug mode
            debugRenderer.render(world, cam.combined);
        }
    }

    /**
     * A method to continue rendering the game while it is paused
     *
     * @param delta Time between the previous and current call to render()
     */
    private void drawPausedGame(float delta) {
        batch.setProjectionMatrix(cam.combined);

        // Only use the render system so that the game is still rendered
        ecsEngine.getSystem(RenderingSystem.class).update(delta);

        if (RogueTrails.DEBUG) {
            // Show debug rendering if the game is run in debug mode
            debugRenderer.render(world, cam.combined);
        }

        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        dun.draw(shape);
        shape.end();
    }
}
