package com.rgd.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2025/12/07
 * A component containing the transformation and positional data of an entity
 */
public class TransformComponent implements Pool.Poolable, Component {
    public final Vector3 position = new Vector3();
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;

    @Override
    public void reset() {
        position.set(0, 0, 0);
        scale.set(1.0f, 1.0f);
        rotation = 0.0f;
    }
}
