package com.raginggoose.roguetrails.dungeon;

import com.badlogic.gdx.Gdx;

import java.awt.*;

public class Hallway extends Room {

    //Room attributes
    private final int w;
    private final int h;
    private final RoomType TYPE = RoomType.HALLWAY;
    private final Orientation ORIENTATION;

    //Adjacent rooms
    private Room NORTH = null;
    private Room EAST = null;
    private Room SOUTH = null;
    private Room WEST = null;

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
    public void setNorth(Room room) {
        if (ORIENTATION == Orientation.VERTICAL)
            NORTH = room;
    }

    @Override
    public void setEast(Room room) {
        if (ORIENTATION == Orientation.HORIZONTAL)
            EAST = room;
    }

    @Override
    public void setSouth(Room room) {
        if (ORIENTATION == Orientation.VERTICAL)
            SOUTH = room;
    }

    @Override
    public void setWest(Room room) {
        if (ORIENTATION == Orientation.HORIZONTAL)
            WEST = room;
    }

    @Override
    public RoomType getRoomType() {
        return TYPE;
    }

    @Override
    public void draw(int x, int y, Graphics g) {

    }
}
