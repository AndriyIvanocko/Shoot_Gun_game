package com.mygdx.my_game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.my_game.Multi_touch.Touch_Control;
import com.mygdx.my_game.MyGame;

/**
 * Created by Android on 29.10.2016.
 */

public class Bricks extends TiledObject {

    public Bricks(Touch_Control control,Rectangle rectangle) {
        super(control,rectangle);
        fixture.setUserData(this);
        setCategoryFilter(MyGame.BRICKS_BIT);
    }

    @Override
    public void hit() {
        Gdx.app.log("Bricks","collision");

    }

}
