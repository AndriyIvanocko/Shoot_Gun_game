package com.mygdx.my_game.Multi_touch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.my_game.Enemys.Enemy;
import com.mygdx.my_game.Player.Hero;
import com.mygdx.my_game.Seenc.Hud;
import com.mygdx.my_game.Shoot.Bullet;
import com.mygdx.my_game.Tools.BulletListener;
import com.mygdx.my_game.Tools.WorldRender;

import java.util.ArrayList;

/**
 * Created by Android on 22.10.2016.
 */

public class Touch_Control {
    private final float SHOOT_TIMER = 0.25f;
    private float shoot = 0;

    //create multi touch controller
    private Rectangle leftButton,rightButton,upButton,shootButton,touch;
    private Sprite leftSprite,rightSprite,upSprite,shootSprite,downSprite;
    private Sprite left,right,up,shooting;
    private Texture buttonTexture;

    //to create world and render
    private World world;
    private Box2DDebugRenderer bd2render;
    private TiledMap map;
    private WorldRender render;
    //to create player and enemy and bullet
    private Hero hero;
    private TextureAtlas buttonAtlas;
//    private Array<Player> player;
    private ArrayList<Bullet> bullets;

    public Touch_Control(TiledMap map) {
        buttonAtlas = new TextureAtlas(Gdx.files.internal("resource/my_game"));
        this.map = map;
        world = new World(new Vector2(0, -20), true);
        world.setContactListener(new BulletListener());
        bd2render = new Box2DDebugRenderer();
        bd2render.setDrawBodies(false);
        render = new WorldRender(this);

        bullets = new ArrayList<Bullet>();

        hero = new Hero(world, this);

        buttonTexture = new Texture(Gdx.files.internal("multi_touch/touch_righ.png"));
        rightSprite = new Sprite(buttonTexture);
        buttonTexture = new Texture(Gdx.files.internal("multi_touch/touch_lef.png"));
        leftSprite = new Sprite(buttonTexture);
        buttonTexture = new Texture(Gdx.files.internal("multi_touch/touch_up.png"));
        upSprite = new Sprite(buttonTexture);
        buttonTexture = new Texture(Gdx.files.internal("multi_touch/shoot.png"));
        shootSprite = new Sprite(buttonTexture);
        buttonTexture = new Texture(Gdx.files.internal("multi_touch/touch_righ.png"));
        right = new Sprite(buttonTexture);
        buttonTexture = new Texture(Gdx.files.internal("multi_touch/touch_lef.png"));
        left = new Sprite(buttonTexture);
        buttonTexture = new Texture(Gdx.files.internal("multi_touch/touch_up.png"));
        up = new Sprite(buttonTexture);
        buttonTexture = new Texture(Gdx.files.internal("multi_touch/shoot.png"));
        shooting = new Sprite(buttonTexture);
        buttonTexture = new Texture(Gdx.files.internal("screen_map/down.png"));
        downSprite = new Sprite(buttonTexture, 0, 0, 0, 0);

        leftButton = new Rectangle(0, 0, 60, 60);
        rightButton = new Rectangle(0, 27, 60,60);
        upButton = new Rectangle(0, 27, 60, 60);
        shootButton = new Rectangle(0, 27, 60, 60);

        leftSprite.setSize(90, 90);
        rightSprite.setSize(90, 90);
        upSprite.setSize(90, 90);
        shootSprite.setSize(90,90);
        left.setSize(90,90);
        right.setSize(90,90);
        up.setSize(90,90);
        shooting.setSize(90,90);
    }

    public TextureAtlas getAtlas() {
        return buttonAtlas;

    }

