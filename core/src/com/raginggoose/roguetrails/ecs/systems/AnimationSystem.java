package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.AnimationComponent;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.RenderComponent;
import com.raginggoose.roguetrails.ecs.components.StateComponent;

public class AnimationSystem extends IteratingSystem {

    private final ComponentMapper<AnimationComponent> animationMapper;
    private final ComponentMapper<StateComponent> stateMapper;
    private final ComponentMapper<RenderComponent> renderMapper;

    public AnimationSystem() {
        super(Family.all(PlayerComponent.class).get());
        animationMapper = Mapper.ANIMATION_MAPPER;
        stateMapper = Mapper.STATE_MAPPER;
        renderMapper = Mapper.RENDER_MAPPER;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = animationMapper.get(entity);
        StateComponent stateComponent = stateMapper.get(entity);
        RenderComponent renderComponent = renderMapper.get(entity);
        renderComponent.region = animationComponent.animations.get(stateComponent.getState()).getKeyFrame(stateComponent.time, stateComponent.isLooping);
        stateComponent.time += deltaTime;
    }
}
