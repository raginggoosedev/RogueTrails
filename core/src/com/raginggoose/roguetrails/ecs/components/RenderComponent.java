package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Contains information about how an entity should be rendered
 */
public class RenderComponent implements Component, Pool.Poolable {
    public TextureRegion region = null;
    public boolean shouldRender = false;

    @Override
    public void reset() {
        shouldRender = false;
        region = null;
    }
}
