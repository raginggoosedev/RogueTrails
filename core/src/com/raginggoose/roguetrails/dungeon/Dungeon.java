package com.raginggoose.roguetrails.dungeon;

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

    public void draw(Graphics g) {
        //the base draw method
        draw(x, y, START, g);
    }

    public void draw (int x, int y, Room room, Graphics g) {
        //get adjacent rooms
        Room north = room.getNorth();
        Room east = room.getEast();
        Room south = room.getSouth();
        Room west = room.getWest();

        room.draw(x,y,g);

        if (north != null) {
            x = x + room.getWidth()/2 - north.getWidth()/2;
            y = y - north.getHeight();
            draw(x,y, north, g);
        }

        if (east != null) {
            x = x + room.getWidth();
            y = y + room.getHeight()/2 - east.getHeight()/2;
            draw(x,y, east, g);
        }

        if (south != null) {
            x = x + room.getWidth()/2 - south.getWidth()/2;
            y = y + room.getHeight();
            draw(x,y, south, g);
        }

        if (west != null) {
            x = x - west.getWidth();
            y = y + room.getHeight()/2 - west.getHeight()/2;
            draw(x,y, west, g);
        }
    }

}
