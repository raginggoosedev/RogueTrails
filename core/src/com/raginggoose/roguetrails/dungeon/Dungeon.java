package com.raginggoose.roguetrails.dungeon;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.raginggoose.roguetrails.ecs.ECSEngine;
import com.raginggoose.roguetrails.player.Player;
import com.raginggoose.roguetrails.room.Room;

import java.awt.*;
import java.util.ArrayList;

public class Dungeon implements Cloneable {

    //Start and End rooms
    private final Room START;
    private final Room END;

    //position
    private int x;
    private int y;

    //Constructor without position
    public Dungeon (Room start, Room end) {
        START = start;
        END = null; //TODO: make this not null
        x = 0;
        y = 0;
    }

    //Constructor with position
    public Dungeon (Room start, Room end, int x, int y) {
        START = start;
        END = null; //TODO: make this not null
        this.x = x;
        this.y = y;
    }

    public Room getStart() {
        return START;
    }

    public Room getEnd() {
        return END;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveX(int dx) {
        x += dx;
    }

    public void moveY(int dy) {
        y += dy;
    }

    //public void draw(int x, int y, ShapeRenderer shape) {
    public void draw(ShapeRenderer shape) {
        //the base draw method
        //draw(x, y, START, shape);
        draw(START, shape);
    }

    //public void draw (int x, int y, Room room, ShapeRenderer shape) {
    public void draw (Room room, ShapeRenderer shape) {
        //get adjacent rooms
        Room north = room.getNorth();
        Room east = room.getEast();
        Room south = room.getSouth();
        Room west = room.getWest();

        //room.draw(x,y,shape);
        room.draw(shape);

        if (north != null) {
            draw(north, shape);
        }

        if (east != null) {
              draw(east, shape);
        }

        if (south != null) {
            draw(south, shape);
        }

        if (west != null) {
              draw(west, shape);
        }
    }

    public Room getCurrentRoom(float x, float y) {

        ArrayList<Room> roomList = new ArrayList<>();
        putIntoArray(START, roomList);

        for (Room r : roomList) {
            if (inRoom(x, y, r)) return r;
        }

        return null;
    }

    private void putIntoArray(Room room, ArrayList<Room> result) {
        if (!result.contains(room)) {
            result.add(room);
            if (room.getNorth() != null && !result.contains(room.getNorth())) {
                putIntoArray(room.getNorth(), result);
            }
            if (room.getEast() != null && !result.contains(room.getEast())) {
                putIntoArray(room.getEast(), result);
            }
            if (room.getSouth() != null && !result.contains(room.getSouth())) {
                putIntoArray(room.getSouth(), result);
            }
            if (room.getWest() != null && !result.contains(room.getWest())) {
                putIntoArray(room.getWest(), result);
            }
        }
    }



    private boolean inRoom(float playerX, float playerY, Room room) {

        if (room == null) return false;

        //room
        int x1 = room.getX();
        int x2 = x1 + room.getWidth();
        int y1 = room.getY();
        int y2 = y1 + room.getHeight();

        return (playerX >= x1 && playerX <= x2 && playerY >= y1 && playerY <= y2);
    }

}
