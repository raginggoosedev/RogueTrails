package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;
import com.raginggoose.roguetrails.ecs.components.EnemyComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;
import com.raginggoose.roguetrails.room.Direction;
import com.raginggoose.roguetrails.room.Room;

/**
 * The system used to make the enemy entities move towards the player
 */
public class EnemyMovementSystem extends IteratingSystem {
    public EnemyMovementSystem() {
        super(Family.all(EnemyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mapper.TRANSFORM_MAPPER.get(entity);
        EnemyComponent enemyComponent = Mapper.ENEMY_MAPPER.get(entity);

        // Get the player and its transform component
        Entity player = enemyComponent.player;
        TransformComponent playerTransformComponent = Mapper.TRANSFORM_MAPPER.get(player);

        Vector3 enemyPos = transformComponent.position;
        Vector3 playerPos = playerTransformComponent.position;
        Vector2 force = new Vector2(0, 0);

        float speed = enemyComponent.speed;

        // Find the difference between the enemy and player positions
        float diffX = playerPos.x - enemyPos.x;
        float diffY = playerPos.y - enemyPos.y;

        // Find the angle of the difference x and y
        float angle = MathUtils.atan2(diffY, diffX);

        CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);

        boolean hittingPlayer = false;

        if (collisionComponent.collided && collisionComponent.collisionBody != null)
            if (collisionComponent.collisionBody.getUserData() == player)
                hittingPlayer = true;

        if (!hittingPlayer) {
            if (diffX < -1 || diffX > 1) force.x = speed * MathUtils.cos(angle);
            if (diffY < -1 || diffY > 1) force.y = speed * MathUtils.sin(angle);
        }

        if (collisionComponent.pushStrength <= 0.0f)
            collisionComponent.body.setLinearVelocity(force.x, force.y);
        else {
            Vector2 pushDirection = collisionComponent.collisionNormal.cpy();
            Vector2 pushImpulse = pushDirection.scl(-collisionComponent.pushStrength);
            collisionComponent.body.setLinearVelocity(pushImpulse);
            collisionComponent.pushStrength -= 0.5f;
            collisionComponent.collisionBody = null;
        }
    }
}
