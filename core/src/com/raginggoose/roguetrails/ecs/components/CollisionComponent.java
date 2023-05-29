package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.raginggoose.roguetrails.collisions.CollisionBox;

public class CollisionComponent implements Component, Pool.Poolable {
    public CollisionBox collisionBox = null;
    public CollisionBox box = null;
    public boolean collided = false;

    @Override
    public void reset() {
        collisionBox = null;
        collided = false;
        box = null;
    }
}
