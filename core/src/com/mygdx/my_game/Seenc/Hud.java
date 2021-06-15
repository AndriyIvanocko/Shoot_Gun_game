package com.mygdx.my_game.Seenc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.my_game.MyGame;

/**
 * Created by Android on 22.09.2016.
 */
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    public static int health = 100;
    public static int world = 1;
    public static int level = 1;
    public static boolean enemyCount = false;
    public static boolean bullet = false;

    private static Label heathLabel;
    private static Label levelLabel;
    private Label heroLabel;
    private Label worldLabel;

    public Hud(SpriteBatch spriteBatch) {

        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        heathLabel = new Label(String.format("%03d",health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        heroLabel = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(String.format("%01d - 3",world), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(heroLabel).expandX();
        table.add(worldLabel).expandX();
        table.row();
        table.add(heathLabel).expandX();
        table.add(levelLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();

    }

    public static void healthBar(int value){
        health -= value;
        heathLabel.setText(String.format("%03d",health));
    }

    public static void levelWorld(int value){
        world = value;
        levelLabel.setText(String.format("%01d - 3",world));
    }

    public static void setHealth(int value){
        health = value;
        heathLabel.setText(String.format("%03d",health));

    }

    public static int setLevel(int value){
        level = value;
        return level;
    }
}
