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
import com.raginggoose.roguetrails.ecs.systems.InteractionSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerCameraSystem;
import com.raginggoose.roguetrails.ecs.systems.PlayerMovementSystem;
import com.raginggoose.roguetrails.loader.AssetLoader;
import com.raginggoose.roguetrails.inventory.Inventory;

/**
 * Handles all the game's entities
 */
public class ECSEngine extends PooledEngine {
    public final static String TAG = ECSEngine.class.getSimpleName();
    public static final Color ENEMY_DEBUG_COLOUR = Color.RED;
    public static final Color PLAYER_DEBUG_COLOUR = Color.BLUE;
    public static final Color ITEM_DEBUG_COLOUR = Color.GREEN;

    private Entity player;
    private final AssetManager assetManager;
    public ECSEngine(ShapeRenderer s, OrthographicCamera cam, AssetManager assetManager) {
        if (RogueTrails.DEBUG)
            this.addSystem(new DebugRenderingSystem(s));
            
        this.assetManager = assetManager;
        this.addSystem(new PlayerMovementSystem());
        this.addSystem(new PlayerCameraSystem(cam));

        this.addSystem(new EnemyMovementSystem());
        
        player = this.createEntity();
        this.addSystem(new InteractionSystem(player));
    }

    /**
     * Creates the player entity
     *
     * @param x         the player's x coordinate
     * @param y         the player's y coordinate
     * @param w         the player's width
     * @param h         the player's height
     * @param drawOrder the layer that the player is drawn on (the order)
     */
    public void createPlayer(int x, int y, int w, int h, int drawOrder, AssetManager assetManager) {
        StringBuilder sBuild = new StringBuilder();
        sBuild.append("----------------------\n");
        sBuild.append("New PLAYER entity\n");


        // Player Component
        PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed = 2.0f;
        playerComponent.inventory = new Inventory();
        player.add(playerComponent);
        sBuild.append("Player Speed: ").append(playerComponent.speed).append("\n");

        // Transform Component
        TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        transformComponent.position.set(x, y, drawOrder);
        transformComponent.scale.set(1, 1);
        transformComponent.width = w;
        transformComponent.height = h;
        player.add(transformComponent);
        sBuild.append("Player Position: ").append(transformComponent.position.toString()).append("\n");
        sBuild.append("Player Width & Height: ").append(transformComponent.width).append(", ").append(transformComponent.height).append("\n");

        // Render Component
        RenderComponent renderComponent = this.createComponent(RenderComponent.class);
        renderComponent.shouldRender = true;
        player.add(renderComponent);


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
            debugComponent.color = PLAYER_DEBUG_COLOUR;
            player.add(debugComponent);
            sBuild.append("Player Debug Color: ").append(PLAYER_DEBUG_COLOUR).append("\n");
        }

