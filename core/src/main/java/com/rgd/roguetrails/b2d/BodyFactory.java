
package com.rgd.roguetrails.b2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2025/12/07
 * A class used to create all physics bodies in the games world, following a singleton, abstract factory, and builder design pattern
 */
public class BodyFactory {
    private static BodyFactory bf;
    private final World world;

    /**
     * Constructor for the BodyFactory
     *
     * @param world Sets the world to the given physics world
     */
    private BodyFactory(World world) {
        this.world = world;
    }

    /**
     * Get an instance of the BodyFactory
     *
     * @param world The physics world of the game
     * @return A new BodyFactory if one does not exist for the given world, otherwise the existing BodyFactory is returned
     */
    public static BodyFactory getInstance(World world) {
        if (bf == null) {
            bf = new BodyFactory(world);
        }

        return bf;
    }

    public Body makeBox(float posX, float posY, float width, float height, BodyDef.BodyType bodyType) {
        //TODO complete
        return null;
    }
}
