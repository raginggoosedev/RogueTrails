package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.collisions.CollisionBox;
import com.raginggoose.roguetrails.collisions.CollisionWorld;
import com.raginggoose.roguetrails.ecs.ECSEngine;

import java.util.ArrayList;


public class Cell extends Room {

    //Room attributes
    private final int w;
    private final int h;
    private final RoomType TYPE = RoomType.CELL;
    private final ECSEngine ecsEngine;
    private final ArrayList<CollisionBox> collisionBoxes;
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

    //Constructor if no parameters given
    /* public Cell() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
    }*/

    //Constructor if width and height given
    public Cell(int w, int h, ECSEngine ecsEngine, CollisionWorld world) {
        this.w = w;
        this.h = h;
        this.ecsEngine = ecsEngine;
        this.world = world;

        collisionBoxes = new ArrayList<>();
    }

    public void createCollisionBoxes() {
        int hallwayGap = 80; // Adjust the gap width as needed

        // Calculate the position of the hallway gap for both width and height
        int hallwayPositionX = (w - hallwayGap) / 2 + x;
        int hallwayPositionY = (h - hallwayGap) / 2 + y;

        // Create collision boxes for the top wall
        if (NORTH != null) {
            collisionBoxes.add(new CollisionBox(new Vector2(x, y + h), hallwayPositionX - x, 5, this)); // Left side wall
            collisionBoxes.add(new CollisionBox(new Vector2(hallwayPositionX + hallwayGap, y + h), (x + w) - (hallwayPositionX + hallwayGap), 5, this)); // Right side wall
        } else {
            collisionBoxes.add(new CollisionBox(new Vector2(x, y + h - 1), w, 5, this)); // Full top wall
        }

        // Create collision boxes for the right wall
        if (EAST != null) {
            collisionBoxes.add(new CollisionBox(new Vector2(x + w - 1, y), 5, hallwayPositionY - y, this)); // Top side wall
            collisionBoxes.add(new CollisionBox(new Vector2(x + w - 1, hallwayPositionY + hallwayGap), 5, (y + h) - (hallwayPositionY + hallwayGap), this)); // Bottom side wall
        } else {
            collisionBoxes.add(new CollisionBox(new Vector2(x + w - 1, y), 5, h, this)); // Full right wall
        }

        // Create collision boxes for the bottom wall
        if (SOUTH != null) {
            collisionBoxes.add(new CollisionBox(new Vector2(x, y - 1), hallwayPositionX - x, 5, this)); // Left side wall
            collisionBoxes.add(new CollisionBox(new Vector2(hallwayPositionX + hallwayGap, y - 1), (x + w) - (hallwayPositionX + hallwayGap), 5, this)); // Right side wall
        } else {
            collisionBoxes.add(new CollisionBox(new Vector2(x, y), w, 5, this)); // Full bottom wall
        }

        // Create collision boxes for the left wall
        if (WEST != null) {
            collisionBoxes.add(new CollisionBox(new Vector2(x, y), 5, hallwayPositionY - y, this)); // Top side wall
            collisionBoxes.add(new CollisionBox(new Vector2(x, hallwayPositionY + hallwayGap), 5, (y + h) - (hallwayPositionY + hallwayGap), this)); // Bottom side wall
        } else {
            collisionBoxes.add(new CollisionBox(new Vector2(x, y), 5, h, this)); // Full left wall
        }

        // Add the collision boxes to the collision world
        for (CollisionBox box : collisionBoxes) {
            world.addCollisionBox(box);
        }
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
        if (PARENT == null) PARENT = room;
        if (NORTH == room) return;

        room.setX(this.x + (this.w - room.getWidth()) / 2);
        room.setY(this.y + this.h);
        NORTH = room;
        addEnemies();

        room.setSouth(this);
    }

    @Override
    public Room getEast() {
        return EAST;
    }

    @Override
    public void setEast(Room room) {
        if (PARENT == null) PARENT = room;
        if (EAST == room) return;

        room.setX(this.x + this.w);
        room.setY(this.y + (this.h - room.getHeight()) / 2);
        EAST = room;
        addEnemies();

        room.setWest(this);
    }

    @Override
    public Room getSouth() {
        return SOUTH;
    }

    @Override
    public void setSouth(Room room) {
        if (PARENT == null) PARENT = room;
        if (SOUTH == room) return;

        room.setX(this.x + (this.w - room.getWidth()) / 2);
        room.setY(this.y - room.getHeight());
        SOUTH = room;
        addEnemies();

        room.setNorth(this);
    }

    @Override
    public Room getWest() {
        return WEST;
    }


    @Override
    public void setWest(Room room) {
        if (PARENT == null) PARENT = room;
        if (WEST == room) return;

        room.setX(this.x - room.getWidth());
        room.setY(this.y + (this.h - room.getHeight()) / 2);
        WEST = room;
        addEnemies();

        room.setEast(this);
    }

    @Override
    public Room getParent() {
        return PARENT;
    }

    @Override
    public void setParent(Room room) {
        PARENT = room;
    }

    public void addEnemies() {
        int numEnemies = MathUtils.random(1, 5);

        for (int i = 0; i < numEnemies; i++) {
            int enemyX = MathUtils.random(x, x + w - 32);
            int enemyY = MathUtils.random(y, y + h - 32);

            ecsEngine.createEnemy(enemyX, enemyY, 32, 32, 1, 1.0f);
        }
    }

    @Override
    public RoomType getRoomType() {
        return TYPE;
    }

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

    public void moveX(int dx) {
        x += dx;
    }

    public void moveY(int dy) {
        y += dy;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isParentOf(Room room) {
        return this == room.getParent();
    }
}
