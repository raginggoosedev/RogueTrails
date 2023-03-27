package com.raginggoose.roguetrails.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.raginggoose.roguetrails.dungeon.Dungeon;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.PlayerComponent;
import com.raginggoose.roguetrails.ecs.components.TransformComponent;
import com.raginggoose.roguetrails.player.Direction;
import com.raginggoose.roguetrails.player.Player;
import com.raginggoose.roguetrails.room.Room;
import com.raginggoose.roguetrails.screens.GameScreen;

public class PlayerMovementSystem extends IteratingSystem {

    private Direction checkCollision(Player player, Room room) {

        int px1 = player.getX();
        int px2 = player.getX() + player.SIZE;
        int py1 = player.getY();
        int py2 = player.getY() + player.SIZE;

        int leftBound = room.getX();
        int rightBound = room.getX() + room.getWidth();
        int lowerBound = room.getY();
        int upperBound = room.getY() + room.getHeight();

        if (px1 <= leftBound) {
            if (room.getWest() == null) return Direction.LEFT;
            if (py1 < room.getWest().getY() || py2 > room.getWest().getY() + room.getWest().getHeight()) return Direction.LEFT;
        }
        if (px2 >= rightBound) {
            if (room.getEast() == null) return Direction.RIGHT;
            if (py1 < room.getEast().getY() || py2 > room.getEast().getY() + room.getEast().getHeight()) return Direction.RIGHT;
        }
        if (py1 <= lowerBound) {
            if (room.getSouth() == null) return Direction.DOWN;
            if (px1 < room.getSouth().getX() || px2 > room.getSouth().getX() + room.getSouth().getWidth()) return Direction.DOWN;
        }
        if (py2 >= upperBound) {
            if (room.getNorth() == null) return Direction.UP;
            if (px1 < room.getNorth().getX() || px2 > room.getNorth().getX() + room.getNorth().getWidth()) return Direction.UP;
        }

        return null;

    }

    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mapper.transformMapper.get(entity);

        int px = (int) transform.position.x;
        int py = (int) transform.position.y;

        Player player = new Player(px,py);
        Dungeon dun = GameScreen.dun;
        Direction dir = checkCollision(player, dun.getCurrentRoom(player));

        if (Gdx.input.isKeyPressed(Input.Keys.W) && dir != Direction.UP)
            transform.position.add(0, 1, 0);
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && dir != Direction.DOWN)
            transform.position.add(0, -1, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.A) && dir != Direction.LEFT)
            transform.position.add(-1, 0, 0);
        else if (Gdx.input.isKeyPressed(Input.Keys.D) && dir != Direction.RIGHT)
            transform.position.add(1, 0, 0);
    }
}
