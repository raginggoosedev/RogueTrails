package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class MeleeComponent implements Component, Pool.Poolable {
    public float speed = 0.0f;
    public float damage = 0.0f;
    public float range = 0.0f;
    public float coolDown = 0.0f;

    @Override
    public void reset() {
        speed = 0.0f;
        damage = 0.0f;
        range = 0.0f;
        coolDown = 0.0f;
    }
}
