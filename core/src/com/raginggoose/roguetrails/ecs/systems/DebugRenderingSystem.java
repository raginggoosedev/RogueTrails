package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.raginggoose.roguetrails.Constants;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.ZComparator;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

import java.util.Comparator;

/**
 * Renders everything as basic rectangles with assigned colours for debugging purposes
 */
public class DebugRenderingSystem extends SortedIteratingSystem {
    private final ShapeRenderer shape;
    private final Comparator<Entity> comparator;
    private final Array<Entity> renderQueue;
    private final Box2DDebugRenderer debugRenderer;
    private final World world;
    private final OrthographicCamera cam;

    /**
     * Creates a new debug rendering system with a given shape renderer
     *
     * @param shape the shape renderer to be used by the system
     */
    public DebugRenderingSystem(ShapeRenderer shape, Box2DDebugRenderer debugRenderer, World world, OrthographicCamera cam) {
        super(Family.all(TransformComponent.class).get(), new ZComparator());
        this.shape = shape;
        comparator = new ZComparator();
        renderQueue = new Array<>();
        this.debugRenderer = debugRenderer;
        this.world = world;
        this.cam = cam;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // Add the entity to the ordered array of entities to be rendered
        renderQueue.add(entity);
    }

    @Override
    public void update(float delta) {
        super.update(delta);


        // Debug render the physics world
        debugRenderer.render(world, cam.combined);
    }

}