        this.addEntity(player);
        sBuild.append("----------------------------------\n");
        Gdx.app.log(TAG, sBuild.toString());
    }

    /**
     * Creates an enemy entity with the given parameters
     *
     * @param x         the enemy's x coordinate
     * @param y         the enemy's y coordinate
     * @param w         the enemy's width
     * @param h         the enemy's height
     * @param drawOrder the enemy's layer to be drawn on
     */
    public void createEnemy(int x, int y, int w, int h, int drawOrder) {
        Entity enemy = this.createEntity();
        StringBuilder sBuild = new StringBuilder();
        sBuild.append("----------------------\n");
        sBuild.append("New ENEMY entity\n");

        // Enemy Component
        EnemyComponent enemyComponent = this.createComponent(EnemyComponent.class);
        enemyComponent.player = player;
        enemyComponent.speed = 1.0f;
        enemy.add(enemyComponent);
        sBuild.append("Enemy Speed: ").append(enemyComponent.speed).append("\n");

        // Transform Component
        TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        transformComponent.position.set(x, y, drawOrder);
        transformComponent.scale.set(1, 1);
        transformComponent.width = w;
        transformComponent.height = h;
        enemy.add(transformComponent);
        sBuild.append("Enemy Position: ").append(transformComponent.position.toString()).append("\n");
        sBuild.append("Enemy Width & Height: ").append(transformComponent.width).append(", ").append(transformComponent.height).append("\n");

        // Render Component
        RenderComponent renderComponent = this.createComponent(RenderComponent.class);
        renderComponent.shouldRender = true;
        enemy.add(renderComponent);

        // Debug Component
        if (RogueTrails.DEBUG) {
            DebugComponent debugComponent = this.createComponent(DebugComponent.class);
            debugComponent.color = ENEMY_DEBUG_COLOUR;
            enemy.add(debugComponent);
            sBuild.append("Enemy Debug Color: ").append(ENEMY_DEBUG_COLOUR).append("\n");
        }

        this.addEntity(enemy);

        sBuild.append("----------------------------------\n");

        Gdx.app.debug(TAG, sBuild.toString());
    }

    public void createItem(int x, int y, int w, int h, int drawOrder, int type) {
        Entity item = this.createEntity();

        StringBuilder sBuild = new StringBuilder();
        sBuild.append("----------------------\n");
        sBuild.append("New ITEM entity\n");

        // Item Component
        ItemComponent itemComponent = this.createComponent(ItemComponent.class);
        itemComponent.type = type;
        itemComponent.collected = false;
        item.add(itemComponent);
        sBuild.append("Item Type: ").append(itemComponent).append("\n");

        // Transform Component
        TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        transformComponent.position.set(x, y, drawOrder);
        transformComponent.scale.set(1, 1);
        transformComponent.width = w;
        transformComponent.height = h;
        item.add(transformComponent);
        sBuild.append(itemComponent).append(" Position: ").append(transformComponent.position.toString()).append("\n");
        sBuild.append(itemComponent).append(" Width & Height: ").append(transformComponent.width).append(", ").append(transformComponent.height).append("\n");

        // Render Component
        RenderComponent renderComponent = this.createComponent(RenderComponent.class);
        renderComponent.shouldRender = true;
        item.add(renderComponent);

        // Debug Component
        if (RogueTrails.DEBUG) {
            DebugComponent debugComponent = this.createComponent(DebugComponent.class);
            debugComponent.color = ITEM_DEBUG_COLOUR;
            item.add(debugComponent);
            sBuild.append(itemComponent).append(" Debug Color: ").append(ITEM_DEBUG_COLOUR).append("\n");
        }

        switch (type) {
            case ItemComponent.SHORT_SWORD:
                MeleeComponent meleeComponent = this.createComponent(MeleeComponent.class);
                meleeComponent.damage = 5;
                meleeComponent.range = 3;
                meleeComponent.speed = 10;
                meleeComponent.coolDown = 3;
                item.add(meleeComponent);

                sBuild.append(itemComponent).append(" Damage: ").append(meleeComponent.damage).append("\n");
                sBuild.append(itemComponent).append(" Range: ").append(meleeComponent.range).append("\n");
                sBuild.append(itemComponent).append(" Speed: ").append(meleeComponent.speed).append("\n");
                sBuild.append(itemComponent).append(" Cool-down: ").append(meleeComponent.coolDown).append("\n");
                break;
            case ItemComponent.BROAD_SWORD:
                meleeComponent = this.createComponent(MeleeComponent.class);
                meleeComponent.damage = 7;
                meleeComponent.range = 5;
                meleeComponent.speed = 5;
                meleeComponent.coolDown = 5;
                item.add(meleeComponent);

                sBuild.append(itemComponent).append(" Damage: ").append(meleeComponent.damage).append("\n");
                sBuild.append(itemComponent).append(" Range: ").append(meleeComponent.range).append("\n");
                sBuild.append(itemComponent).append(" Speed: ").append(meleeComponent.speed).append("\n");
                sBuild.append(itemComponent).append(" Cool-down: ").append(meleeComponent.coolDown).append("\n");
                break;
            case ItemComponent.SPEED_BUFF:
            case ItemComponent.REGENERATION_BUFF:
            case ItemComponent.STAMINA_BUFF:
            case ItemComponent.STRENGTH_BUFF:
                PerkComponent perkComponent = this.createComponent(PerkComponent.class);
                perkComponent.timeLimit = 5.0f;
                item.add(perkComponent);

                sBuild.append(itemComponent).append(" Limit: ").append(perkComponent.timeLimit).append("\n");
                break;
        }

        this.addEntity(item);

        sBuild.append("----------------------------------\n");
        Gdx.app.debug(TAG, sBuild.toString());
    }
}
