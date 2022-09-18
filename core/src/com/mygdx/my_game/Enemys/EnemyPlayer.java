package com.mygdx.my_game.Enemys;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.my_game.Multi_touch.Touch_Control;
import com.mygdx.my_game.MyGame;

/**
 * Created by Android on 16.11.2016.
 */

public class EnemyPlayer extends Enemy {
    private final Array<TextureRegion> frames = new Array<>();
    private Animation animation;
    public boolean isDestroy = false;
    public boolean destroyed = false;
    private int hitCount = 0;
    private float stateTime = 0;

    public EnemyPlayer(Touch_Control control, float x, float y) {
        super(control, x, y);
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(control.getAtlas().findRegion("goombas"), i * 72, 0, 65, 65));
        }
        animation = new Animation(0.2f, frames);
        setBounds(getX(), getY(), 36, 36);
    }

    @Override
    public void defineEnemy() {
        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.DynamicBody;

        body2 = world.createBody(def);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(15, 15);

        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = MyGame.ENEMY_BIT;
        fixtureDef.filter.maskBits = MyGame.BRICKS_BIT |
                MyGame.GROUND_BIT |
                MyGame.DEFAULT_BIT |
                MyGame.ENEMY_BIT |
                MyGame.BULLET_BIT |
                MyGame.HERO_BIT;

        body2.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void onHit() {
        hitCount++;
        if (hitCount == 6) {
            isDestroy = true;
        }
    }

    public void update(float dt) {
        stateTime += dt;
        if (!destroyed) {
            if (isDestroy) {
                world.destroyBody(body2);
                destroyed = true;
                stateTime = 0;
            } else {
                body2.setLinearVelocity(impulseVector);
                setPosition(body2.getPosition().x - getWidth() / 2, body2.getPosition().y - getHeight() / 2);
                setRegion((TextureRegion) animation.getKeyFrame(stateTime, true));
            }
        }
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 0) {
            super.draw(batch);
        }
    }
}

