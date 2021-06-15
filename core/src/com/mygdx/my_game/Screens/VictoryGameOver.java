package com.mygdx.my_game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.my_game.MyGame;
import com.mygdx.my_game.Seenc.Hud;

/**
 * Created by Android on 29.11.2016.
 */
public class VictoryGameOver implements Screen {
    private Viewport viewport;
    private Stage stage;

    private MyGame game;

    public VictoryGameOver(MyGame game) {
        this.game = game;

        viewport = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOver = new Label("YOU VICTORY",style);
        Label click = new Label("Click to play again", style);
        table.add(gameOver).expandX();
        table.row();
        table.add(click).expandX();

        stage.addActor(table);
        Hud.setHealth(100);
        Hud.levelWorld(1);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            game.setScreen(new PlayScreen(game));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
