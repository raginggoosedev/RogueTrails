package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;

/**
 * Contains information about the entity's animations
 */
public class AnimationComponent implements Component, Pool.Poolable {
    public IntMap<Animation<TextureRegion>> animations = new IntMap<>();
    public IntMap<TextureAtlas.AtlasRegion> atlas = new IntMap<>();
    @Override
    public void reset() {
        animations = new IntMap<>();
    }
}
