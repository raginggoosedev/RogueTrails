package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.raginggoose.roguetrails.ecs.components.DebugComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

public class Mapper {
    public static final ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<DebugComponent> debugMapper = ComponentMapper.getFor(DebugComponent.class);
}
