package com.raginggoose.roguetrails.collisions;

import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;
import com.raginggoose.roguetrails.room.Direction;

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
                // Entity-entity collision
                if (posA.x < posB.x + boxB.getWidth() && posA.x + boxA.getWidth() > posB.x &&
                        posA.y < posB.y + boxB.getHeight() && posA.y + boxA.getHeight() > posB.y) {
                    handleEntityCollision(boxA, boxB);
                }
            } else if (dungeon != null && dungeon.getCurrentRoom(boxA.getPosition().x, boxA.getPosition().y) != null && dungeon.getCurrentRoom(boxB.getPosition().x, boxB.getPosition().y) != null) {
                // Static-entity collision
                if (boxA.isStatic() && !boxB.isStatic() && dungeon.getCurrentRoom(boxB.getPosition().x, boxB.getPosition().y).equals(boxA.getRoom())) {
                    // Check if the non-static box touches the static walls
                    if (posB.x <= posA.x + boxA.getWidth() && posB.x + boxB.getWidth() >= posA.x &&
                            posB.y <= posA.y + boxA.getHeight() && posB.y + boxB.getHeight() >= posA.y) {
                        handleStaticCollision(boxB, boxA);
                    }
                } else if (!boxA.isStatic() && boxB.isStatic() && dungeon.getCurrentRoom(boxA.getPosition().x, boxA.getPosition().y).equals(boxB.getRoom())) {
                    // Check if the non-static box touches the static walls
                    if (posA.x < posB.x + boxB.getWidth() && posA.x + boxA.getWidth() > posB.x &&
                            posA.y < posB.y + boxB.getHeight() && posA.y + boxA.getHeight() > posB.y) {
                        handleStaticCollision(boxA, boxB);
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

        float overlapX = Math.min(boxA.getPosition().x + boxB.getWidth(), boxA.getPosition().x + boxB.getWidth()) -
                Math.max(boxA.getPosition().x, boxB.getPosition().x);
        float overlapY = Math.min(boxA.getPosition().y + boxA.getHeight(), boxB.getPosition().y + boxB.getHeight()) -
                Math.max(boxA.getPosition().y, boxB.getPosition().y);

        if (overlapX < overlapY) {
            // Horizontal collision
            if (boxA.getCenterX() < boxB.getCenterX()) {
                boxA.setCollisionDirection(Direction.RIGHT);
                boxB.setCollisionDirection(Direction.LEFT);
            } else {
                boxA.setCollisionDirection(Direction.LEFT);
                boxB.setCollisionDirection(Direction.RIGHT);
            }
        } else {
            // Vertical collision
            if (boxA.getCenterY() < boxB.getCenterY()) {
                boxA.setCollisionDirection(Direction.UP);
                boxB.setCollisionDirection(Direction.DOWN);
            } else {
                boxA.setCollisionDirection(Direction.DOWN);
                boxB.setCollisionDirection(Direction.UP);
            }
        }
    }

    private void handleStaticCollision(CollisionBox entityBox, CollisionBox staticBox) {
        System.out.println("YOU STUPID NI-");
        float overlapX = Math.min(entityBox.getPosition().x + entityBox.getWidth(), staticBox.getPosition().x + staticBox.getWidth()) -
                Math.max(entityBox.getPosition().x, staticBox.getPosition().x);
        float overlapY = Math.min(entityBox.getPosition().y + entityBox.getHeight(), staticBox.getPosition().y + staticBox.getHeight()) -
                Math.max(entityBox.getPosition().y, staticBox.getPosition().y);

        if (overlapX < overlapY) {
            // Horizontal collision
            if (entityBox.getCenterX() < staticBox.getCenterX()) {
                entityBox.setCollisionDirection(Direction.RIGHT);
                staticBox.setCollisionDirection(Direction.LEFT);
            } else {
                entityBox.setCollisionDirection(Direction.LEFT);
                staticBox.setCollisionDirection(Direction.RIGHT);
            }
        } else {
            // Vertical collision
            if (entityBox.getCenterY() < staticBox.getCenterY()) {
                entityBox.setCollisionDirection(Direction.UP);
                staticBox.setCollisionDirection(Direction.DOWN);
            } else {
                entityBox.setCollisionDirection(Direction.DOWN);
                staticBox.setCollisionDirection(Direction.UP);
            }
        }


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

    public void removeCollisionBox(CollisionBox box) {
        world.remove(box);
    }
}
