package com.raginggoose.roguetrails.room;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.raginggoose.roguetrails.b2d.BodyFactory;
import com.raginggoose.roguetrails.ecs.ECSEngine;


public class Cell extends Room {

    //Room attributes
    private static final float WALL_THICKNESS = 5;
    private final int w;
    private final int h;
    private final RoomType TYPE = RoomType.CELL;
    private final ECSEngine ecsEngine;
    private final World world;
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
    public Cell(int w, int h, ECSEngine ecsEngine, World world) {
        this.w = w;
        this.h = h;
        this.ecsEngine = ecsEngine;
        this.world = world;
    }

    public void createCollisionBoxes() {
        int hallwayGap = 80; // Adjust the gap width as needed

        // Calculate the position of the hallway gap for both width and height
        int hallwayPositionX = (w - hallwayGap) / 2 + x;
        int hallwayPositionY = (h - hallwayGap) / 2 + y;

        BodyFactory bf = BodyFactory.getInstance(world);

        // Create collision boxes for the top wall
        if (NORTH != null) {
            bf.makeLine(x, y + h, hallwayPositionX, y + h, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Left side top wall
            bf.makeLine(hallwayPositionX + hallwayGap, y + h, x + w, y + h, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Right side top wall
        } else {
            bf.makeLine(x, y + h, x + w, y + h, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Full top wall
        }

        // Create collision boxes for the right wall
        if (EAST != null) {
            bf.makeLine(x + w, y, x + w, hallwayPositionY, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Top side wall
            bf.makeLine(x + w, hallwayPositionY + hallwayGap, x + w, y + h, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Bottom side wall
        } else {
            bf.makeLine(x + w, y, x + w, y + h, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Full right wall
        }

        // Create collision boxes for the bottom wall
        if (SOUTH != null) {
            bf.makeLine(x, y, hallwayPositionX, y, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Left side wall
            bf.makeLine(hallwayPositionX + hallwayGap, y, x + w, y, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Right side wall
        } else {
            bf.makeLine(x, y, x + w, y, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Full bottom wall
        }

        // Create collision boxes for the left wall
        if (WEST != null) {
            bf.makeLine(x, y, x, hallwayPositionY, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Top side wall
            bf.makeLine(x, hallwayPositionY + hallwayGap, x, y + h, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Bottom side wall
        } else {
            bf.makeLine(x, y, x, y + h, BodyDef.BodyType.StaticBody, BodyFactory.STATIC_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.ENEMY_BITS)); // Full left wall
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
        int enemyWidth = 32;
        int enemyHeight = 32;

        if (w >= enemyWidth && h >= enemyHeight) {
            int safeZoneX = Math.max(0, w - 2 * enemyWidth);
            int safeZoneY = Math.max(0, h - 2 * enemyHeight);

            for (int i = 0; i < numEnemies; i++) {
                int enemyX = x + enemyWidth + MathUtils.random(safeZoneX);
                int enemyY = y + enemyHeight + MathUtils.random(safeZoneY);

                ecsEngine.createEnemy(enemyX, enemyY, enemyWidth, enemyHeight, 1, 1.0f);
            }
        }
    }

    @Override
    public RoomType getRoomType() {
        return TYPE;
    }

    //public void draw(int x, int y, ShapeRenderer shape) {
    public void draw(ShapeRenderer shape) {
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
