package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.raginggoose.roguetrails.ecs.components.DebugComponent;
import com.raginggoose.roguetrails.ecs.components.EnemyComponent;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

public class Mapper {
    // Component mappers for all the components available as statics
    public static final ComponentMapper<TransformComponent> TRANSFORM_MAPPER = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<DebugComponent> DEBUG_MAPPER = ComponentMapper.getFor(DebugComponent.class);
    public static final ComponentMapper<EnemyComponent> ENEMY_MAPPER = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<PlayerComponent> PLAYER_MAPPER = ComponentMapper.getFor(PlayerComponent.class);
}
