package com.raginggoose.roguetrails.item;

public abstract class RangedWeapon extends Weapon {
    public abstract int getMaxAmmo();
    public abstract void setMaxAmmo(int max);
    public abstract int getAmmo();
    public abstract void setAmmo(int ammo);
    public abstract void reload();
    public abstract void shoot();

}
