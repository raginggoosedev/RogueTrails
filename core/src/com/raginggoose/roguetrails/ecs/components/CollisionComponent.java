package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class CollisionComponent implements Component, Pool.Poolable {
    public Body body = null;
    public boolean collided = false;
    public float collisionOverlapX = 0.0f;
    public float collisionOverlapY = 0.0f;
    public float pushStrength = 0;


    @Override
    public void reset() {
        body = null;
        collided = false;
        collisionOverlapX = 0.0f;
        collisionOverlapY = 0.0f;
        pushStrength = 0;
    }
}
