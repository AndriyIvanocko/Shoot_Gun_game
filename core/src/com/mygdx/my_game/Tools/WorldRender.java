package com.mygdx.my_game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.my_game.Enemys.EnemyPlayer;
import com.mygdx.my_game.Multi_touch.Touch_Control;

/**
 * Created by Android on 29.10.2016.
 */

public class WorldRender {
    private final Array<EnemyPlayer> goombas = new Array<>();
    public WorldRender(Touch_Control control) {
        TiledMap map = control.getMap();
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Bricks(control,rectangle);
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Ground(control,rectangle);
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            goombas.add(new EnemyPlayer(control,rectangle.getX(),rectangle.getY()));
        }
    }

    public Array<EnemyPlayer> getGoombas() {
        return goombas;
    }
}
