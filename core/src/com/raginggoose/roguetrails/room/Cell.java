package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.collisions.CollisionBox;
import com.raginggoose.roguetrails.collisions.CollisionWorld;
import com.raginggoose.roguetrails.ecs.ECSEngine;


public class Cell extends Room {

    //Room attributes
    private final int w;
    private final int h;
    private final RoomType TYPE = RoomType.CELL;
    private final ECSEngine ecsEngine;
    public String name;
    private int x = 0;
    private int y = 0;
    //Adjacent rooms
    private Room NORTH = null;
    private Room EAST = null;
    private Room SOUTH = null;
    private Room WEST = null;
    private Room PARENT = null;
    private final CollisionBox box;

    //Constructor if no parameters given
    /* public Cell() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
    }*/

    //Constructor if width and height given
    public Cell(int w, int h, ECSEngine ecsEngine, CollisionWorld world) {
        this.w = w;
        this.h = h;
        this.ecsEngine = ecsEngine;
        box = new CollisionBox(new Vector2(x, y), w, h, this);
        world.addCollisionBox(box);
    }


    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public Room getNorth() {
        return NORTH;
    }

    @Override
    public void setNorth(Room room) {
//        room.moveX(this.w/2 - room.getWidth()/2);
//        room.moveY(room.getHeight());
        if (PARENT == null) PARENT = room;
        if (NORTH == room) return;
        room.setX(this.x + this.w / 2 - room.getWidth() / 2);
        room.setY(this.y + this.h);
        NORTH = room;
        addEnemies();

        room.setSouth(this);
    }

    @Override
    public Room getEast() {
        return EAST;
    }

    @Override
    public void setEast(Room room) {
        if (PARENT == null) PARENT = room;
        if (EAST == room) return;

        room.setX(this.x + this.w);
        room.setY(this.y + this.h / 2 - room.getHeight() / 2);
        EAST = room;
        addEnemies();

        room.setWest(this);
    }

    @Override
    public Room getSouth() {
        return SOUTH;
    }

    @Override
    public void setSouth(Room room) {
//        room.moveX(this.w/2-room.getWidth()/2);
//        room.moveY(this.h);
        if (PARENT == null) PARENT = room;
        if (SOUTH == room) return;

        room.setX(this.x + this.w / 2 - room.getWidth() / 2);
        room.setY(this.y - room.getHeight());
        SOUTH = room;
        addEnemies();

        room.setNorth(this);
    }

    @Override
    public Room getWest() {
        return WEST;
    }


    @Override
    public void setWest(Room room) {
//        room.moveX(-room.getWidth());
//        room.moveY(-this.w/2 + room.getHeight()/2);
        if (PARENT == null) PARENT = room;
        if (WEST == room) return;

        room.setX(this.x - room.getWidth());
        room.setY(this.y + this.h / 2 - room.getHeight() / 2);
        WEST = room;
        addEnemies();

        room.setEast(this);
    }

    @Override
    public Room getParent() {
        return PARENT;
    }

    public void addEnemies() {
        int numEnemies = MathUtils.random(1, 5);

        for (int i = 0; i < numEnemies; i++) {
            int enemyX = MathUtils.random(x, x + w - 32);
            int enemyY = MathUtils.random(y, y + h - 32);

            ecsEngine.createEnemy(enemyX, enemyY, 32, 32, 1, 1.0f);
        }
    }

    @Override
    public RoomType getRoomType() {
        return TYPE;
    }

    //public void draw(int x, int y, ShapeRenderer shape) {
    public void draw(ShapeRenderer shape) {
        shape.rect(x, y, w, h);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public void moveX(int dx) {
        x += dx;
    }

    public void moveY(int dy) {
        y += dy;
    }

    public String getName() {
        return name;
    }

    public CollisionBox getBox() {
        return box;
    }

    @Override
    public void setParent(Room room) {
        PARENT = room;
    }

    @Override
    public boolean isParentOf(Room room) {
        return this == room.getParent();
    }
}
