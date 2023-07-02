package com.raginggoose.roguetrails.collisions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.room.Direction;
import com.raginggoose.roguetrails.room.Room;

/**
 * Creates a box around an entity or a static location for handling collisions
 */
public class CollisionBox {
    private final Vector2 pos;
    private final float w, h;
    private final Entity entity;
    private final Room room;
    private boolean isStatic;
    private boolean hasCollided = false;
    private Direction collisionDirection;

    /**
     * Constructor for static world collision boxes
     * @param pos of the box
     * @param width of the box
     * @param height of the box
     * @param room The room that this collision box is for
     */
    public CollisionBox(Vector2 pos, float width, float height, Room room) {
        this.pos = pos;
        w = width;
        h = height;
        isStatic = true;
        entity = null;
        this.room = room;
    }

    /**
     * Constructor for entity collision boxes
     * @param pos of the box
     * @param width of the box
     * @param height of the box
     * @param entity The entity that this box is for
     */
    public CollisionBox(Vector2 pos, float width, float height, Entity entity) {
        this.pos = pos;
        w = width;
        h = height;
        this.entity = entity;
        isStatic = false;
        room = null;
    }

    public boolean hasCollided() {
        return hasCollided;
    }

    public void setCollision(boolean collision) {
        hasCollided = collision;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public Entity getEntity() {
        return entity;
    }

    public Room getRoom() {
        return room;
    }

    public Vector2 getPosition() {
        return pos;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void updatePosition(Vector2 pos) {
        this.pos.set(pos);
    }

    public float getCenterX() {
        return pos.x + (w / 2);
    }

    public float getCenterY() {
        return pos.y + (h / 2);
    }

    public void setCollisionDirection(Direction dir) {
        collisionDirection = dir;
    }

    public Direction getCollisionDirection() {
        return collisionDirection;
    }
}
