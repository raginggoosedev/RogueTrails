package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;
import com.raginggoose.roguetrails.ecs.components.ItemComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

public class InteractionSystem extends IteratingSystem {
    private final Entity player;

    public InteractionSystem(Entity player) {
        super(Family.all(ItemComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ItemComponent itemComponent = Mapper.ITEM_MAPPER.get(entity);
        CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);

        if (!itemComponent.collected && collisionComponent.collisionBody != null) {
            if (collisionComponent.collisionBody.getUserData() == player) {
                if (Gdx.input.isKeyPressed(Input.Keys.E)) {
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
}
