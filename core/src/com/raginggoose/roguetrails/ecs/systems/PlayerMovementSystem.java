package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;
import com.raginggoose.roguetrails.player.Direction;
import com.raginggoose.roguetrails.room.Room;
import com.raginggoose.roguetrails.screens.GameScreen;

public class PlayerMovementSystem extends IteratingSystem {

    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    private Direction checkCollision(float x, float y, float w, float h, Room room) {
        if (room != null) {
            int leftBound = room.getX();
            int rightBound = room.getX() + room.getWidth();
            int lowerBound = room.getY();
            int upperBound = room.getY() + room.getHeight();

            if (x <= leftBound) {
                if (room.getWest() == null) return Direction.LEFT;
                if (y < room.getWest().getY() || y + h > room.getWest().getY() + room.getWest().getHeight())
                    return Direction.LEFT;
            }
            if (x + w >= rightBound) {
                if (room.getEast() == null) return Direction.RIGHT;
                if (y < room.getEast().getY() || y + h > room.getEast().getY() + room.getEast().getHeight())
                    return Direction.RIGHT;
            }
            if (y <= lowerBound) {
                if (room.getSouth() == null) return Direction.DOWN;
                if (x < room.getSouth().getX() || x + w > room.getSouth().getX() + room.getSouth().getWidth())
                    return Direction.DOWN;
            }
            if (y + h >= upperBound) {
                if (room.getNorth() == null) return Direction.UP;
                if (x < room.getNorth().getX() || x + w > room.getNorth().getX() + room.getNorth().getWidth())
                    return Direction.UP;
            }

            return null;
        }
        //TODO Josh fix bug please
        throw new NullPointerException("Player's room is null!");
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mapper.transformMapper.get(entity);
        PlayerComponent playerComponent = Mapper.playerMapper.get(entity);

        float speed = playerComponent.speed;

        Vector3 pos = transform.position;

        Dungeon dun = GameScreen.dun;
        Direction dir = checkCollision(pos.x, pos.y, transform.width, transform.height, dun.getCurrentRoom(pos.x, pos.y));

        if (Gdx.input.isKeyPressed(Input.Keys.W) && dir != Direction.UP)
            transform.position.add(0, speed, 0);
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && dir != Direction.DOWN)
            transform.position.add(0, -speed, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.A) && dir != Direction.LEFT)
            transform.position.add(-speed, 0, 0);
        else if (Gdx.input.isKeyPressed(Input.Keys.D) && dir != Direction.RIGHT)
            transform.position.add(speed, 0, 0);
    }
}
