package com.mygdx.my_game.Player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.my_game.Multi_touch.Touch_Control;
import com.mygdx.my_game.MyGame;

/**
 * Created by Android on 30.10.2016.
 */

public class Hero extends Sprite {

    private enum State{Running,Standing};
    public State currentState;
    public State previousState;
    private World world;
    public Body body;
    private TextureRegion region;
    private Animation runAnimation;
    private float stateTimer;
    public boolean runningRight;

    public Hero(World world, Touch_Control screen) {

        super(screen.getAtlas().findRegion("little"));
        this.world = world;
        currentState = State.Standing;
        previousState = State.Standing;

        stateTimer = 0;
        runningRight = true;

        createBody();
        region = new TextureRegion(getTexture(),0,0,50,52);
        setBounds(0,0,32,32);
        setRegion(region);

        Array<TextureRegion> array = new Array<TextureRegion>();
        for(int i = 1; i < 3; i++) {
            array.add(new TextureRegion(getTexture(), i * 55, 0, 50, 52));
        }
        runAnimation = new Animation(0.2f,array);
        array.clear();
    }

    public void update(float dt) {
        stateTimer += dt;
        setPosition(body.getPosition().x - getWidth() / 2,body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region1;
        switch (currentState)
        {
            case Running:
                region1 = (TextureRegion) runAnimation.getKeyFrame(stateTimer,true);
                break;
            case Standing:
                default:
                    region1 = region;
        }
        if((body.getLinearVelocity().x < 0 || !runningRight) && !region1.isFlipX())
        {
            region1.flip(true,false);
            runningRight = false;

        }
        if((body.getLinearVelocity().x > 0 || runningRight) && region1.isFlipX())
        {
            region1.flip(true,false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region1;
    }

    public State getState() {
        State fixState = null;
        if(body.getLinearVelocity().x != 0 && body.getLinearVelocity().y == 0) {
            fixState = State.Running;
        }else {
            fixState = State.Standing;
        }
        return fixState;
    }

    public void createBody(){
        BodyDef def = new BodyDef();
        def.position.set(50, 400);
        def.type = BodyDef.BodyType.DynamicBody;
        def.gravityScale = 10f;

        body = world.createBody(def);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(15f);

        fixtureDef.friction = 50f;
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = MyGame.HERO_BIT;
        fixtureDef.filter.maskBits = MyGame.BRICKS_BIT |
                                    MyGame.GROUND_BIT |
                                    MyGame.ENEMY_BIT |
                                    MyGame.DEFAULT_BIT;

        body.createFixture(fixtureDef);
    }

    public float getStateTimer(){
        return stateTimer;

    }
}
