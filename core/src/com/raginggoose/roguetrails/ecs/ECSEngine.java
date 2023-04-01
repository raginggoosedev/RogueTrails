package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.ecs.components.*;
import com.raginggoose.roguetrails.ecs.systems.DebugRenderingSystem;
import com.raginggoose.roguetrails.ecs.systems.EnemyMovementSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerCameraSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerMovementSystem;
import com.raginggoose.roguetrails.loader.AssetLoader;

public class ECSEngine extends PooledEngine {
    private Entity player;
    private final AssetManager assetManager;
    public ECSEngine(ShapeRenderer s, OrthographicCamera cam, AssetManager assetManager) {
        if (RogueTrails.DEBUG)
            this.addSystem(new DebugRenderingSystem(s));
        this.assetManager = assetManager;
        this.addSystem(new PlayerMovementSystem());

        this.addSystem(new PlayerCameraSystem(cam));

        this.addSystem(new EnemyMovementSystem());
    }

    public void createPlayer(int x, int y, int w, int h, int drawOrder, Color debugColour, AssetManager assetManager) {
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


        Texture playerMove = new Texture(Gdx.files.internal("playerSprites/playerMove.png"));
        final int FRAME_COLS = 10, FRAME_ROWS = 1;
        float frameSpeed = 0.05f;
        TextureRegion[][] movement = TextureRegion.split(playerMove, playerMove.getHeight() / FRAME_COLS, playerMove.getWidth() / FRAME_ROWS);

        TextureRegion[] movementFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int indexMove = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                movementFrames[indexMove++] = movement[i][j];
            }
        }

        Animation<TextureRegion> movementAnimation = new Animation<>(frameSpeed, movementFrames);

        // Animation Component
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);

        animationComponent.animations.put(StateComponent.STATE_RIGHT, movementAnimation);

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
