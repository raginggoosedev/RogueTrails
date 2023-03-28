package com.raginggoose.roguetrails.item;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Shortsword extends Melee {

    private int x;
    private int y;
    private int w;
    private int h;
    private int damage;
    private double cooldown;
    private int range;

    public Shortsword(int x, int y, int damage, double cooldown, int range) {
        this.x=x;
        this.y=y;
        this.damage=damage;
        this.cooldown=cooldown;
        this.range=range;
    }

    public Shortsword (int x, int y) {
        this.x = x;
        this.y = y;
        w = 40;
        h = 40;
        damage = 5;
        cooldown = 0.3;
        range = 25;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public void setWidth(int w) {
        this.w = w;
    }

    @Override
    public void setHeight(int h) {
        this.h = h;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.rect(x,y,w,h);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public double getCooldown() {
        return cooldown;
    }

    @Override
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public void swing() {
        //TODO: code to attack
    }
}
