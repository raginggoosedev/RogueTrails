package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

/**
 * Contains information about the entity's collisions
 */
public class CollisionComponent implements Component, Pool.Poolable {
    public Body body = null;
    public boolean collided = false;
    public float pushStrength = 0.0f;
    public Body collisionBody = null;
    public Vector2 collisionNormal = null;


    @Override
    public void reset() {
        body = null;
        collided = false;
        pushStrength = 0.0f;
        collisionBody = null;
        collisionNormal = null;
    }
}
