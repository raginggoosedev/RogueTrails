package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.raginggoose.roguetrails.inventory.Inventory;

/**
 * Stores variables for the player and is used to identify the player entity in systems
 */
public class PlayerComponent implements Component, Pool.Poolable {
    public float speed = 0.0f;
    public Inventory inventory = null;

    @Override
    public void reset() {
        speed = 0.0f;
        inventory = null;
    }
}
