package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.ZComparator;
import com.raginggoose.roguetrails.ecs.components.RenderComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {
    private final SpriteBatch batch;
    private final Comparator<Entity> comparator;
    private final Array<Entity> renderQueue;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(RenderComponent.class).get(), new ZComparator());
        this.batch = batch;
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

        // Draw each entity's texture
        batch.begin();
        for (Entity e : new Array.ArrayIterator<>(renderQueue)) {
            RenderComponent renderComponent = Mapper.RENDER_MAPPER.get(e);

            if (!Mapper.RENDER_MAPPER.get(e).shouldRender || renderComponent.region == null) {
                continue;
            }

            TransformComponent transformComponent = Mapper.TRANSFORM_MAPPER.get(e);

            float originX = renderComponent.region.getRegionWidth() / 2f;
            float originY = renderComponent.region.getRegionHeight() / 2f;

            batch.draw(renderComponent.region.getTexture(), transformComponent.position.x, transformComponent.position.y, transformComponent.width, transformComponent.height);
        }

        batch.end();

        // All entities have now been rendered, clear the array
        renderQueue.clear();
    }
}
