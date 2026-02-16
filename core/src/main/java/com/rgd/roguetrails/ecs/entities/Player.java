package com.rgd.roguetrails.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.rgd.roguetrails.b2d.BodyFactory;
import com.rgd.roguetrails.ecs.components.B2DComponent;
import com.rgd.roguetrails.ecs.components.CollisionComponent;
import com.rgd.roguetrails.ecs.components.TransformComponent;
import com.rgd.roguetrails.utils.Constants;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2026/01/03
 * The player entity; Contains all ECS components used to make/manage the player
 */
public class Player extends Entity {

    private final TransformComponent transformComponent;
    private final B2DComponent b2DComponent;
    private final CollisionComponent collisionComponent;

    public Player(World world, float posX, float posY) {
        transformComponent = new TransformComponent();
        transformComponent.position.set(posX, posY, 0);
        transformComponent.scale.set(1, 1);
        transformComponent.rotation = 0;
        this.add(transformComponent);

        b2DComponent = new B2DComponent();
        b2DComponent.body = BodyFactory.getInstance(world).makeBox(posX, posY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, BodyDef.BodyType.DynamicBody);
        this.add(b2DComponent);

        collisionComponent = new CollisionComponent();
        this.add(collisionComponent);
    }
}
