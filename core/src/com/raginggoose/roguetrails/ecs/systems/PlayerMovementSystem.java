package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.math.Vector2;
import com.raginggoose.roguetrails.ecs.Animations;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.*;
import com.raginggoose.roguetrails.input.GameInputListener;
import com.raginggoose.roguetrails.input.GameKeys;
import com.raginggoose.roguetrails.input.InputManager;

/**
 * The system used to move the player based on user input
 */
public class PlayerMovementSystem extends IteratingSystem implements GameInputListener {
    private final ComponentMapper<StateComponent> stateMapper;
    private final Vector2 force;

    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class, TransformComponent.class, CollisionComponent.class).get());
        stateMapper = Mapper.STATE_MAPPER;

        force = new Vector2();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = Mapper.PLAYER_MAPPER.get(entity);
        CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);

        float speed = playerComponent.speed;
        final StateComponent stateComponent = stateMapper.get(entity);

        if (collisionComponent.pushStrength <= 0.0f) {
            if (force.y > 0) {
                stateComponent.isLooping = true;
                stateComponent.setState(StateComponent.STATE_UP);
            } else if (force.y < 0) {
                stateComponent.isLooping = true;
                stateComponent.setState(StateComponent.STATE_DOWN);
            }

            if (force.x < 0) {
                stateComponent.isLooping = true;
                stateComponent.setState(StateComponent.STATE_LEFT);
            } else if (force.x > 0) {
                stateComponent.isLooping = true;
                stateComponent.setState(StateComponent.STATE_RIGHT);
            }

            if (force.isZero()) {
                stateComponent.setState(StateComponent.STATE_STOP);
                stateComponent.isLooping = false;
                Animations.frameRate = 0f;
            }

            collisionComponent.body.setLinearVelocity(force.x * speed, force.y * speed);

        } else {
            Vector2 pushDirection = collisionComponent.collisionNormal.cpy();
            Vector2 pushImpulse = pushDirection.scl(-collisionComponent.pushStrength);
            collisionComponent.body.setLinearVelocity(pushImpulse);
            collisionComponent.pushStrength -= 0.5f;
            collisionComponent.collisionBody = null;
        }
    }

    @Override
    public void keyPressed(InputManager inputManager, GameKeys gameKey) {
        switch (gameKey) {
            case UP:
                force.y = 1.0f;
                break;
            case DOWN:
                force.y = -1.0f;
                break;
            case LEFT:
                force.x = -1.0f;
                break;
            case RIGHT:
                force.x = 1.0f;
        }
    }

    @Override
    public void keyUp(InputManager inputManager, GameKeys gameKey) {
        switch (gameKey) {
            case UP:
                force.y = inputManager.isKeyPressed(GameKeys.DOWN) ? -1 : 0;
                break;
            case DOWN:
                force.y = inputManager.isKeyPressed(GameKeys.UP) ? 1 : 0;
                break;
            case LEFT:
                force.x = inputManager.isKeyPressed(GameKeys.RIGHT) ? 1 : 0;
                break;
            case RIGHT:
                force.x = inputManager.isKeyPressed(GameKeys.LEFT) ? -1 : 0;
        }
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        float deadzone = 0.25f;
        ControllerMapping mapping = controller.getMapping();
        if (axisCode == mapping.axisLeftX) {
            // Checks to see if the absolute value is in the deadzone
            if (Math.abs(value) < deadzone) {
                value = 0.0f;
            }
            force.x = value;
        }
        if (axisCode == mapping.axisLeftY) {
            if (Math.abs(value) < deadzone) {
                value = 0.0f;
            }
            force.y = -value;
        }
        return false;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }


}
