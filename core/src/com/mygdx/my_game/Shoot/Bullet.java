package com.mygdx.my_game.Shoot;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.my_game.Multi_touch.Touch_Control;
import com.mygdx.my_game.MyGame;
import com.mygdx.my_game.Seenc.Hud;

/**
 * Created by Name on 17.11.2017.
 */

public class Bullet extends BulletRender {

    private Animation animation;
    private Array<TextureRegion> frames;

    public Bullet(Touch_Control control, float x, float y, boolean heroRunning) {
        super(control, x, y, heroRunning);

        frames = new Array<TextureRegion>();

        for (int i = 0; i < 1;i++)
        {
            frames.add(new TextureRegion(control.getAtlas().findRegion("bullet"),i * 55, 0, 16, 9));
        }
        animation = new Animation(0.3f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),16,9);
    }

    @Override
    public void defineObject() {

        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.DynamicBody;
        def.gravityScale = 1f;

        body2 = world.createBody(def);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(6,3);

        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = MyGame.BULLET_BIT;
        fixtureDef.filter.maskBits = MyGame.BRICKS_BIT |
                MyGame.GROUND_BIT |
                MyGame.DEFAULT_BIT |
                MyGame.ENEMY_BIT;

        body2.createFixture(fixtureDef).setUserData(this);
    }

    public boolean isDead(){
        if(time < 0 || Hud.bullet){
            Hud.bullet = false;
            return true;
        }
        return false;
    }

    public void update(float dt) {
        stateTime += dt;
        body2.setLinearVelocity(impulseVector);
        setPosition(body2.getPosition().x - getWidth() / 2,body2.getPosition().y - getHeight() / 2);
        setRegion((TextureRegion) animation.getKeyFrame(stateTime,true));
        time -= dt;
    }

    public void destroyedBody() {
        world.destroyBody(body2);

    }

}
