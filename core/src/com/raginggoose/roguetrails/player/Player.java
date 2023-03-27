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
            y+=1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y-=1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x-=1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x+=1;
        }
    }
}
