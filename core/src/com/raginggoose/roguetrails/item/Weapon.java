package com.raginggoose.roguetrails.item;

public abstract class Weapon extends Item {
    public abstract int getDamage();
    public abstract void setDamage(int damage);
    public abstract int getCooldown();
    public abstract void setCooldown(double cooldown);
    public abstract int getRange();
    public abstract void setRange(int range);
}
