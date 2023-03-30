package com.raginggoose.roguetrails.item;

public abstract class Perk extends Item {
    public abstract Buff getBuff();
    public abstract void setBuff(Buff buff);
    public abstract int getTimeLimit();
    public abstract void setTimeLimit();
    public abstract int getTimeLeft();
    public abstract int setTimeLeft();
}
