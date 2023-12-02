package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

/**
 * A Z coordinate comparator
 */
public class ZComparator implements Comparator<Entity> {
    public ZComparator() {

    }

    /**
     * Compares the z coordinate of two entities
     *
     * @param e1 the first entity to be compared
     * @param e2 the seconf entity to be compared
     * @return 1 if the z coordinate of e1 > e2, -1 if the z coordinate of e1 < e2, otherwise 0
     */
    @Override
    public int compare(Entity e1, Entity e2) {
        float az = Mapper.TRANSFORM_MAPPER.get(e1).position.z;
        float bz = Mapper.TRANSFORM_MAPPER.get(e2).position.z;

        // Return 1 if entity 1 has a greater z value, -1 if it has a smaller z value, else return 0
        if (az > bz)
            return 1;
        else if (az < bz)
            return -1;
        return 0;
    }
}