    public void move_camera(float dt,OrthographicCamera camera,TiledMap map) {
        shoot += dt;
        if(hero.body.getLinearVelocity().y != 0 ) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && hero.body.getLinearVelocity().x >= -0.5f * 100) {
                hero.body.applyLinearImpulse(new Vector2(-0.5f * 100, 0), hero.body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && hero.body.getLinearVelocity().x <= 0.5f * 100) {
                hero.body.applyLinearImpulse(new Vector2(0.5f * 100, 0), hero.body.getWorldCenter(), true);
            }
        }
        else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  && hero.body.getLinearVelocity().x >= -6f * 100) {
                hero.body.applyLinearImpulse(new Vector2(-5f * 100, 0), hero.body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)  && hero.body.getLinearVelocity().x <= 6f * 100) {
                hero.body.applyLinearImpulse(new Vector2(5f * 100, 0), hero.body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) ) {
                hero.body.applyLinearImpulse(new Vector2(0, 5f * 100), hero.body.getWorldCenter(), true);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && shoot >= SHOOT_TIMER ){
            shoot = 0;
            if(hero.runningRight) {
                bullets.add(new Bullet(this,hero.body.getPosition().x + 25f,hero.body.getPosition().y - 5f, hero.runningRight));
            }
            else{
                bullets.add(new Bullet(this,hero.body.getPosition().x - 25f,hero.body.getPosition().y - 5f, hero.runningRight));
            }
        }
        if(Gdx.input.isTouched())
        {
            Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);

            touch = new Rectangle((int)touchPos.x,(int)touchPos.y,32,32 );
            if(hero.body.getLinearVelocity().y != 0 ) {
                if (touch.overlaps(leftButton) && hero.body.getLinearVelocity().x >= -0.5f * 100) {
                    hero.body.applyLinearImpulse(new Vector2(-0.5f * 100, 0), hero.body.getWorldCenter(), true);
                }
                if (touch.overlaps(rightButton) && hero.body.getLinearVelocity().x <= 0.5f * 100) {
                    hero.body.applyLinearImpulse(new Vector2(0.5f * 100, 0), hero.body.getWorldCenter(), true);
                }
            }
            else {
                if (touch.overlaps(leftButton) && hero.body.getLinearVelocity().x >= -6f * 100) {
                    hero.body.applyLinearImpulse(new Vector2(-5f * 100, 0), hero.body.getWorldCenter(), true);
                }
                if (touch.overlaps(rightButton) && hero.body.getLinearVelocity().x <= 6f * 100) {
                    hero.body.applyLinearImpulse(new Vector2(5f * 100, 0), hero.body.getWorldCenter(), true);
                }
                if (touch.overlaps(upButton)) {
                    hero.body.applyLinearImpulse(new Vector2(0, 5f * 100), hero.body.getWorldCenter(), true);
                }
            }

        }
        if(Gdx.input.isTouched())
        {
            Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);

            touch = new Rectangle((int) touchPos.x, (int) touchPos.y,32,32 );
            if(touch.overlaps(shootButton) && shoot >= SHOOT_TIMER )
            {
                shoot = 0;
                if(hero.runningRight) {
                    bullets.add(new Bullet(this,hero.body.getPosition().x + 25f,hero.body.getPosition().y - 5f, hero.runningRight));
                }
                else{
                    bullets.add(new Bullet(this,hero.body.getPosition().x - 25f,hero.body.getPosition().y - 5f, hero.runningRight));
                }
            }
        }
    }

    public void updateCamera(float dt,OrthographicCamera camera) {
        hero.update(dt);
        updateBullet(bullets,dt);
        world.step(1f/60f,6,2);
        for (Enemy enemy: render.getGoombas()) {
            enemy.update(dt);
        }

        if(hero.body.getPosition().x > 400 && hero.body.getPosition().x < 3430) {
            camera.position.x = hero.body.getPosition().x;
        }
        camera.position.y = hero.body.getPosition().y;
        multiTouchPosition();
    }

    public void multiTouchPosition(){
        if(hero.body.getPosition().x > 400 && hero.body.getPosition().x < 3430) {
            leftButton.x = (int) (hero.body.getPosition().x - 340);
            leftButton.y = (int) (hero.body.getPosition().y - 130);
            leftSprite.setX(hero.body.getPosition().x - 360);
            leftSprite.setY(hero.body.getPosition().y - 160);
            rightButton.x = (int) (hero.body.getPosition().x - 140);
            rightButton.y = (int) (hero.body.getPosition().y - 130);
            rightSprite.setX(hero.body.getPosition().x - 200);
            rightSprite.setY(hero.body.getPosition().y - 160);
            upButton.x = (int) (hero.body.getPosition().x + 335);
            upButton.y = (int) (hero.body.getPosition().y - 130);
            upSprite.setX(hero.body.getPosition().x + 315);
            upSprite.setY(hero.body.getPosition().y - 160);
            shootButton.x = (int) (hero.body.getPosition().x + 190);
            shootButton.y = (int) (hero.body.getPosition().y - 130);
            shootSprite.setX(hero.body.getPosition().x + 160);
            shootSprite.setY(hero.body.getPosition().y - 160);
        }
        if(hero.body.getPosition().x < 400){
            leftButton.x = 80;
            leftButton.y = (int) (hero.body.getPosition().y - 130);
            left.setX(40);
            left.setY(hero.body.getPosition().y - 160);
            rightButton.x = (220);
            rightButton.y = (int) (hero.body.getPosition().y - 130);
            right.setX(200);
            right.setY(hero.body.getPosition().y - 160);
            upButton.x = (750);
            upButton.y = (int) (hero.body.getPosition().y - 130);
            up.setX(715);
            up.setY(hero.body.getPosition().y - 160);
            shootButton.x = (610);
            shootButton.y = (int) (hero.body.getPosition().y - 130);
            shooting.setX(560);
            shooting.setY(hero.body.getPosition().y - 160);
        }
        if(hero.body.getPosition().x > 3430){

            leftButton.x = (3145);
            leftButton.y = (int) (hero.body.getPosition().y - 130);
            left.setX(3070);
            left.setY(hero.body.getPosition().y - 160);
            rightButton.x = (3260);
            rightButton.y = (int) (hero.body.getPosition().y - 130);
            right.setX(3230);
            right.setY(hero.body.getPosition().y - 160);
            upButton.x = (3750);
            upButton.y = (int) (hero.body.getPosition().y - 130);
            up.setX(3745);
            up.setY(hero.body.getPosition().y - 160);
            shootButton.x = (3610);
            shootButton.y = (int) (hero.body.getPosition().y - 130);
            shooting.setX(3590);
            shooting.setY(hero.body.getPosition().y - 160);
        }

    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        hero.draw(batch);
        renderBullet(bullets,batch);
        for (Enemy enemy: render.getGoombas()) {
            enemy.draw(batch);
        }
        if(hero.body.getPosition().x > 400 && hero.body.getPosition().x < 3430) {
            leftSprite.draw(batch);
            rightSprite.draw(batch);
            upSprite.draw(batch);
            shootSprite.draw(batch);
        }
        if(hero.body.getPosition().x < 400){
            left.draw(batch);
            right.draw(batch);
            up.draw(batch);
            shooting.draw(batch);
        }
        if(hero.body.getPosition().x > 3430){
            left.draw(batch);
            right.draw(batch);
            up.draw(batch);
            shooting.draw(batch);
        }
        downSprite.draw(batch);
        bd2render.render(world, camera.combined);
    }

    public void renderBullet(ArrayList<Bullet> bullets, SpriteBatch batch) {
        for (Bullet bullet: bullets) {
            bullet.draw(batch);
        }
    }

    public void updateBullet(ArrayList<Bullet> bullets, float dt){
        ArrayList<Bullet> bulletArray = new ArrayList<Bullet>();
        for (Bullet bullet: bullets) {
            bullet.update(dt);
            if(bullet.isDead())
            {
                bullet.destroyedBody();
                bulletArray.add(bullet);
            }
        }
        bullets.removeAll(bulletArray);
    }


    public void dispose() {
        world.dispose();
        bd2render.dispose();
    }

    public TiledMap getMap(){
        return map;

    }

    public World getWorld(){

        return world;

    }

    public boolean isGameOver(){
        if(Hud.health <= 0){
            return true;
        }
        return false;
    }

    public boolean isFindPosition(){
        if(hero.body.getPosition().x >= 3700 && hero.getStateTimer() > 3 ) {
            return true;
        }
        return false;
    }
}
