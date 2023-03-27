package com.raginggoose.roguetrails.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Temporary player class for early testing
 */
@Deprecated
public class Player {
    private int x, y;
    public final static int SIZE = 10;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(ShapeRenderer s) {
        s.rect(x, y, SIZE, SIZE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(Direction dir) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && dir != Direction.UP) {
            y+=1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && dir != Direction.DOWN) {
            y-=1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && dir != Direction.LEFT) {
            x-=1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && dir != Direction.RIGHT) {
            x+=1;
        }
    }
}
