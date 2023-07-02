package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.collisions.CollisionBox;
import com.raginggoose.roguetrails.collisions.CollisionWorld;

import java.util.ArrayList;


public class Hallway extends Room {

    private static final float WALL_WIDTH = 5;
    //Room attributes
    private final int w;
    private final int h;
    private final RoomType TYPE = RoomType.HALLWAY;
    private final Orientation ORIENTATION;
    private final CollisionWorld world;
    public String name;
    private int x = 0;
    private int y = 0;
    //Adjacent rooms
    private Room NORTH = null;
    private Room EAST = null;
    private Room SOUTH = null;
    private Room WEST = null;
    private Room PARENT = null;
    private ArrayList<CollisionBox> collisionBoxes;

    //constructor without dimensions
    public Hallway(Orientation orientation, CollisionWorld world) {
        this.world = world;
        ORIENTATION = orientation;
        w = getDefaultWidth(orientation);
        h = getDefaultHeight(orientation);

        collisionBoxes = new ArrayList<>();
    }

    //constructor with dimensions
    public Hallway(int w, int h, Orientation orientation, CollisionWorld world) {
        this.world = world;
        ORIENTATION = orientation;
        this.w = w;
        this.h = h;

        collisionBoxes = new ArrayList<>();
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
        if (ORIENTATION == Orientation.VERTICAL) {
            if (PARENT == null) PARENT = room;
            if (NORTH == room) return;

            room.setX(this.x + this.w / 2 - room.getWidth() / 2);
            room.setY(this.y + this.h);
            NORTH = room;

            room.setSouth(this);

            // Initialize the collisionBoxes list and add the collision boxes for the top wall
            collisionBoxes = new ArrayList<>();
            collisionBoxes.add(new CollisionBox(new Vector2(x - WALL_WIDTH, y), WALL_WIDTH, h, this));
            collisionBoxes.add(new CollisionBox(new Vector2(x + w, y), WALL_WIDTH, h, this));

            for (CollisionBox b : collisionBoxes) {
                world.addCollisionBox(b);
            }
        }
    }

    @Override
    public Room getEast() {
        return EAST;
    }

    @Override
    public void setEast(Room room) {
        if (ORIENTATION == Orientation.HORIZONTAL) {
            if (PARENT == null) PARENT = room;
            if (EAST == room) return;

            room.setX(this.x + this.w);
            room.setY(this.y + this.h / 2 - room.getWidth() / 2);
            EAST = room;

            room.setWest(this);

            // Initialize the collisionBoxes list and add the collision boxes for the right wall
            collisionBoxes = new ArrayList<>();
            collisionBoxes.add(new CollisionBox(new Vector2(x, y - WALL_WIDTH), w, WALL_WIDTH, this)); // This one won't work
            collisionBoxes.add(new CollisionBox(new Vector2(x, y + h - WALL_WIDTH), w, WALL_WIDTH, this));

            for (CollisionBox b : collisionBoxes) {
                System.out.println("Adding box " + b);
                world.addCollisionBox(b);
            }
        }
    }


    @Override
    public Room getSouth() {
        return SOUTH;
    }

    @Override
    public void setSouth(Room room) {
        if (ORIENTATION == Orientation.VERTICAL) {
            if (PARENT == null) PARENT = room;
            if (SOUTH == room) return;

            room.setX(this.x + this.w / 2 - room.getWidth() / 2);
            room.setY(this.y - room.getHeight());
            SOUTH = room;

            room.setNorth(this);

            // Initialize the collisionBoxes list and add the collision boxes for the bottom wall
            collisionBoxes = new ArrayList<>();
            collisionBoxes.add(new CollisionBox(new Vector2(x - WALL_WIDTH, y), WALL_WIDTH, h, this));
            collisionBoxes.add(new CollisionBox(new Vector2(x + w, y), WALL_WIDTH, h, this));

            for (CollisionBox b : collisionBoxes) {
                world.addCollisionBox(b);
            }
        }
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
        collisionBoxes.add(new CollisionBox(new Vector2(x, y - WALL_WIDTH), w, WALL_WIDTH, this));
        collisionBoxes.add(new CollisionBox(new Vector2(x, y + h - WALL_WIDTH), w, WALL_WIDTH, this));

        for (CollisionBox b : collisionBoxes) {
            world.addCollisionBox(b);
        }
    }

    @Override
    public RoomType getRoomType() {
        return TYPE;
    }

    @Override
    //public void draw(int x, int y, ShapeRenderer shape) {
    public void draw(ShapeRenderer shape) {
        for (CollisionBox b : collisionBoxes) {
            shape.rect(b.getPosition().x, b.getPosition().y, b.getWidth(), b.getHeight());
        }
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
    public Room getParent() {
        return PARENT;
    }

    @Override
    public void setParent(Room room) {
        PARENT = room;
    }

    @Override
    public boolean isParentOf(Room room) {
        return (this == room.getParent());
    }
}
