package com.rgd.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rgd.roguetrails.ecs.Mapper;
import com.rgd.roguetrails.ecs.components.TransformComponent;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 2025-12-09, v1.0
 * System to set the camera to follow the player's position
 */
public class PlayerCameraSystem extends IteratingSystem {
    private final OrthographicCamera camera;

    public PlayerCameraSystem(OrthographicCamera camera) {
        super(Family.all(TransformComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mapper.TRANSFORM_MAPPER.get(entity);

        // Set the camera's position to the player's position and update it
        camera.position.x = transformComponent.position.x;
        camera.position.y = transformComponent.position.y;
        camera.update();
    }
}
