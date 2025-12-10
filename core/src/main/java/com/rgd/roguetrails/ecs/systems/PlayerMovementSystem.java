package com.rgd.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.rgd.roguetrails.ecs.Mapper;
import com.rgd.roguetrails.ecs.components.B2DComponent;
import com.rgd.roguetrails.ecs.components.TransformComponent;

public class PlayerMovementSystem extends IteratingSystem {
    private final Vector2 force;

    public PlayerMovementSystem() {
        super(Family.all(TransformComponent.class, B2DComponent.class).get());
        force = new Vector2();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2DComponent b2dComponent = Mapper.B2D_MAPPER.get(entity);
        b2dComponent.body.setLinearVelocity(Vector2.Zero);
        force.set(0, 0);

        float speed = 4.0f;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            force.add(-1, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            force.add(1, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            force.add(0, 1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            force.add(0, -1);
        }

        b2dComponent.body.setLinearVelocity(force.scl(speed));
    }
}
