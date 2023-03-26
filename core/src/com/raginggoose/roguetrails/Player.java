package com.raginggoose.roguetrails;

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

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(ShapeRenderer s) {
        s.rect(x, y, 10, 10, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
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

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y++;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y--;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x--;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x++;
        }
    }
}
