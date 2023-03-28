package com.raginggoose.roguetrails.item;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Item {
    public abstract int getX();
    public abstract int getY();
    public abstract void setX(int x);
    public abstract void setY(int y);
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract void setWidth(int w);
    public abstract void setHeight(int h);
    public abstract void draw(ShapeRenderer shape);
}
