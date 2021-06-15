package com.mygdx.my_game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.my_game.Multi_touch.Touch_Control;
import com.mygdx.my_game.MyGame;
import com.mygdx.my_game.Seenc.Hud;

/**
 * Created by Android on 27.11.2016.
 */

public class Level_Screen_Two implements Screen{
    private MyGame game1;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;
    private Touch_Control touchControl;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public Level_Screen_Two(MyGame game1) {
        this.game1 = game1;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT,gamecam);
        hud = new Hud(game1.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("screen_map/demo_map5.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gameport.getWorldWidth() / 2 , gameport.getWorldHeight() / 2, 0);
        touchControl = new Touch_Control(map);
        Hud.levelWorld(2);
    }

    public void handleInput(float dt){

        touchControl.move_camera(dt,gamecam,map);
    }

    public void update(float dt){
        handleInput(dt);

        gamecam.update();
        touchControl.updateCamera(dt,gamecam);
        renderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game1.batch.setProjectionMatrix(gamecam.combined);
        game1.batch.begin();
        touchControl.render(game1.batch,gamecam);
        game1.batch.end();

        game1.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        Hud.setLevel(3);

        if(touchControl.isGameOver()){
            game1.setScreen(new GameOverScreen(game1));
        }
        if(touchControl.isFindPosition() && Hud.level == 3){
            game1.setScreen(new NextLevel(game1));
        }
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);

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
        game1.batch.dispose();
        touchControl.dispose();
        renderer.dispose();
        hud.dispose();
    }
}
