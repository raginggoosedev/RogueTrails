package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.room.Cell;

public class GameScreen implements Screen {
    private final RogueTrails game;
    private final SpriteBatch batch;

    private final ShapeRenderer shape;

    public GameScreen(RogueTrails game) {
        this.game = game;
        this.batch = game.getBatch();

        shape = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Draw a small rectangle
        shape.begin(ShapeRenderer.ShapeType.Line);

        //GAME VARIABLES
        Cell startRoom = new Cell(300,300);
        Cell room2 = new Cell(200, 80);
        Cell room3 = new Cell(60,60);
        startRoom.setEast(room2);
        room2.setNorth(room3);
        Dungeon dungeon = new Dungeon(startRoom, null);

        dungeon.draw(10, 10, shape);


        //shape.rect(10, 10, 10, 10);
        shape.end();
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
