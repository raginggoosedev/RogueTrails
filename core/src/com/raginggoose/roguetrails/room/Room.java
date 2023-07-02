package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.raginggoose.roguetrails.collisions.CollisionBox;

import java.awt.*;

public abstract class Room {

    //width and height
    public abstract int getWidth();
    public abstract int getHeight();

    //adjacent rooms
    public abstract Room getNorth();
    public abstract Room getEast();
    public abstract Room getSouth();
    public abstract Room getWest();
    public abstract Room getParent();
    public abstract void setNorth(Room room);
    public abstract void setEast(Room room);
    public abstract void setSouth(Room room);
    public abstract void setWest(Room room);

    //room type
    public abstract RoomType getRoomType();

    //to draw
    //public abstract void draw(int x, int y, ShapeRenderer shape); //to draw room
    public abstract void draw(ShapeRenderer shape); //to draw room
    public abstract int getX();
    public abstract int getY();
    public abstract void setX(int x);
    public abstract void setY(int y);
    public abstract String getName();
    public abstract void moveX(int dx);
    public abstract void moveY(int dy);
    public abstract boolean isParentOf(Room room);
    public abstract void setParent(Room room);
}
