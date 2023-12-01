package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool;

/**
 * Contains debug information about the entity
 */
public class DebugComponent implements Component, Pool.Poolable {
    public Color color = Color.WHITE;

    @Override
    public void reset() {
        color = Color.WHITE;
    }
}
