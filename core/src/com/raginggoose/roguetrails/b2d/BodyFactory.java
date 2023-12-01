package com.raginggoose.roguetrails.b2d;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

import static com.raginggoose.roguetrails.Constants.PPM;

/**
 * Builds the collision bodies for the game
 */
public class BodyFactory {
    // Collision bits for the different body types
    public static final short PLAYER_BITS = 0x1;
    public static final short ENEMY_BITS = 0x1 << 1;
    public static final short ITEM_BITS = 0x1 << 2;
    public static final short STATIC_BITS = 0x1 << 3;

    private static BodyFactory instance;
    private final World world;

    /**
     * Creates a new body factory to build collision bodies for the given world
     * @param world the world to build bodies in
     */
    private BodyFactory(World world) {
        this.world = world;
    }

    /**
     * Uses a single instance of body factory in multiple areas of the game (singleton)
     * @param world the world to build bodies in
     * @return an instance of body factory
     */
    public static BodyFactory getInstance(World world) {
        if (instance == null) {
            // There is no instance of a body factory yet, create one
            instance = new BodyFactory(world);
        }

        return instance;
    }

    /**
     * Makes a box body with the given parameters
     * @param x the x coordinate of the box
     * @param y the y coordinate of the box
     * @param w the width of the box
     * @param h the height of the box
     * @param bodyType the type of box it is (static/dynamic/kinetic)
     * @param isSensor whether the box is a sensor or not
     * @param userdata the data for the box (entity or null for boundary)
     * @param categoryBits the category bits for the box (who is this box is in terms of collisions)
     * @param maskBits the mask bits for the box (i.e., who this box can collide with)
     * @return a box body built with the given parameters
     */
    public Body makeBox(float x, float y, float w, float h, BodyDef.BodyType bodyType, boolean isSensor, Entity userdata, short categoryBits, short maskBits) {
        // Debugging message
        Gdx.app.debug("Body Factory", "Making a new Box2D Box!");

        // Create the body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x / 2f / PPM, y / 2f / PPM);
        bodyDef.fixedRotation = true;

        // Create the body in the world with its body def
        Body body = world.createBody(bodyDef);
        body.setUserData(userdata);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(w / 2f / PPM, h / 2f / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = isSensor;
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        fixtureDef.shape = poly;
        body.createFixture(fixtureDef);

        // Dispose of the poly shape
        poly.dispose();
        return body;
    }

    /**
     * Creates a new line body with the given parameters
     * @param x1 the head x coordinate of the line
     * @param y1 the head y coordinate of the line
     * @param x2 the tail x coordinate of the line
     * @param y2 the tail y coordinate of the line
     * @param bodyType the type of line it is (static/dynamic/kinetic)
     * @param categoryBits the category bits for the line (who the line is in terms of collisions)
     * @param maskBits the mask bits for the line (who the line can collide with)
     * @return a line body built with the given parameters
     */
    public Body makeLine(float x1, float y1, float x2, float y2, BodyDef.BodyType bodyType, short categoryBits, short maskBits) {
        // Debugging message
        Gdx.app.debug("Body Factory", "Making a new Box2D Line!");

        // Create the body def for the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(0, 0); // Set initial position to (0, 0)
        bodyDef.fixedRotation = true;

        // Create the body in the world with its body def
        Body body = world.createBody(bodyDef);
        body.setUserData(null);
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(x1 / PPM, y1 / PPM, x2 / PPM, y2 / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        fixtureDef.shape = edgeShape;
        body.createFixture(fixtureDef);


        // Dispose of the edge shape
        edgeShape.dispose();
        return body;
    }
}
