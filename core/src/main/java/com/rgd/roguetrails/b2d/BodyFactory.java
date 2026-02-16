
package com.rgd.roguetrails.b2d;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.rgd.roguetrails.utils.Constants.PPM;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2025/12/07
 * A class used to create all physics bodies in the games world, following a singleton design pattern
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
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(posX / PPM, posY / PPM);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        PolygonShape poly = new PolygonShape();

        // SetAsBox uses half width/height
        poly.setAsBox(width / 2f / PPM, height / 2f / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        body.createFixture(fixtureDef);
        poly.dispose();

        return body;
    }

    public Body makePolyline(float posX, float posY, float[] points, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX / PPM, posY / PPM);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        ChainShape line = new ChainShape();

        for (int i = 0; i < points.length; i++) {
            points[i] = points[i] / PPM;
        }

        // x1, y1, x2, y2, ...
        line.createChain(points);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = line;
        body.createFixture(fixtureDef);
        line.dispose();

        return body;
    }
}
