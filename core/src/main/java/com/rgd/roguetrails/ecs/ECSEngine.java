package com.rgd.roguetrails.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.rgd.roguetrails.RogueTrails;
import com.rgd.roguetrails.ecs.entities.Player;
import com.rgd.roguetrails.ecs.systems.PhysicsSystem;
import com.rgd.roguetrails.ecs.systems.PlayerCameraSystem;
import com.rgd.roguetrails.ecs.systems.PlayerMovementSystem;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 2025-12-09 v1.0
 * Engine for creating and managing entities within the game. ECS - Entity, Component, and Systems
 */
public class ECSEngine extends PooledEngine {
    private final World world;

    //TODO Make Entity Component System Engine (Ashley)
    public ECSEngine(SpriteBatch batch, OrthographicCamera camera, World world, RogueTrails game) {
        this.world = world;

        this.addSystem(new PhysicsSystem(world));
        this.addSystem(new PlayerMovementSystem());
        this.addSystem(new PlayerCameraSystem(camera));

        // Player Entity
        Entity player = new Player(world, 32, 32);
        this.addEntity(player);
    }
}
