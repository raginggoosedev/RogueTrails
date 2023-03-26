package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.Gdx;
import java.awt.*; //replace with libgdx library later

public class Cell extends Room {

    //Room attributes
    private final int w;
    private final int h;
    private final RoomType TYPE = RoomType.CELL;

    //Adjacent rooms
    private Room NORTH = null;
    private Room EAST = null;
    private Room SOUTH = null;
    private Room WEST = null;

    //Constructor if no parameters given
    public Cell() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
    }

    //Constructor if width and height given
    public Cell (int w, int h) {
        this.w = w;
        this.h = h;
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
        NORTH = room;
    }

    @Override
    public void setEast(Room room) {
        EAST = room;
    }

    @Override
    public void setSouth(Room room) {
        SOUTH = room;
    }

    @Override
    public void setWest(Room room) {
        WEST = room;
    }

    @Override
    public RoomType getRoomType() {
        return TYPE;
    }

    @Override
    public void draw(int x, int y, Graphics g) {
        g.fillRect(x,y,w,h);
    }
}
