package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.ecs.components.DebugComponent;
import com.raginggoose.roguetrails.ecs.components.EnemyComponent;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;
import com.raginggoose.roguetrails.ecs.systems.DebugRenderingSystem;
import com.raginggoose.roguetrails.ecs.systems.EnemyMovementSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerCameraSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerMovementSystem;

public class ECSEngine extends PooledEngine {
    private Entity player;

    public ECSEngine(ShapeRenderer s, OrthographicCamera cam) {
        if (RogueTrails.DEBUG)
            this.addSystem(new DebugRenderingSystem(s));

        this.addSystem(new PlayerMovementSystem());

        this.addSystem(new PlayerCameraSystem(cam));

        this.addSystem(new EnemyMovementSystem());
    }

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
