package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.StateComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;
import com.raginggoose.roguetrails.room.Direction;

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
        TransformComponent transform = Mapper.TRANSFORM_MAPPER.get(entity);
        PlayerComponent playerComponent = Mapper.PLAYER_MAPPER.get(entity);
        CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);

        float speed = playerComponent.speed;
        final StateComponent stateComponent = stateMapper.get(entity);
        Vector3 pos = transform.position;
        Vector2 prevPos = transform.prevPosition;

        if (!collisionComponent.collided) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) pos.add(0, speed, 0);
            else if (Gdx.input.isKeyPressed(Input.Keys.S)) pos.add(0, -speed, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.A)) pos.add(-speed, 0, 0);
            else if (Gdx.input.isKeyPressed(Input.Keys.D)) pos.add(speed, 0, 0);
            stateComponent.setState(StateComponent.STATE_RIGHT);
        } else {
            Direction collisionDirection = collisionComponent.collisionBox.getCollisionDirection();
            float overlapX = collisionComponent.collisionOverlapX;
            float overlapY = collisionComponent.collisionOverlapY;

            // Revert to previous position
            pos.set(prevPos.x, prevPos.y, transform.position.z);

            // Adjust position based on collision direction
            if (collisionDirection.equals(Direction.RIGHT))
                pos.add(overlapX + 1, 0, 0);
            else if (collisionDirection.equals(Direction.LEFT))
                pos.add(-(overlapX + 1), 0, 0);
            else if (collisionDirection.equals(Direction.UP))
                pos.add(0, overlapY + 1, 0);
            else if (collisionDirection.equals(Direction.DOWN))
                pos.add(0, -(overlapY + 1), 0);

            collisionComponent.collided = false;
            collisionComponent.box.setCollision(false);
            collisionComponent.collisionBox = null;
        }

        dun.getCurrentRoom(transform.position.x, transform.position.y);

        transform.prevPosition.set(pos.x, pos.y);
        transform.position.set(pos);
    }
}
