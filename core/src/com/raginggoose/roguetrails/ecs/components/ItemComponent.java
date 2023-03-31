package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ItemComponent implements Component, Pool.Poolable {
    public static final int SHORT_SWORD = 0;
    public static final int BROAD_SWORD = 1;
    public static final int ARTIFACT = 2;
    public static final int SPEED_BUFF = 3;
    public static final int REGENERATION_BUFF = 4;
    public static final int STRENGTH_BUFF = 5;
    public static final int STAMINA_BUFF = 6;

    public int type = -1;

    @Override
    public void reset() {
        type = -1;
    }
}
