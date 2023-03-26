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

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        // Draw a small rectangle
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);

        //GAME VARIABLES
        Cell startRoom = new Cell(300, 300);
        Cell room2 = new Cell(200, 80);
        Cell room3 = new Cell(60, 60);
        startRoom.setEast(room2);
        room2.setNorth(room3);
        Dungeon dungeon = new Dungeon(startRoom, null);

        dungeon.draw(10, 10, shape);

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
