package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.hud.HUD;
import com.raginggoose.roguetrails.inventory.Inventory;
import com.raginggoose.roguetrails.loader.AssetLoader;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.ECSEngine;
import com.raginggoose.roguetrails.ecs.components.ItemComponent;
import com.raginggoose.roguetrails.ecs.systems.PlayerMovementSystem;
import com.raginggoose.roguetrails.room.Cell;
import com.raginggoose.roguetrails.room.Hallway;
import com.raginggoose.roguetrails.room.Orientation;
import com.raginggoose.roguetrails.room.Room;

public class GameScreen implements Screen {
    public final Dungeon dun;
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

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ecsEngine = new ECSEngine(shape, batch, cam);
        ecsEngine.createPlayer(10, 10, 32, 32, 0, assetManager);

        dun = makeDungeon();

        spawnItems(dun.getStart(), 200, 100);

        ecsEngine.addSystem(new PlayerMovementSystem(dun));

        skin = new Skin(Gdx.files.internal("skin.json"));

        inventory = Mapper.PLAYER_MAPPER.get(ecsEngine.getPlayer()).inventory;
        hud = new HUD(inventory, skin);

        stage = new Stage();
        hud.setStage(stage);
    }

    @Override
    public void show() {

    }

    public Dungeon makeDungeon() {
        Cell start = new Cell(300, 300, ecsEngine);
        Hallway hall1 = new Hallway(300, 80, Orientation.HORIZONTAL);
        Cell cellA = new Cell(300, 300, ecsEngine);
        Hallway hall2 = new Hallway(80, 300, Orientation.VERTICAL);
        Hallway hall3 = new Hallway(300, 80, Orientation.HORIZONTAL);
        Cell cellB = new Cell(300, 300, ecsEngine);
        Cell cellD = new Cell(100, 100, ecsEngine);
        Hallway hall4 = new Hallway(300, 80, Orientation.HORIZONTAL);
        Cell cellC = new Cell(1000, 1500, ecsEngine);
        Cell cellE = new Cell(80, 80, ecsEngine);

        Dungeon dungeon = new Dungeon(start, null);

        start.setEast(hall1);
        hall1.setEast(cellA);
        cellA.setSouth(hall2);
        hall2.setSouth(cellE);
        cellE.setWest(hall3);
        hall3.setWest(cellB);
        cellB.setSouth(cellD);
        cellE.setEast(hall4);
        hall4.setEast(cellC);

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
        ScreenUtils.clear(0, 0, 0, 1);
        // Draw a small rectangle
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        assetLoader.queueAssets();

        dun.draw(shape);

        shape.end();

        batch.setProjectionMatrix(cam.combined);
        //TODO add collision system to ecs
        ecsEngine.update(delta);

        hud.updateInventory(inventory);
        stage.draw();
        stage.act(delta);
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
        shape.dispose();
        stage.dispose();
    }
}
