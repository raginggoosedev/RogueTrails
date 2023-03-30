package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.components.DebugComponent;
import com.raginggoose.roguetrails.ecs.components.EnemyComponent;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;
import com.raginggoose.roguetrails.ecs.systems.DebugRenderingSystem;
import com.raginggoose.roguetrails.ecs.systems.EnemyMovementSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerCameraSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerMovementSystem;

/**
 * Handles all the game's entities
 */
public class ECSEngine extends PooledEngine {
    private Entity player;

    public ECSEngine(ShapeRenderer s, OrthographicCamera cam) {
        if (RogueTrails.DEBUG)
            this.addSystem(new DebugRenderingSystem(s));

        this.addSystem(new PlayerCameraSystem(cam));

        this.addSystem(new EnemyMovementSystem());
    }

    /**
     * Creates the player entity
     *
     * @param x           the player's x coordinate
     * @param y           the player's y coordinate
     * @param w           the player's width
     * @param h           the player's height
     * @param drawOrder   the layer that the player is drawn on (the order)
     * @param debugColour what colour the debug box should be drawn as
     */
    public void createPlayer(int x, int y, int w, int h, int drawOrder, Color debugColour) {
        player = this.createEntity();

        // Player Component
        PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed = 2.0f;
        player.add(playerComponent);

        // Transform Component
        TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        transformComponent.position.set(x, y, drawOrder);
        transformComponent.scale.set(1, 1);
        transformComponent.width = w;
        transformComponent.height = h;
        player.add(transformComponent);

        // Debug Component
        if (RogueTrails.DEBUG) {
            DebugComponent debugComponent = this.createComponent(DebugComponent.class);
            debugComponent.color = debugColour;
            player.add(debugComponent);
        }

        this.addEntity(player);
    }

    /**
     * Creates an enemy entity with the given parameters
     *
     * @param x           the enemy's x coordinate
     * @param y           the enemy's y coordinate
     * @param w           the enemy's width
     * @param h           the enemy's height
     * @param drawOrder   the enemy's layer to be drawn on
     * @param debugColour the colour assigned to the enemy for debug rendering
     */
    public void createEnemy(int x, int y, int w, int h, int drawOrder, Color debugColour) {
        Entity enemy = this.createEntity();

        // Enemy Component
        EnemyComponent enemyComponent = this.createComponent(EnemyComponent.class);
        enemyComponent.player = player;
        enemyComponent.speed = 1.0f;
        enemy.add(enemyComponent);

        // Transform Component
        TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        transformComponent.position.set(x, y, drawOrder);
        transformComponent.scale.set(1, 1);
        transformComponent.width = w;
        transformComponent.height = h;
        enemy.add(transformComponent);

        // Debug Component
        if (RogueTrails.DEBUG) {
            DebugComponent debugComponent = this.createComponent(DebugComponent.class);
            debugComponent.color = debugColour;
            enemy.add(debugComponent);
        }

        this.addEntity(enemy);
    }
}
