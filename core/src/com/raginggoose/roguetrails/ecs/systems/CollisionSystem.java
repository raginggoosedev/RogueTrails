package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;
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
    protected void processEntity(Entity entity, float delta) {
        CollisionComponent collComp = Mapper.COLLISION_MAPPER.get(entity);

        PlayerComponent playerComp = Mapper.PLAYER_MAPPER.get(entity);
        if (playerComp != null && collComp.collisionBody != null) {
            if (collComp.collisionBody.getUserData() instanceof Entity) {
                System.out.println("Wow");
                if (Mapper.ENEMY_MAPPER.get((Entity) collComp.collisionBody.getUserData()) != null) {
                    collComp.pushStrength = 10.0f;
                    playerComp.health -= Mapper.ENEMY_MAPPER.get((Entity) collComp.collisionBody.getUserData()).damage;
                    System.out.println(collComp.pushStrength);
                }
            }
        }
    }
}
