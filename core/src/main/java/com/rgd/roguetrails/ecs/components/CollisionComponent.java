package com.rgd.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2025/12/07
 * A component containing the physics collision data of an entity
 */
public class CollisionComponent implements Pool.Poolable, Component {
    public Entity collisionEntity;

    @Override
    public void reset() {
        collisionEntity = null;
    }
}
