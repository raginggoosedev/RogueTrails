package com.raginggoose.roguetrails.ecs;

import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

/**
 * Compares the z coordinates of two entities
 * @author Michael Quick
 * @version 1.0, 2023/03/26
 */
public class ZComparator implements Comparator<Entity> {
    public ZComparator() {

    }

    /**
     * Compares the z coordinate of two entities
     * @param e1 the first entity to be compared
     * @param e2 the seconf entity to be compared
     * @return 1 if the z coordinate of e1 > e2, -1 if the z coordinate of e1 < e2, otherwise 0
     */
    @Override
    public int compare(Entity e1, Entity e2) {
        float az = Mapper.transformMapper.get(e1).position.z;
        float bz = Mapper.transformMapper.get(e2).position.z;

        if (az > bz)
            return 1;
        else if (az < bz)
            return -1;
        return 0;
    }
}
