package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public float speed = 0.0f;

    @Override
    public void reset() {
        speed = 0.0f;
    }
}
