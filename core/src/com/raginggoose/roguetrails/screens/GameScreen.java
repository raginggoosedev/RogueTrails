package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.raginggoose.roguetrails.item.Broadsword;
import com.raginggoose.roguetrails.item.Item;
import com.raginggoose.roguetrails.item.Shortsword;
import com.raginggoose.roguetrails.ecs.systems.PlayerMovementSystem;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.ECSEngine;
import com.raginggoose.roguetrails.room.Cell;
import com.raginggoose.roguetrails.room.Hallway;
import com.raginggoose.roguetrails.room.Orientation;
import com.raginggoose.roguetrails.room.Room;

public class GameScreen implements Screen {
    private final RogueTrails game;
    private final SpriteBatch batch;
    private final ECSEngine ecsEngine;

    private final ShapeRenderer shape;

    private final OrthographicCamera cam;
    public final Dungeon dun;

    /**
     * Create a new game screen to display and play the game
     * @param game the parent game class
     */
    public GameScreen(RogueTrails game) {
        this.game = game;
        this.batch = game.getBatch();

        shape = new ShapeRenderer();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ecsEngine = new ECSEngine(shape, cam);
        ecsEngine.createPlayer(10, 10, 32, 32, 0, Color.BLUE);

        dun = makeDungeon();

        ecsEngine.addSystem(new PlayerMovementSystem(dun));
    }

    @Override
    public void show() {

    }

    public Dungeon makeDungeon() {
        Cell start = new Cell(300,300, ecsEngine);
        Hallway hall1 = new Hallway(300,80, Orientation.HORIZONTAL);
        Cell cellA = new Cell(300,300, ecsEngine);
        Hallway hall2 = new Hallway(80,300, Orientation.VERTICAL);
        Hallway hall3 = new Hallway(300,80, Orientation.HORIZONTAL);
        Cell cellB = new Cell(300,300, ecsEngine);
        Cell cellD = new Cell(100,100, ecsEngine);
        Hallway hall4 = new Hallway(300,80, Orientation.HORIZONTAL);
        Cell cellC = new Cell(1000,1500, ecsEngine);
        Cell cellE = new Cell(80,80, ecsEngine);

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
    public Item[] spawnItems(Room room, int x, int y) {
        int rx = room.getX();
        int ry = room.getY();
        Shortsword shortsword = new Shortsword(rx+200,ry+100);
        Broadsword broad = new Broadsword(rx+250,ry+250);

        Item items[] = {shortsword, broad};

        return items;
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        // Draw a small rectangle
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);

        Item items[]  = spawnItems(dun.getStart(), 200,100);

        dun.draw(shape);

        for (int i = 0; i < items.length; i++) {
            items[i].draw(shape);
        }

        shape.end();

        //TODO add collision system to ecs
        ecsEngine.update(delta);
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
    }
}
