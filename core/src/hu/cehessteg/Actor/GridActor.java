package hu.cehessteg.Actor;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class GridActor extends OneSpriteStaticActor {
    public GridActor(MyGame game, int x, int y) {
        super(game, "pic/grid.png");
        setSize(1,1);
        setPosition(x,y);
    }
}
