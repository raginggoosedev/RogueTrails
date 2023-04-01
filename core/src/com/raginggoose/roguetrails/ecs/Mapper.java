package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.raginggoose.roguetrails.ecs.components.*;

public class Mapper {
    // Component mappers for all the components available as statics
    public static final ComponentMapper<TransformComponent> TRANSFORM_MAPPER = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<DebugComponent> DEBUG_MAPPER = ComponentMapper.getFor(DebugComponent.class);
    public static final ComponentMapper<EnemyComponent> ENEMY_MAPPER = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<PlayerComponent> PLAYER_MAPPER = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<ItemComponent> ITEM_MAPPER = ComponentMapper.getFor(ItemComponent.class);
    public static final ComponentMapper<RenderComponent> RENDER_MAPPER = ComponentMapper.getFor(RenderComponent.class);
}
