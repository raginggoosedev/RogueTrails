package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.ecs.components.*;
import com.raginggoose.roguetrails.ecs.systems.DebugRenderingSystem;
import com.raginggoose.roguetrails.ecs.systems.EnemyMovementSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerCameraSystem;

/**
 * Handles all the game's entities
 */
public class ECSEngine extends PooledEngine {
    public static final Color ENEMY_DEBUG_COLOUR = Color.RED;
    public static final Color PLAYER_DEBUG_COLOUR = Color.BLUE;
    public static final Color ITEM_DEBUG_COLOUR = Color.GREEN;

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
<<<<<<< HEAD
     * @param x         the player's x coordinate
     * @param y         the player's y coordinate
     * @param w         the player's width
     * @param h         the player's height
     * @param drawOrder the layer that the player is drawn on (the order)
=======
     * @param x           the player's x coordinate
     * @param y           the player's y coordinate
     * @param w           the player's width
     * @param h           the player's height
     * @param drawOrder   the layer that the player is drawn on (the order)
>>>>>>> origin/main
     */
    public void createPlayer(int x, int y, int w, int h, int drawOrder) {
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
            debugComponent.color = PLAYER_DEBUG_COLOUR;
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
     */
    public void createEnemy(int x, int y, int w, int h, int drawOrder) {
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
            debugComponent.color = ENEMY_DEBUG_COLOUR;
            enemy.add(debugComponent);
        }

        this.addEntity(enemy);
    }

    public void createItem(int x, int y, int w, int h, int drawOrder, int type) {
        Entity item = this.createEntity();

        // Item Component
        ItemComponent itemComponent = this.createComponent(ItemComponent.class);
        itemComponent.type = type;
        item.add(itemComponent);

        // Transform Component
        TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        transformComponent.position.set(x, y, drawOrder);
        transformComponent.scale.set(1, 1);
        transformComponent.width = w;
        transformComponent.height = h;
        item.add(transformComponent);

        // Debug Component
        if (RogueTrails.DEBUG) {
            DebugComponent debugComponent = this.createComponent(DebugComponent.class);
            debugComponent.color = ITEM_DEBUG_COLOUR;
            item.add(debugComponent);
        }

        switch (type) {
            case ItemComponent.SHORT_SWORD:
                MeleeComponent meleeComponent = this.createComponent(MeleeComponent.class);
                meleeComponent.damage = 5;
                meleeComponent.range = 3;
                meleeComponent.speed = 10;
                meleeComponent.coolDown = 3;
                item.add(meleeComponent);
                break;
            case ItemComponent.BROAD_SWORD:
                meleeComponent = this.createComponent(MeleeComponent.class);
                meleeComponent.damage = 7;
                meleeComponent.range = 5;
                meleeComponent.speed = 5;
                meleeComponent.coolDown = 5;
                item.add(meleeComponent);
                break;
            case ItemComponent.SPEED_BUFF:
            case ItemComponent.REGENERATION_BUFF:
            case ItemComponent.STAMINA_BUFF:
            case ItemComponent.STRENGTH_BUFF:
                PerkComponent perkComponent = this.createComponent(PerkComponent.class);
                perkComponent.timeLimit = 5.0f;
                item.add(perkComponent);
                break;
        }

        this.addEntity(item);
    }
}
