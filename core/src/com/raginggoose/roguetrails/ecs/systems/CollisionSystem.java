package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;
import com.raginggoose.roguetrails.ecs.components.EnemyComponent;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

/**
 * Handles collisions involving one or more entities
 */
public class CollisionSystem extends IteratingSystem {

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        TransformComponent transformComp = Mapper.TRANSFORM_MAPPER.get(entity);
        CollisionComponent collComp = Mapper.COLLISION_MAPPER.get(entity);

        // Update entity's collision box position
        Vector2 pos = new Vector2(transformComp.position.x, transformComp.position.y);
        collComp.box.updatePosition(pos);

        PlayerComponent playerComp = Mapper.PLAYER_MAPPER.get(entity);
        if (playerComp != null) {
            // Handle player entity's collisions
            if (collComp.collided && collComp.collisionBox.getEntity() != null) {
                if (Mapper.ENEMY_MAPPER.get(collComp.collisionBox.getEntity()) != null) {
                    // Get enemy information from collided entity
                    EnemyComponent enemyComp = Mapper.ENEMY_MAPPER.get(collComp.collisionBox.getEntity());
                    playerComp.health -= enemyComp.damage;
                }
            } else if (collComp.collided) {
                // Player collided with static boundary
                System.out.println("HIT A WALL!");

            }
        }

        // Reset the entity's collision component
        collComp.collided = false;
        collComp.box.setCollision(false);
        collComp.collisionBox = null;
    }
}
