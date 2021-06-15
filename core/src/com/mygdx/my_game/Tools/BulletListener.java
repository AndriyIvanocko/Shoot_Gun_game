package com.mygdx.my_game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.my_game.Enemys.Enemy;
import com.mygdx.my_game.MyGame;
import com.mygdx.my_game.Seenc.Hud;

/**
 * Created by Android on 16.11.2016.
 */

public class BulletListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case MyGame.BRICKS_BIT | MyGame.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == MyGame.BRICKS_BIT) {
                    ((TiledObject) fixA.getUserData()).hit();
                    Hud.bullet = true;
                } else {
                    ((TiledObject) fixB.getUserData()).hit();
                    Hud.bullet = true;
                }
                break;
            case MyGame.ENEMY_BIT | MyGame.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == MyGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).onHit();
                    Hud.bullet = true;
                } else {
                    ((Enemy) fixB.getUserData()).onHit();
                    Hud.bullet = true;
                }
                break;
            case MyGame.ENEMY_BIT | MyGame.BRICKS_BIT:
                if (fixA.getFilterData().categoryBits == MyGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).changeVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).changeVelocity(true, false);
                }
                break;
            case MyGame.ENEMY_BIT | MyGame.HERO_BIT:
                if (fixA.getFilterData().categoryBits == MyGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).changeVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).changeVelocity(true, false);
                }
                Hud.healthBar(10);
                if (Hud.health <= 0) {
                    Gdx.app.log("Hero", "died");
                }
                break;
            case MyGame.ENEMY_BIT | MyGame.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).changeVelocity(true, false);
                ((Enemy) fixB.getUserData()).changeVelocity(true, false);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
