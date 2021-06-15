package com.mygdx.my_game.Shoot;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.my_game.Multi_touch.Touch_Control;

/**
 * Created by Android on 20.11.2016.
 */

public abstract class BulletRender extends Sprite {

    protected World world;
    protected Touch_Control control;
    protected float x, y;
    public Body body2;
    protected Vector2 impulseVector;
    protected float stateTime = 0;
    protected float time = 4f;

    public BulletRender(Touch_Control control, float x , float y, boolean heroRunning){
        this.world = control.getWorld();
        this.control = control;
        this.x = x;
        this.y = y;
        impulseChange(heroRunning);
        setPosition(x,y);
        defineObject();
    }

    public void impulseChange(boolean changeImpulse){
        if(changeImpulse){

            impulseVector = new Vector2(3f * 100, 0);
        }else{

            impulseVector = new Vector2(-3f * 100, 0);
        }

    }
    public abstract void defineObject();
}
