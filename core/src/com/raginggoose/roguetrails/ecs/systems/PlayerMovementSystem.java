package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

public class PlayerMovementSystem extends IteratingSystem {

    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mapper.transformMapper.get(entity);

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            transform.position.add(0, 1, 0);
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            transform.position.add(0, -1, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            transform.position.add(-1, 0, 0);
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            transform.position.add(1, 0, 0);
    }
}
