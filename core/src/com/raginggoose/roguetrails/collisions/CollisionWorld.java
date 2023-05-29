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
        for (CollisionBox a : world) {
            for (CollisionBox b : world) {
                if (!a.equals(b)) {
                    Vector2 posA = a.getPosition();
                    Vector2 posB = b.getPosition();

                    if (!a.isStatic() && !b.isStatic()) {
                        if (posA.x < posB.x + b.getWidth() && posA.x + a.getWidth() > posB.x && posA.y < posB.y + b.getHeight() && posA.y + a.getHeight() > posB.y) {
                            // Entity on entity collisions
                            a.setCollision(true);
                            CollisionComponent collA = Mapper.COLLISION_MAPPER.get(a.getEntity());
                            collA.collisionBox = b;
                            collA.collided = true;

                            b.setCollision(true);
                            CollisionComponent collB = Mapper.COLLISION_MAPPER.get(b.getEntity());
                            collB.collisionBox = a;
                            collB.collided = true;
                        }
                    } else if (dungeon != null) {
                        if (!a.isStatic() && b.getRoom().equals(dungeon.getCurrentRoom(posA.x, posA.y))) {
                            if ((posA.x > posB.x + b.getWidth() || posA.x + a.getWidth() < posB.x) && (posA.y > posB.y + b.getHeight() || posA.y + a.getHeight() < posB.y)) {
                                // Box A is an entity colliding into the static box B
                                CollisionComponent collA = Mapper.COLLISION_MAPPER.get(a.getEntity());
                                a.setCollision(true);
                                collA.collisionBox = b;
                                collA.collided = true;
                            }
                        } else if (!b.isStatic() && a.getRoom().equals(dungeon.getCurrentRoom(posB.x, posB.y))) {
                            if ((posB.x > posA.x + a.getWidth() || posB.x + b.getWidth() < posA.x) && (posB.y > posA.y + a.getHeight() || posB.y + b.getHeight() < posA.y)) {
                                // Box B is an entity colling into the static box A
                                CollisionComponent collB = Mapper.COLLISION_MAPPER.get(b.getEntity());
                                collB.collisionBox = a;
                                collB.collided = true;
                            }
                        }
                    }
                }
            }
        }

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
