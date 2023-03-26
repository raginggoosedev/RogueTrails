package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.raginggoose.roguetrails.Player;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.room.Cell;
import com.raginggoose.roguetrails.room.Hallway;
import com.raginggoose.roguetrails.room.Orientation;

public class GameScreen implements Screen {
    private final RogueTrails game;
    private final SpriteBatch batch;
    private final Player testPlayer;

    private final ShapeRenderer shape;

    private final OrthographicCamera cam;

    public GameScreen(RogueTrails game) {
        this.game = game;
        this.batch = game.getBatch();

        shape = new ShapeRenderer();

        testPlayer = new Player(100, 100);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() /2f, Gdx.graphics.getHeight() /2f);
    }

    @Override
    public void show() {

    }

    public Dungeon makeDungeon() {
        Cell start = new Cell(300,300);
        Hallway hall1 = new Hallway(500,80, Orientation.HORIZONTAL);
        Cell cellA = new Cell(300,300);
        Hallway hall2 = new Hallway(80,500, Orientation.VERTICAL);
        Hallway hall3 = new Hallway(500,80, Orientation.HORIZONTAL);
        Cell cellB = new Cell(300,300);
        Cell cellD = new Cell(100,100);
        Hallway hall4 = new Hallway(500,80, Orientation.HORIZONTAL);
        Cell cellC = new Cell(1000,1500);
        Cell cellE = new Cell(80,80);

        start.setEast(hall1);
        hall1.setEast(cellA);
        cellA.setSouth(hall2);
        hall2.setSouth(cellE);
        cellE.setWest(hall3);
        hall3.setWest(cellB);
        cellB.setSouth(cellD);
        cellE.setEast(hall4);
        hall4.setEast(cellC);

        Dungeon dungeon = new Dungeon(start, null);
        return dungeon;

    }

    Dungeon dun = makeDungeon();

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        // Draw a small rectangle
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);

        dun.draw(10,10,shape);

        testPlayer.draw(shape);
        shape.end();

        // Move player, camera moves with player
        testPlayer.move();
        cam.position.set(new Vector2(testPlayer.getX(), testPlayer.getY()), 0);
        cam.update();
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
