package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;
import com.raginggoose.roguetrails.ecs.components.ItemComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;
import com.raginggoose.roguetrails.input.GameInputListener;
import com.raginggoose.roguetrails.input.GameKeys;
import com.raginggoose.roguetrails.input.InputManager;

public class InteractionSystem extends IteratingSystem implements GameInputListener {
    private final Entity player;
    private boolean pressed;
    private final InputManager inputManager;

    public InteractionSystem(Entity player, InputManager inputManager) {
        super(Family.all(ItemComponent.class).get());
        this.inputManager = inputManager;
        this.player = player;
        pressed = false;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ItemComponent itemComponent = Mapper.ITEM_MAPPER.get(entity);
        CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);

        if (!itemComponent.collected && collisionComponent.collisionBody != null) {
            if (collisionComponent.collisionBody.getUserData() == player) {
                if (pressed) {
                    Mapper.PLAYER_MAPPER.get(player).inventory.addItem(entity);
                    itemComponent.collected = true;

                    if (Mapper.RENDER_MAPPER.get(entity) != null) {
                        Mapper.RENDER_MAPPER.get(entity).shouldRender = false;
                    }

                    collisionComponent.body.getWorld().destroyBody(collisionComponent.body);
                    entity.remove(CollisionComponent.class);

                    CollisionComponent playerCollComp = Mapper.COLLISION_MAPPER.get(player);
                    playerCollComp.collisionBody = null;
                    playerCollComp.collided = false;
                }
            }
        }
    }

    @Override
    public void keyPressed(InputManager inputManager, GameKeys gameKey) {
        if (gameKey == GameKeys.INTERACT)
            pressed = true;
    }

    @Override
    public void keyUp(InputManager inputManager, GameKeys gameKey) {
        if (gameKey == GameKeys.INTERACT)
            pressed = false;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if (buttonCode == controller.getMapping().buttonX)
            pressed = true;

        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if (buttonCode == controller.getMapping().buttonX && !inputManager.isKeyPressed(GameKeys.INTERACT))
            pressed = false;

        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }
}
