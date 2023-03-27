package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

public class PlayerCameraSystem extends IteratingSystem {
    private final OrthographicCamera cam;

    public PlayerCameraSystem(OrthographicCamera cam) {
        super(Family.all(PlayerComponent.class).get());
        this.cam = cam;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mapper.transformMapper.get(entity);

        cam.position.x = transformComponent.position.x;
        cam.position.y = transformComponent.position.y;

        cam.update();
    }
}
