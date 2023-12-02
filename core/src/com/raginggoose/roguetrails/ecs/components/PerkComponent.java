package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Contains information about an entity's current perks
 */
public class PerkComponent implements Component, Pool.Poolable {
    public float timeLimit = 0.0f;

    @Override
    public void reset() {
        timeLimit = 0.0f;
    }
}
