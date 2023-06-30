package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.audio.AudioType;
import com.raginggoose.roguetrails.collisions.CollisionWorld;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.ECSEngine;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.ItemComponent;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.systems.DebugRenderingSystem;
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

public class GameScreen implements Screen {
    private final RogueTrails game;
    private final SpriteBatch batch;
    private final ECSEngine ecsEngine;
    private final AssetManager assetManager;
    private final AssetLoader assetLoader;
    private final ShapeRenderer shape;
    private final OrthographicCamera cam;
    private final HUD hud;
    private final Skin skin;
    private final Stage stage;
    private final Inventory inventory;
    private final CollisionWorld world;
    private final PlayerComponent playerComponent;
    private final Menu menu;
    public Dungeon dun;
    private boolean paused;

    /**
     * Create a new game screen to display and play the game
     *
     * @param game the parent game class
     */
    public GameScreen(RogueTrails game) {

        this.game = game;
        this.batch = game.getBatch();
        assetManager = game.getAssetManager().manager;
        assetLoader = game.getAssetManager();
        shape = new ShapeRenderer();

        if (game.getPreferences().isMusicEnabled())
            game.getAudioManager().playAudio(AudioType.BACKGROUND);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        world = new CollisionWorld();


        ecsEngine = new ECSEngine(shape, batch, cam, world, assetLoader);
        ecsEngine.createPlayer(10, 10, 32, 32, 0);


        dun = makeDungeon();
        dun.updateBoxes();

        spawnItems(dun.getStart(), 200, 100);

        ecsEngine.addSystem(new PlayerMovementSystem(dun));

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
        Gdx.input.setInputProcessor(stage);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));

        if (paused) {
            resume();
        }
    }

    public Dungeon makeDungeon() {
        Cell start = new Cell(300, 300, ecsEngine, world);
        Hallway hall1 = new Hallway(300, 80, Orientation.HORIZONTAL, world);
        Cell cellA = new Cell(300, 300, ecsEngine, world);
        Hallway hall2 = new Hallway(80, 300, Orientation.VERTICAL, world);
        Hallway hall3 = new Hallway(300, 80, Orientation.HORIZONTAL, world);
        Cell cellB = new Cell(300, 300, ecsEngine, world);
        Cell cellD = new Cell(100, 100, ecsEngine, world);
        Hallway hall4 = new Hallway(300, 80, Orientation.HORIZONTAL, world);
        Cell cellC = new Cell(1000, 1000, ecsEngine, world);
        Cell cellE = new Cell(80, 80, ecsEngine, world);

        Dungeon dungeon = new Dungeon(start, null);

        start.setEast(hall1);
        hall1.setEast(cellA);
        cellA.setSouth(hall2);
        hall2.setSouth(cellE);
        cellE.setEast(hall3);
        hall3.setEast(cellC);
        cellE.setWest(hall4);
        hall4.setWest(cellB);
        cellB.setSouth(cellD);

        world.setDungeon(dungeon);
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

        if (!paused) {
            // Game is not paused, logic and rendering should be done
            processInput();
            updateGameLogic(delta);
            drawGame();

            hud.updateInventory(inventory);
            hud.updateHealth(playerComponent.health);
        } else {
            // Game is paused, only rendering should be done
            drawPausedGame(delta);
        }

        // Stage is drawn regardless
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

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
    }

    private void processInput() {
        // If escape is pressed, the game is paused
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            pause();
    }

    private void updateGameLogic(float delta) {
        // Update entities and the physics world
        ecsEngine.update(delta);
        world.update();
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
    }

    /**
     * A method to continue rendering the game while it is paused
     * @param delta Time between the previous and current call to render()
     */
    private void drawPausedGame(float delta) {
        batch.setProjectionMatrix(cam.combined);

        // Only use the render system so that the game is still rendered
        ecsEngine.getSystem(RenderingSystem.class).update(delta);

        if (RogueTrails.DEBUG) {
            // Show debug rendering if the game is run in debug mode
            ecsEngine.getSystem(DebugRenderingSystem.class).update(delta);
        }

        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        dun.draw(shape);
        shape.end();
    }
}
