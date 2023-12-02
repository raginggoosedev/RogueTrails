package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.raginggoose.roguetrails.ecs.components.*;

/**
 * Class containing static mappers for ECS components
 */
public class Mapper {

    // Component mappers for all the components available as statics
    public static final ComponentMapper<TransformComponent> TRANSFORM_MAPPER = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<DebugComponent> DEBUG_MAPPER = ComponentMapper.getFor(DebugComponent.class);
    public static final ComponentMapper<EnemyComponent> ENEMY_MAPPER = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<PlayerComponent> PLAYER_MAPPER = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<ItemComponent> ITEM_MAPPER = ComponentMapper.getFor(ItemComponent.class);
    public static final ComponentMapper<RenderComponent> RENDER_MAPPER = ComponentMapper.getFor(RenderComponent.class);
    public static final ComponentMapper<AnimationComponent> ANIMATION_MAPPER = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<StateComponent> STATE_MAPPER = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<CollisionComponent> COLLISION_MAPPER = ComponentMapper.getFor(CollisionComponent.class);
}
