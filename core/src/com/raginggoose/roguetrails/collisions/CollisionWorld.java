package com.raginggoose.roguetrails.collisions;

import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;

import java.util.ArrayList;

/**
 * Creates a world for handling collisions within the game
 */
public class CollisionWorld {
    private final ArrayList<CollisionBox> world;
    private Dungeon dungeon;

    public CollisionWorld() {
        world = new ArrayList<>();
    }

    /**
     * Updates the world, checking for any collisions between collision boxes
     */
    public void update() {
        for (int i = 0; i < world.size(); i++) {
            CollisionBox boxA = world.get(i);
            for (int j = i + 1; j < world.size(); j++) {
                CollisionBox boxB = world.get(j);
                checkCollision(boxA, boxB);
            }
        }
    }

    private void checkCollision(CollisionBox boxA, CollisionBox boxB) {
        if (!boxA.equals(boxB)) {
            Vector2 posA = boxA.getPosition();
            Vector2 posB = boxB.getPosition();

            if (!boxA.isStatic() && !boxB.isStatic()) {
                if (posA.x < posB.x + boxB.getWidth() && posA.x + boxA.getWidth() > posB.x &&
                        posA.y < posB.y + boxB.getHeight() && posA.y + boxA.getHeight() > posB.y) {
                    handleEntityCollision(boxA, boxB);
                }
            } else if (dungeon != null) {
                if (!boxA.isStatic() && boxB.getRoom().equals(dungeon.getCurrentRoom(posA.x, posA.y))) {
                    if ((posA.x > posB.x + boxB.getWidth() || posA.x + boxA.getWidth() < posB.x) &&
                            (posA.y > posB.y + boxB.getHeight() || posA.y + boxA.getHeight() < posB.y)) {
                        handleStaticCollision(boxA, boxB);
                    }
                } else if (!boxB.isStatic() && boxA.getRoom().equals(dungeon.getCurrentRoom(posB.x, posB.y))) {
                    if ((posB.x > posA.x + boxA.getWidth() || posB.x + boxB.getWidth() < posA.x) &&
                            (posB.y > posA.y + boxA.getHeight() || posB.y + boxB.getHeight() < posA.y)) {
                        handleStaticCollision(boxB, boxA);
                    }
                }
            }
        }
    }

    private void handleEntityCollision(CollisionBox boxA, CollisionBox boxB) {
        boxA.setCollision(true);
        CollisionComponent collA = Mapper.COLLISION_MAPPER.get(boxA.getEntity());
        collA.collisionBox = boxB;
        collA.collided = true;

        boxB.setCollision(true);
        CollisionComponent collB = Mapper.COLLISION_MAPPER.get(boxB.getEntity());
        collB.collisionBox = boxA;
        collB.collided = true;
    }

    private void handleStaticCollision(CollisionBox entityBox, CollisionBox staticBox) {
        CollisionComponent coll = Mapper.COLLISION_MAPPER.get(entityBox.getEntity());
        coll.collisionBox = staticBox;
        coll.collided = true;
    }

    public void addCollisionBox(CollisionBox newBox) {
        world.add(newBox);
    }

    public void clearWorld() {
        world.clear();
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }
}
