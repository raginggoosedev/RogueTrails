package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
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

    /**
     * Creates a new debug rendering system with a given shape renderer
     *
     * @param shape the shape renderer to be used by the system
     */
    public DebugRenderingSystem(ShapeRenderer shape) {
        super(Family.all(TransformComponent.class).get(), new ZComparator());
        this.shape = shape;
        comparator = new ZComparator();
        renderQueue = new Array<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // Add the entity to the ordered array of entities to be rendered
        renderQueue.add(entity);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // Sort the array by the z value of each entities' position
        renderQueue.sort(comparator);

        // Draw each entity as a rectangle of their given colour
        shape.begin(ShapeRenderer.ShapeType.Line);
        for (Entity e : new Array.ArrayIterator<>(renderQueue)) {
            if (Mapper.RENDER_MAPPER.get(e) != null && !Mapper.RENDER_MAPPER.get(e).shouldRender) {
                continue;
            }

            TransformComponent transformComponent = Mapper.TRANSFORM_MAPPER.get(e);
            Color c = Mapper.DEBUG_MAPPER.get(e).color;
            shape.rect(transformComponent.position.x, transformComponent.position.y, transformComponent.width, transformComponent.height, c, c, c, c);
        }

        shape.end();

        // All entities have now been rendered, clear the array
        renderQueue.clear();
    }
}
