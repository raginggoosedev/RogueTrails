package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

public class PhysicsSystem extends IteratingSystem {
    private final Array<Entity> bodyQueue;

    public PhysicsSystem(World world) {
        super(Family.all(CollisionComponent.class).get());
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
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);
            Vector2 position = collisionComponent.body.getPosition();

            transformComponent.position.x = position.x;
            transformComponent.position.y = position.y;
            transformComponent.rotation = collisionComponent.body.getAngle() * MathUtils.radiansToDegrees;
        }

        bodyQueue.clear();
    }
}
