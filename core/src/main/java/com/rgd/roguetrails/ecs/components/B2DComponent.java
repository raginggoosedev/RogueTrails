package com.rgd.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2025/12/07
 * A component containing the physics body data of an entity
 */
public class B2DComponent implements Pool.Poolable, Component {
    public Body body;

    @Override
    public void reset() {
        body = null;
    }
}
