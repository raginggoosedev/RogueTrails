package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.raginggoose.roguetrails.ecs.components.*;

public class Mapper {
    public static final ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<DebugComponent> debugMapper = ComponentMapper.getFor(DebugComponent.class);
    public static final ComponentMapper<EnemyComponent> enemyMapper = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<AnimationComponent> animationMapper = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<StateComponent> stateMapper = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor((TextureComponent.class));
}
