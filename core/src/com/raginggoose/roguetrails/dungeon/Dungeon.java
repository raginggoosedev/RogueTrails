package com.raginggoose.roguetrails.dungeon;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.raginggoose.roguetrails.player.Player;
import com.raginggoose.roguetrails.room.Room;

import java.awt.*;

public class Dungeon {

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
//            x = x + room.getWidth()/2 - north.getWidth()/2;
//            y = y - north.getHeight();
            //draw(x,y, north, shape);
            draw(north, shape);
        }

        if (east != null) {
//            x = x + room.getWidth();
//            y = y + room.getHeight()/2 - east.getHeight()/2;
//            draw(x,y, east, shape);
              draw(east, shape);
        }

        if (south != null) {
//            x = x + room.getWidth()/2 - south.getWidth()/2;
//            y = y + room.getHeight();
//            draw(x,y, south, shape);
            draw(south, shape);
        }

        if (west != null) {
//            x = x - west.getWidth();
//            y = y + room.getHeight()/2 - west.getHeight()/2;
//            draw(x,y, west, shape);
              draw(west, shape);
        }
    }

    public Room getCurrentRoom(Player player) {
        return getRoom(player, START);
    }

    private Room getRoom(Player player, Room room) {

        int px = player.getX();
        int py = player.getY();
        int x1 = room.getX();
        int x2 = x1 + room.getWidth();
        int y1 = room.getY();
        int y2 = y1 + room.getHeight();

        if (px >= x1 && px <= x2 && py >= y1 && py <= y2) {
            return room;
        }

        else {

            Room north = room.getNorth();
            Room east = room.getEast();
            Room south = room.getSouth();
            Room west = room.getWest();

            if (north != null)
                getRoom(player, north);
            if (east != null)
                getRoom(player, east);
            if (south != null)
                getRoom(player, south);
            if (west != null)
                getRoom(player, west);

        }

        return null;

    }
}
