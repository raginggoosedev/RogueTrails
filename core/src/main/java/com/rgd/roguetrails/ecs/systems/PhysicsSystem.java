package com.rgd.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rgd.roguetrails.ecs.Mapper;
import com.rgd.roguetrails.ecs.components.B2DComponent;
import com.rgd.roguetrails.ecs.components.TransformComponent;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 2025-12-09 v1.0
 * System to handle translating entity physics data to rendering data; Matches transform component positional data to the Box2D body positional data
 */
public class PhysicsSystem extends IteratingSystem {
    private final Array<Entity> bodyQueue;

    public PhysicsSystem(World world) {
        super(Family.all(B2DComponent.class).get());
        bodyQueue = new Array<>();
    }


    @Override
    protected void processEntity(Entity entity, float v) {
        bodyQueue.add(entity);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // Iterate through all bodies
        for (Entity entity : new Array.ArrayIterator<>(bodyQueue)) {
            TransformComponent transformComponent = Mapper.TRANSFORM_MAPPER.get(entity);
            B2DComponent b2DComponent = Mapper.B2D_MAPPER.get(entity);
            Vector2 position = b2DComponent.body.getPosition();

            transformComponent.position.x = position.x;
            transformComponent.position.y = position.y;
            transformComponent.rotation = b2DComponent.body.getAngle() * MathUtils.radiansToDegrees;
        }

        bodyQueue.clear();
    }
}
