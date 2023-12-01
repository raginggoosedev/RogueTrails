package com.raginggoose.roguetrails.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Contains information about an item entity
 */
public class ItemComponent implements Component, Pool.Poolable {
    public static final int SHORT_SWORD = 0;
    public static final int BROAD_SWORD = 1;
    public static final int ARTIFACT = 2;
    public static final int SPEED_BUFF = 3;
    public static final int REGENERATION_BUFF = 4;
    public static final int STRENGTH_BUFF = 5;
    public static final int STAMINA_BUFF = 6;

    public int type = -1;
    public boolean collected = false;

    @Override
    public void reset() {
        type = -1;
        collected = false;
    }

    @Override
    public String toString() {
        switch (type) {
            case SHORT_SWORD:
                return "Short Sword";
            case BROAD_SWORD:
                return "Broad Sword";
            case ARTIFACT:
                return "Artifact";
            case SPEED_BUFF:
                return "Speed Buff";
            case REGENERATION_BUFF:
                return "Regeneration Buff";
            case STRENGTH_BUFF:
                return "Strength Buff";
            case STAMINA_BUFF:
                return "Stamina Buf";
            default:
                return "Null Item";
        }
    }
}
