package com.raginggoose.roguetrails.b2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

import static com.raginggoose.roguetrails.Constants.PPM;

public class BodyFactory {
    private static BodyFactory instance;
    private final World world;

    private BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory getInstance(World world) {
        if (instance == null) {
            instance = new BodyFactory(world);
        }

        return instance;
    }

    public Body makeBox(float x, float y, float w, float h, BodyDef.BodyType bodyType, boolean isSensor) {
        Gdx.app.debug("Body Factory", "Making a new Box2D Box!");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x / 2f / PPM, y / 2f / PPM);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(w / 2f / PPM, h / 2f / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.isSensor = isSensor;
        fixtureDef.shape = poly;
        body.createFixture(fixtureDef);

        poly.dispose();
        return body;
    }

    public Body makeLine(float x1, float y1, float x2, float y2, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(0, 0); // Set initial position to (0, 0)
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(x1 / PPM, y1 / PPM, x2 / PPM, y2 / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        body.createFixture(fixtureDef);

        edgeShape.dispose();
        return body;
    }
}
