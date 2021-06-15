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

public class Player extends Enemy {
    private float stateTime = 0;
    private Animation animation;
    private Array<TextureRegion> frames;
    public boolean setDestroy;
    public boolean destroyed;
    int k = 0;

    public Player( Touch_Control control, float x, float y) {
        super(control,x, y);
        frames = new Array<TextureRegion>();

        for (int i = 0; i < 2;i++)
        {
            frames.add(new TextureRegion(control.getAtlas().findRegion("goomba"),i * 72, 0, 65, 65));
        }
        animation = new Animation(0.2f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),36,36);
        setDestroy = false;
        destroyed = false;
    }

    @Override
    public void defineEnemy() {
        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.DynamicBody;

        body2 = world.createBody(def);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(15,15);

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
        k++;
        if(k == 6) {
            setDestroy = true;
        }
    }

    public void update(float dt) {
        stateTime += dt;
        if(setDestroy && !destroyed){
            world.destroyBody(body2);
            destroyed = true;
            stateTime = 0;
        }
        else if(!destroyed) {
            body2.setLinearVelocity(impulseVector);
            setPosition(body2.getPosition().x - getWidth() / 2, body2.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) animation.getKeyFrame(stateTime, true));
        }
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 0){
            super.draw(batch);
        }
    }
}

