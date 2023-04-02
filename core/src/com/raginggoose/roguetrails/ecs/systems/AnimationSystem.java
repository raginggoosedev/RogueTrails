package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.*;

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
        final AnimationComponent animationComponent = animationMapper.get(entity);
        final StateComponent stateComponent = stateMapper.get(entity);
        RenderComponent renderComponent = renderMapper.get(entity);
        //textureComponent.region = animationComponent.animations.get(stateComponent.getState()).getKeyFrame(stateComponent.time, stateComponent.isLooping);
    }
}
