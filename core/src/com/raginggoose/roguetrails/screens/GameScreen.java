package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.raginggoose.roguetrails.player.Direction;
import com.raginggoose.roguetrails.player.Player;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.room.Cell;
import com.raginggoose.roguetrails.room.Hallway;
import com.raginggoose.roguetrails.room.Orientation;
import com.raginggoose.roguetrails.room.Room;

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

        testPlayer = new Player(145, 145);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() /2f, Gdx.graphics.getHeight() /2f);
    }

    @Override
    public void show() {

    }

    public Dungeon makeDungeon() {
        Cell start = new Cell(300,300);
        Hallway hall1 = new Hallway(300,80, Orientation.HORIZONTAL);
        Cell cellA = new Cell(300,300);
        Hallway hall2 = new Hallway(80,300, Orientation.VERTICAL);
        Hallway hall3 = new Hallway(300,80, Orientation.HORIZONTAL);
        Cell cellB = new Cell(300,300);
        Cell cellD = new Cell(100,100);
        Hallway hall4 = new Hallway(300,80, Orientation.HORIZONTAL);
        Cell cellC = new Cell(1000,1500);
        Cell cellE = new Cell(80,80);

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


        start.name= "start";
        cellA.name="cellA";
        cellB.name="cellB";
        cellC.name="cellC";
        cellD.name="cellD";
        hall1.name="hall1";
        hall2.name="hall2";
        hall3.name="hall3";
        hall4.name="hall4";

        return dungeon;

    }

    public Direction checkCollision(Player player, Room room) {

        int px1 = player.getX();
        int px2 = player.getX() + player.SIZE;
        int py1 = player.getY();
        int py2 = player.getY() + player.SIZE;

        int leftBound = room.getX();
        int rightBound = room.getX() + room.getWidth();
        int lowerBound = room.getY();
        int upperBound = room.getY() + room.getHeight();

        if (px1 <= leftBound) {
            if (room.getWest() == null) return Direction.LEFT;
            if (py1 < room.getWest().getY() || py2 > room.getWest().getY() + room.getWest().getHeight()) return Direction.LEFT;
        }
        if (px2 >= rightBound) {
            if (room.getEast() == null) return Direction.RIGHT;
            if (py1 < room.getEast().getY() || py2 > room.getEast().getY() + room.getEast().getHeight()) return Direction.RIGHT;
        }
        if (py1 <= lowerBound) {
            if (room.getSouth() == null) return Direction.DOWN;
            if (px1 < room.getSouth().getX() || px2 > room.getSouth().getX() + room.getSouth().getWidth()) return Direction.DOWN;
        }
        if (py2 >= upperBound) {
            if (room.getNorth() == null) return Direction.UP;
            if (px1 < room.getNorth().getX() || px2 > room.getNorth().getX() + room.getNorth().getWidth()) return Direction.UP;
        }

        return null;

    }

    Dungeon dun = makeDungeon();

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        // Draw a small rectangle
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);

        dun.draw(shape);


        testPlayer.draw(shape);
        shape.end();

        // Move player, camera moves with player
        testPlayer.move(checkCollision(testPlayer, dun.getCurrentRoom(testPlayer)));
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
