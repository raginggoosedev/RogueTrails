package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.raginggoose.roguetrails.ecs.Mapper;
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
        TransformComponent playerTransform = Mapper.TRANSFORM_MAPPER.get(player);
        TransformComponent itemTransform = Mapper.TRANSFORM_MAPPER.get(entity);
        ItemComponent itemComponent = Mapper.ITEM_MAPPER.get(entity);

        if (!itemComponent.collected) {
            Vector3 playerPos = playerTransform.position;
            Vector3 itemPos = itemTransform.position;

            if (playerPos.x < itemPos.x + itemTransform.width && playerPos.x + playerTransform.width > itemPos.x && playerPos.y < itemPos.y + itemTransform.height && playerPos.y + playerTransform.height > itemPos.y) {
                if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                    Mapper.PLAYER_MAPPER.get(player).inventory.addItem(entity);
                    itemComponent.collected = true;

                    if (Mapper.RENDER_MAPPER.get(entity) != null) {
                        Mapper.RENDER_MAPPER.get(entity).shouldRender = false;
                    }
                }
            }
        }
    }
}
