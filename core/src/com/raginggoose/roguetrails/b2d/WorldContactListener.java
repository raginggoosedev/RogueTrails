package com.raginggoose.roguetrails.b2d;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        Gdx.app.log("World Contact Listener", fA.getBody().getType() + " has hit " + fB.getBody().getType());
        if (fA.getBody().getUserData() instanceof Entity && fB.getBody().getUserData() instanceof Entity) {
            Entity e1 = (Entity) fA.getBody().getUserData();
            Entity e2 = (Entity) fB.getBody().getUserData();

            // e1 collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e1);
            collisionComponent.collisionBody = fB.getBody();
            collisionComponent.collided = true;

            // e2 collision component
            collisionComponent = Mapper.COLLISION_MAPPER.get(e2);
            collisionComponent.collisionBody = fA.getBody();
            collisionComponent.collided = true;
        } else if (fA.getBody().getUserData() instanceof Entity) {
            Entity e = (Entity) fA.getBody().getUserData();
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e);
            collisionComponent.collisionBody = fB.getBody();
            collisionComponent.collided = true;
        } else if (fB.getBody().getUserData() instanceof Entity) {
            Entity e = (Entity) fB.getBody().getUserData();
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e);
            collisionComponent.collisionBody = fA.getBody();
            collisionComponent.collided = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        Gdx.app.log("World Contact Listener", fA.getBody().getType() + " ended contact with " + fB.getBody().getType());
        if (fA.getBody().getUserData() instanceof Entity && fB.getBody().getUserData() instanceof Entity) {
            Entity e1 = (Entity) fA.getBody().getUserData();
            Entity e2 = (Entity) fB.getBody().getUserData();

            // e1 collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e1);
            collisionComponent.collided = false;
            collisionComponent.collisionBody = null;
            collisionComponent.pushStrength = 0.0f;

            // e2 collision component
            collisionComponent = Mapper.COLLISION_MAPPER.get(e2);
            collisionComponent.collided = false;
            collisionComponent.collisionBody = null;
            collisionComponent.pushStrength = 0.0f;

        } else if (fA.getBody().getUserData() instanceof Entity) {
            Entity e = (Entity) fA.getBody().getUserData();
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e);
            collisionComponent.collided = false;
            collisionComponent.collisionBody = null;
            collisionComponent.pushStrength = 0.0f;

        } else if (fB.getBody().getUserData() instanceof Entity) {
            Entity e = (Entity) fB.getBody().getUserData();
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e);
            collisionComponent.collided = false;
            collisionComponent.collisionBody = null;
            collisionComponent.pushStrength = 0.0f;

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getBody().getUserData() instanceof Entity && fixtureB.getBody().getUserData() instanceof Entity) {
            Entity entityA = (Entity) fixtureA.getBody().getUserData();
            Entity entityB = (Entity) fixtureB.getBody().getUserData();

            CollisionComponent collisionComponentA = Mapper.COLLISION_MAPPER.get(entityA);
            CollisionComponent collisionComponentB = Mapper.COLLISION_MAPPER.get(entityB);

            Vector2 collisionNormal = manifold.getLocalNormal();
            collisionComponentA.collisionNormal = collisionNormal.cpy();
            collisionComponentB.collisionNormal = collisionNormal.cpy();
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
