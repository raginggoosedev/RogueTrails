package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.b2d.BodyFactory;
import com.raginggoose.roguetrails.ecs.components.*;
import com.raginggoose.roguetrails.ecs.systems.*;
import com.raginggoose.roguetrails.inventory.Inventory;
import com.raginggoose.roguetrails.loader.AssetLoader;

/**
 * Handles all the game's entities
 */
public class ECSEngine extends PooledEngine {
    public final static String TAG = ECSEngine.class.getSimpleName();
    private final World world;
    public static final Color ENEMY_DEBUG_COLOUR = Color.RED;
    public static final Color PLAYER_DEBUG_COLOUR = Color.BLUE;
    public static final Color ITEM_DEBUG_COLOUR = Color.GREEN;

    private final Entity player;
    private final AssetLoader assetLoader;

    public ECSEngine(ShapeRenderer s, Box2DDebugRenderer debug, SpriteBatch batch, OrthographicCamera cam, AssetLoader assetLoader, World world) {
        this.world = world;
        this.assetLoader = assetLoader;

        if (RogueTrails.DEBUG)
            this.addSystem(new DebugRenderingSystem(s, debug, world, cam));

        this.addSystem(new PlayerCameraSystem(cam));

        this.addSystem(new EnemyMovementSystem());

        this.addSystem(new RenderingSystem(batch));

        this.addSystem(new CollisionSystem());

        this.addSystem(new AnimationSystem());

        this.addSystem(new PhysicsSystem(world));

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
    public void createPlayer(int x, int y, int w, int h, int drawOrder) {
        StringBuilder sBuild = new StringBuilder();
        sBuild.append("----------------------\n");
        sBuild.append("New PLAYER entity\n");


        // Player Component
        PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed = 4.0f;
        playerComponent.health = 3.0f;
        playerComponent.inventory = new Inventory();
        player.add(playerComponent);
        sBuild.append("Player Speed: ").append(playerComponent.speed).append("\n");

        // Transform Component
        TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        transformComponent.position.set(x, y, drawOrder);
        transformComponent.prevPosition.set(transformComponent.position.x, transformComponent.position.y);
        transformComponent.scale.set(1, 1);
        transformComponent.width = w;
        transformComponent.height = h;
        player.add(transformComponent);
        sBuild.append("Player Position: ").append(transformComponent.position.toString()).append("\n");
        sBuild.append("Player Width & Height: ").append(transformComponent.width).append(", ").append(transformComponent.height).append("\n");



        // State Component
        StateComponent stateComponent = this.createComponent(StateComponent.class);
        stateComponent.setState(StateComponent.STATE_DOWN);
        stateComponent.isLooping = true;
        player.add(stateComponent);

        // Animation Component
        AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);

        TextureAtlas playerAtlas = assetLoader.manager.get(AssetLoader.PLAYER_ATLAS);
        Animation<TextureRegion> upAnim = new Animation<>(0.1f, playerAtlas.findRegions("walk"));

        animationComponent.animations.put(StateComponent.STATE_UP, upAnim);
        animationComponent.animations.put(StateComponent.STATE_DOWN, upAnim);
        animationComponent.animations.put(StateComponent.STATE_LEFT, upAnim);
        animationComponent.animations.put(StateComponent.STATE_RIGHT, upAnim);

        player.add(animationComponent);

        // Render Component
        RenderComponent renderComponent = this.createComponent(RenderComponent.class);
        renderComponent.shouldRender = true;
        renderComponent.region = playerAtlas.findRegion("walk", 1);
        player.add(renderComponent);

        // Collision Component
        CollisionComponent collisionComponent = this.createComponent(CollisionComponent.class);

        collisionComponent.body = BodyFactory.getInstance(world).makeBox(x, y, w, h, BodyDef.BodyType.DynamicBody, false, player, BodyFactory.PLAYER_BITS, (short) (BodyFactory.ENEMY_BITS | BodyFactory.ITEM_BITS | BodyFactory.STATIC_BITS));
        player.add(collisionComponent);

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
    public void createEnemy(int x, int y, int w, int h, int drawOrder, float damage) {
        Entity enemy = this.createEntity();
        StringBuilder sBuild = new StringBuilder();
        sBuild.append("----------------------\n");
        sBuild.append("New ENEMY entity\n");

        // Enemy Component
        EnemyComponent enemyComponent = this.createComponent(EnemyComponent.class);
        enemyComponent.player = player;
        enemyComponent.speed = 1.0f;
        enemyComponent.damage = damage;
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

        // Collision Component
        CollisionComponent collisionComponent = this.createComponent(CollisionComponent.class);

        collisionComponent.body = BodyFactory.getInstance(world).makeBox(x, y, w, h, BodyDef.BodyType.DynamicBody, false, enemy, BodyFactory.ENEMY_BITS, (short) (BodyFactory.PLAYER_BITS | BodyFactory.STATIC_BITS));
        enemy.add(collisionComponent);

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

        // Collision Component
        CollisionComponent collisionComponent = this.createComponent(CollisionComponent.class);
        collisionComponent.body = BodyFactory.getInstance(world).makeBox(x, y, w, h, BodyDef.BodyType.StaticBody, true, item, BodyFactory.ITEM_BITS, BodyFactory.PLAYER_BITS);
        item.add(collisionComponent);

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

    public Entity getPlayer() {
        return player;
    }
}
