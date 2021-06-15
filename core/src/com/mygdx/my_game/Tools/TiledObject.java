package com.mygdx.my_game.Tools;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.my_game.Multi_touch.Touch_Control;

/**
 * Created by Android on 29.10.2016.
 */

public abstract class TiledObject extends Sprite{
    protected Body body;
    protected Rectangle rectangle;
    protected World world;
    protected TiledMap map;

    protected Fixture fixture;

    public TiledObject(Touch_Control control, Rectangle rectangle) {
        this.world = control.getWorld();
        this.map = control.getMap();
        this.rectangle = rectangle;

        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();

        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + (rectangle.getHeight() / 2));

        body = world.createBody(def);

        shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
        fDef.shape = shape;

        fixture = body.createFixture(fDef);
    }

    public abstract void hit();

    public void setCategoryFilter(short categoryFilter){
        Filter filter = new Filter();
        filter.categoryBits = categoryFilter;
        fixture.setFilterData(filter);
    }
}
