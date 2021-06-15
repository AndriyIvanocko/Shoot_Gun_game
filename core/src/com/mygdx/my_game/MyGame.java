package com.mygdx.my_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.my_game.Screens.PlayScreen;

public class MyGame extends Game {
    public SpriteBatch batch;

    public static final int V_WIDTH = 800;

    public static final int V_HEIGHT = 480;
    public static int level = 1;

    public static final short DEFAULT_BIT = 1;
    public static final short BULLET_BIT = 2;
    public static final short BRICKS_BIT = 4;
    public static final short GROUND_BIT = 8;
    public static final short ENEMY_BIT = 16;
    public static final short HERO_BIT = 32;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
