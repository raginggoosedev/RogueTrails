package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

/**
 * A system used to make the camera follow the player's movement
 */
public class PlayerCameraSystem extends IteratingSystem {
    private final OrthographicCamera cam;

    /**
     * Creates a new camera system that uses the given game camera
     *
     * @param cam the camera that will follow the player
     */
    public PlayerCameraSystem(OrthographicCamera cam) {
        super(Family.all(PlayerComponent.class).get());
        this.cam = cam;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mapper.TRANSFORM_MAPPER.get(entity);

        // Set the cameras position values equal to the player's values
        cam.position.x = transformComponent.position.x;
        cam.position.y = transformComponent.position.y;

        // Set the camera to the new position by updating it
        cam.update();
    }
}
