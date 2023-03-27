package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
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

public class DebugRenderingSystem extends SortedIteratingSystem {
    private final ShapeRenderer shape;
    private final Comparator<Entity> comparator;
    private final Array<Entity> renderQueue;

    public DebugRenderingSystem(ShapeRenderer shape) {
        super(Family.all(TransformComponent.class).get(), new ZComparator());
        this.shape = shape;
        comparator = new ZComparator();
        renderQueue = new Array<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        renderQueue.sort(comparator);

        shape.begin(ShapeRenderer.ShapeType.Line);
        for (Entity e : new Array.ArrayIterator<>(renderQueue)) {
            TransformComponent transformComponent = Mapper.transformMapper.get(e);
            Color c = Mapper.debugMapper.get(e).color;
            shape.rect(transformComponent.position.x, transformComponent.position.y, transformComponent.width, transformComponent.height, c, c, c, c);
        }

        shape.end();
        renderQueue.clear();
    }
}
