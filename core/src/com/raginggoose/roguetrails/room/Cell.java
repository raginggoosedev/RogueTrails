package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.raginggoose.roguetrails.dungeon.Dungeon;


public class Cell extends Room {

    //Room attributes
    private final int w;
    private final int h;
    private final RoomType TYPE = RoomType.CELL;
    private int x = 0;
    private int y = 0;
    public String name;

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
//        room.moveX(this.w/2 - room.getWidth()/2);
//        room.moveY(room.getHeight());
        room.setX(this.x + this.w/2 - room.getWidth()/2);
        room.setY(this.y + this.h);
        NORTH = room;
    }

    @Override
    public void setEast(Room room) {
//        room.moveX(this.w);
//        room.moveY(this.h/2 - room.getHeight()/2);
        room.setX(this.x + this.w);
        room.setY(this.y + this.h/2 - room.getHeight()/2);
        EAST = room;
    }

    @Override
    public void setSouth(Room room) {
//        room.moveX(this.w/2-room.getWidth()/2);
//        room.moveY(this.h);
        room.setX(this.x + this.w/2 - room.getWidth()/2);
        room.setY(this.y-room.getHeight());
        SOUTH = room;
    }

    @Override
    public void setWest(Room room) {
//        room.moveX(-room.getWidth());
//        room.moveY(-this.w/2 + room.getHeight()/2);
        room.setX(this.x-room.getWidth());
        room.setY(this.y + this.h/2 - room.getHeight()/2);
        WEST = room;
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

    public void moveX(int dx) {
        x+=dx;
    }

    public void moveY(int dy) {
        y+=dy;
    }
    public String getName() {
        return name;
    }

}
