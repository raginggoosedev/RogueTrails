package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable {
    public Vector3 position = new Vector3();
    public Vector2 prevPosition = new Vector2();
    public Vector2 scale = new Vector2(1.0f, 1.0f);
    public float width = 0.0f;
    public float height = 0.0f;
    public float rotation = 0.0f;

    @Override
    public void reset() {
        rotation = 0.0f;
        scale.set(1.0f, 1.0f);
        position.set(0.0f, 0.0f, 0.0f);
        prevPosition.set(0.0f, 0.0f);
        width = height = 0.0f;
    }
}
