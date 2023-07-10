package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.Animations;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.*;

/**
 * The system used to move the player based on user input
 */
public class PlayerMovementSystem extends IteratingSystem {
    private final Dungeon dun;
    private final ComponentMapper<StateComponent> stateMapper;

    public PlayerMovementSystem(Dungeon dun) {
        super(Family.all(PlayerComponent.class, TransformComponent.class, CollisionComponent.class).get());
        this.dun = dun;
        stateMapper = Mapper.STATE_MAPPER;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = Mapper.PLAYER_MAPPER.get(entity);
        CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);

        float speed = playerComponent.speed;
        final StateComponent stateComponent = stateMapper.get(entity);
        Vector2 force = new Vector2(0, 0);


        EnemyComponent enemyComponent = null;
        if (collisionComponent.collisionBody != null) {
            if (collisionComponent.collisionBody.getUserData() instanceof Entity) {
                enemyComponent = Mapper.ENEMY_MAPPER.get((Entity) collisionComponent.collisionBody.getUserData());
            }
        }


        if (enemyComponent == null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                force.y = speed;
                stateComponent.isLooping = true;
                stateComponent.setState(StateComponent.STATE_UP);
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                force.y = -speed;
                stateComponent.isLooping = true;
                stateComponent.setState(StateComponent.STATE_DOWN);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                force.x = -speed;
                stateComponent.isLooping = true;
                stateComponent.setState(StateComponent.STATE_LEFT);
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                force.x = speed;
                stateComponent.isLooping = true;
                stateComponent.setState(StateComponent.STATE_RIGHT);
            } else {
                stateComponent.setState(StateComponent.STATE_STOP);
                stateComponent.isLooping = false;
                Animations.frameRate = 0f;
            }

            collisionComponent.body.setLinearVelocity(force.x, force.y);

        } else {
            Vector2 pushDirection = collisionComponent.collisionNormal.cpy();
            Vector2 pushImpulse = pushDirection.scl(collisionComponent.pushStrength);
            collisionComponent.body.applyLinearImpulse(pushImpulse, collisionComponent.body.getWorldCenter(), true);
            collisionComponent.collisionBody = null;
        }

    }
}
