package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.collisions.CollisionBox;
import com.raginggoose.roguetrails.collisions.CollisionWorld;


public class Hallway extends Room {

    //Room attributes
    private final int w;
    private final int h;
    private final CollisionBox box;
    private final RoomType TYPE = RoomType.HALLWAY;
    private final Orientation ORIENTATION;
    public String name;
    private int x = 0;
    private int y = 0;
    //Adjacent rooms
    private Room NORTH = null;
    private Room EAST = null;
    private Room SOUTH = null;
    private Room WEST = null;
    private Room PARENT = null;

    //constructor without dimensions
    public Hallway(Orientation orientation, CollisionWorld world) {
        ORIENTATION = orientation;
        w = getDefaultWidth(orientation);
        h = getDefaultHeight(orientation);

        box = new CollisionBox(new Vector2(x, y), w, h, this);
        world.addCollisionBox(box);
    }

    //constructor with dimensions
    public Hallway(int w, int h, Orientation orientation, CollisionWorld world) {
        ORIENTATION = orientation;
        this.w = w;
        this.h = h;

        box = new CollisionBox(new Vector2(x, y), w, h, this);
        world.addCollisionBox(box);
    }

    private int getDefaultWidth(Orientation orientation) {
        int w = 0;

        if (orientation == Orientation.VERTICAL) {
            w = Gdx.graphics.getWidth() / 2;
        }

        if (orientation == Orientation.HORIZONTAL) {
            w = Gdx.graphics.getWidth();
        }

        return w;
    }

    private int getDefaultHeight(Orientation orientation) {
        int h = 0;

        if (orientation == Orientation.VERTICAL) {
            h = Gdx.graphics.getHeight();
        }

        if (orientation == Orientation.HORIZONTAL) {
            h = Gdx.graphics.getHeight() / 2;
        }

        return h;
    }

    public Orientation getOrientation() {
        return ORIENTATION;
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
        if (ORIENTATION == Orientation.VERTICAL)
            if (PARENT == null) PARENT = room;
            if (NORTH == room) return;

            room.setX(this.x + this.w / 2 - room.getWidth() / 2);
            room.setY(this.y + this.h);
            NORTH = room;

            room.setSouth(this);
    }

    @Override
    public Room getEast() {
        return EAST;
    }

    @Override
    public void setEast(Room room) {
        if (ORIENTATION == Orientation.HORIZONTAL)
            if (PARENT == null) PARENT = room;
            if (EAST == room) return;

            room.setX(this.x + this.w);
            room.setY(this.y + this.h / 2 - room.getWidth() / 2);
            EAST = room;

            room.setWest(this);
    }

    @Override
    public Room getSouth() {
        return SOUTH;
    }

    @Override
    public void setSouth(Room room) {
        if (ORIENTATION == Orientation.VERTICAL)
            if (PARENT == null) PARENT = room;
            if (SOUTH == room) return;

            room.setX(this.x + this.w / 2 - room.getWidth() / 2);
            room.setY(this.y - room.getHeight());
            SOUTH = room;

            room.setNorth(this);
    }

    @Override
    public Room getWest() {
        return WEST;
    }

    @Override
    public void setWest(Room room) {
        if (ORIENTATION == Orientation.HORIZONTAL)
            if (PARENT == null) PARENT = room;
            if (WEST == room) return;

            room.setX(this.x - room.getWidth());
            room.setY(this.y + this.h / 2 - room.getHeight() / 2);
            WEST = room;

            room.setEast(this);
    }

    @Override
    public RoomType getRoomType() {
        return TYPE;
    }

    @Override
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

    public String getName() {
        return name;
    }

    public void moveX(int dx) {
        x += dx;
    }

    public void moveY(int dy) {
        y += dy;
    }

    @Override
    public CollisionBox getBox() {
        return box;
    }

    @Override
    public void setParent(Room room) {
        PARENT = room;
    }

    @Override
    public Room getParent() {
        return PARENT;
    }

    @Override
    public boolean isParentOf(Room room) {
        return (this == room.getParent());
    }
}
