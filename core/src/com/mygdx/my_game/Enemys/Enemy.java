package com.mygdx.my_game.Enemys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.my_game.Multi_touch.Touch_Control;

/**
 * Created by Android on 12.11.2016.
 */

public abstract class Enemy extends Sprite {
    protected World world;
    protected Touch_Control control;
    public Body body2;
    protected float x, y;
    protected Vector2 impulseVector;

    public Enemy(Touch_Control control, float x , float y){
        this.world = control.getWorld();
        this.control = control;
        this.x = x;
        this.y = y;
        impulseVector = new Vector2(8f * 100,0);
        setPosition(x,y);
        defineEnemy();
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);

    public abstract void onHit();

    public void changeVelocity(boolean x, boolean y){
        if(x)
            impulseVector.x = -impulseVector.x;
        if(y){
            impulseVector.y = -impulseVector.y;
        }
    }
}
