package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Hallway extends Room {

    //Room attributes
    private final int w;
    private final int h;
    private final RoomType TYPE = RoomType.HALLWAY;
    private final Orientation ORIENTATION;
    private int x = 0;
    private int y = 0;
    public String name;

    //Adjacent rooms
    private Room NORTH = null;
    private Room EAST = null;
    private Room SOUTH = null;
    private Room WEST = null;
    private Room PARENT = null;

    //constructor without dimensions
    public Hallway (Orientation orientation) {
        ORIENTATION = orientation;
        w = getDefaultWidth(orientation);
        h = getDefaultHeight(orientation);

    }

    //constructor with dimensions
    public Hallway (int w, int h, Orientation orientation) {
        ORIENTATION = orientation;
        this.w = w;
        this.h = h;
    }

    private int getDefaultWidth(Orientation orientation) {
        int w = 0;

        if (orientation == Orientation.VERTICAL) {
            w = Gdx.graphics.getWidth()/2;
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
            h = Gdx.graphics.getHeight()/2;
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
    public Room getEast() {
        return EAST;
    }

    @Override
    public Room getSouth() {
        return SOUTH;
    }

    @Override
    public Room getWest() {
        return WEST;
    }

    @Override
    public Room getParent() {
        return PARENT;
    }

    @Override
    public void setNorth(Room room) {
        if (ORIENTATION == Orientation.VERTICAL)
            if(PARENT == null) PARENT = room;
            room.setX(this.x + this.w/2 - room.getWidth()/2);
            room.setY(this.y + this.h);
            NORTH = room;
    }

    @Override
    public void setEast(Room room) {
        if (ORIENTATION == Orientation.HORIZONTAL)
            if (PARENT == null) PARENT = room;
            room.setX(this.x + this.w);
            room.setY(this.y + this.h/2 - room.getWidth()/2);
            EAST = room;
    }

    @Override
    public void setSouth(Room room) {
        if (ORIENTATION == Orientation.VERTICAL)
            if (PARENT == null) PARENT = room;
            room.setX(this.x + this.w/2 - room.getWidth()/2);
            room.setY(this.y-room.getHeight());
            SOUTH = room;
    }

    @Override
    public void setWest(Room room) {
        if (ORIENTATION == Orientation.HORIZONTAL)
            if (PARENT == null) PARENT = room;
            room.setX(this.x-room.getWidth());
            room.setY(this.y + this.h/2 - room.getHeight()/2);
            WEST = room;
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
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x=x;
    }

    @Override
    public void setY(int y) {
        this.y=y;
    }

    public String getName() {
        return name;
    }

    public void moveX(int dx) {
        x+=dx;
    }

    public void moveY(int dy) {
        y+=dy;
    }

    @Override
    public boolean isParentOf(Room room) {
        return (this == room.getParent());
    }
}
