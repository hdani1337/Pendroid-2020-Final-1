package hu.cehessteg.Actor;

import hu.cehessteg.TetrisClasses.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class NextActor extends OneSpriteStaticActor {
    public static final String[] hashes = {"tetrominoes/z.png",
            "tetrominoes/s.png",
            "tetrominoes/line.png",
            "tetrominoes/t.png",
            "tetrominoes/kocka.png",
            "tetrominoes/l.png",
            "tetrominoes/mirroredL.png"};

    public float size = 0.8f;
    public ShapeType shapeType;

    public NextActor(MyGame game) {
        super(game, hashes[0]);
    }

    public void update(ShapeType shapeType){
        if(shapeType != ShapeType.NoShape) {
            this.shapeType = shapeType;
            int index = -1;
            for (ShapeType s : ShapeType.values()) {
                if (s == shapeType) break;
                else index++;
            }
            sprite.setTexture(game.getMyAssetManager().getTexture(hashes[index]));
            setSize(game.getMyAssetManager().getTexture(hashes[index]).getWidth() * size, game.getMyAssetManager().getTexture(hashes[index]).getHeight() * size);
        }
    }
}
