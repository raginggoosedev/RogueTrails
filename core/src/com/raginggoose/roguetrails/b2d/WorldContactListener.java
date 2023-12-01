package com.raginggoose.roguetrails.b2d;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.CollisionComponent;

/**
 * Handles collision contacts in the game
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Get the two fixtures that have collided
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        // Log the collision
        Gdx.app.log("World Contact Listener", fA.getBody().getType() + " has hit " + fB.getBody().getType());

        if (fA.getBody().getUserData() instanceof Entity && fB.getBody().getUserData() instanceof Entity) {
            // Entity-entity collision has occurred
            Entity e1 = (Entity) fA.getBody().getUserData();
            Entity e2 = (Entity) fB.getBody().getUserData();

            // Initialize the collision in e1's collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e1);
            collisionComponent.collisionBody = fB.getBody();
            collisionComponent.collided = true;

            // Initialize the collision in e2's collision component
            collisionComponent = Mapper.COLLISION_MAPPER.get(e2);
            collisionComponent.collisionBody = fA.getBody();
            collisionComponent.collided = true;

        } else if (fA.getBody().getUserData() instanceof Entity) {
            // Entity-boundary collision
            Entity e = (Entity) fA.getBody().getUserData();


            // Initialize the collision in e's collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e);
            collisionComponent.collisionBody = fB.getBody();
            collisionComponent.collided = true;
        } else if (fB.getBody().getUserData() instanceof Entity) {
            // Boundary-entity collision
            Entity e = (Entity) fB.getBody().getUserData();

            // Initialize the collision in e's collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e);
            collisionComponent.collisionBody = fA.getBody();
            collisionComponent.collided = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Get the fixtures that have ended contact
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        // Log the contact ending
        Gdx.app.log("World Contact Listener", fA.getBody().getType() + " ended contact with " + fB.getBody().getType());

        if (fA.getBody().getUserData() instanceof Entity && fB.getBody().getUserData() instanceof Entity) {
            // Entity-entity collision
            Entity e1 = (Entity) fA.getBody().getUserData();
            Entity e2 = (Entity) fB.getBody().getUserData();

            // End the contact in e1's collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e1);
            collisionComponent.collided = false;
            collisionComponent.collisionBody = null;

            // End the contact in e2's collision component
            collisionComponent = Mapper.COLLISION_MAPPER.get(e2);
            collisionComponent.collided = false;
            collisionComponent.collisionBody = null;

        } else if (fA.getBody().getUserData() instanceof Entity) {
            // Entity-boundary collision
            Entity e = (Entity) fA.getBody().getUserData();

            // End the contact in e's collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e);
            collisionComponent.collided = false;
            collisionComponent.collisionBody = null;

        } else if (fB.getBody().getUserData() instanceof Entity) {
            // Boundary-entity collision
            Entity e = (Entity) fB.getBody().getUserData();

            // End the contact in e's collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(e);
            collisionComponent.collided = false;
            collisionComponent.collisionBody = null;

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        // Get the fixtures that have had contact
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getBody().getUserData() instanceof Entity && fixtureB.getBody().getUserData() instanceof Entity) {
            // Entity-entity collision
            Entity entityA = (Entity) fixtureA.getBody().getUserData();
            Entity entityB = (Entity) fixtureB.getBody().getUserData();

            // Get the entities' collision components
            CollisionComponent collisionComponentA = Mapper.COLLISION_MAPPER.get(entityA);
            CollisionComponent collisionComponentB = Mapper.COLLISION_MAPPER.get(entityB);

            // Get the normal force of the collision and copy it to the collision components
            Vector2 collisionNormal = manifold.getLocalNormal();
            collisionComponentA.collisionNormal = collisionNormal.cpy();
            collisionComponentB.collisionNormal = collisionNormal.cpy();
        } else if (fixtureA.getBody().getUserData() instanceof Entity) {
            // Entity-boundary collision
            Entity entity = (Entity) fixtureA.getBody().getUserData();

            // Get the entity's collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);

            // Get the normal force of the collision and copy it to the collision component
            Vector2 collisionNormal = manifold.getLocalNormal();
            collisionComponent.collisionNormal = collisionNormal.cpy();
        } else if (fixtureB.getBody().getUserData() instanceof Entity) {
            // Boundary-entity collision
            Entity entity = (Entity) fixtureB.getBody().getUserData();

            // Get the entity's collision component
            CollisionComponent collisionComponent = Mapper.COLLISION_MAPPER.get(entity);

            // Get the normal force of the collision and copy it to the collision component
            Vector2 collisionNormal = manifold.getLocalNormal();
            collisionComponent.collisionNormal = collisionNormal.cpy();
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        // Currently no use for this
    }
}
