package com.rgd.roguetrails.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.rgd.roguetrails.b2d.BodyFactory;
import com.rgd.roguetrails.ecs.components.B2DComponent;
import com.rgd.roguetrails.ecs.components.CollisionComponent;
import com.rgd.roguetrails.ecs.components.TransformComponent;

public class Player extends Entity {
    private static final float PLAYER_WIDTH = 32;
    private static final float PLAYER_HEGIHT = 32;

    private final TransformComponent transformComponent;
    private final B2DComponent b2DComponent;
    private final CollisionComponent collisionComponent;

    public Player(World world, float posX, float posY) {
        transformComponent = new TransformComponent();
        transformComponent.position.set(32, 32, 0);
        transformComponent.scale.set(1, 1);
        transformComponent.rotation = 0;
        this.add(transformComponent);

        b2DComponent = new B2DComponent();
        b2DComponent.body = BodyFactory.getInstance(world).makeBox(posX, posY, PLAYER_WIDTH, PLAYER_HEGIHT, BodyDef.BodyType.DynamicBody);
        this.add(b2DComponent);

        collisionComponent = new CollisionComponent();
        this.add(collisionComponent);
    }
}
