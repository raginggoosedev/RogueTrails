package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Contains information about an entity's ranged capabilities
 */
public class RangedComponent implements Component, Pool.Poolable {
    public float speed = 0.0f;
    public float damage = 0.0f;
    public float coolDown = 0.0f;

    @Override
    public void reset() {
        speed = 0.0f;
        damage = 0.0f;
        coolDown = 0.0f;
    }
}
