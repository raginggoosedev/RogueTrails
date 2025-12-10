package com.rgd.roguetrails.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.rgd.roguetrails.ecs.components.*;

/**
 * Class containing static mappers for ECS components
 */
public class Mapper {

    // Component mappers for all the components available as statics
    public static final ComponentMapper<TransformComponent> TRANSFORM_MAPPER = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<B2DComponent> B2D_MAPPER = ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<CollisionComponent> COLLISION_MAPPER = ComponentMapper.getFor(CollisionComponent.class);
}
