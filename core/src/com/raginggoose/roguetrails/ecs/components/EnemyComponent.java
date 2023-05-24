package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public Entity player = null;
    public float speed = 0.0f;
    public float health = 0.0f;

    @Override
    public void reset() {
        player = null;
        speed = 0.0f;
        health = 0.0f;
    }
}
