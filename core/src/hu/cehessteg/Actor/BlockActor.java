package hu.cehessteg.Actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import hu.cehessteg.TetrisClasses.Board;
import hu.cehessteg.TetrisClasses.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleBodyType;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleWorld;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleWorldHelper;

public class BlockActor extends OneSpriteStaticActor {
    public static final String BLOCK_TEXTURE = "badlogic.jpg";
    public Board board;
    public Vector2 position;
    public float size = 1;

    public BlockActor(MyGame game, Board board, Vector2 position, SimpleWorld world) {
        super(game, BLOCK_TEXTURE);
        this.board = board;
        this.position = position;
        setSize(size,size);
        setPosition(position.x*size,position.y*size);
        setActorWorldHelper(new SimpleWorldHelper(world, this, hu.csanyzeg.master.MyBaseClasses.SimpleWorld.ShapeType.Rectangle, SimpleBodyType.Sensor));
        update();
    }

    public void update(){
        update(board.shapeAt((int)position.x,(int)position.y));
    }

    public void update(ShapeType tetromino){
        ((SimpleWorldHelper)getActorWorldHelper()).getActor().setColor(getTetrominoColor(tetromino));
    }

    public Color getTetrominoColor(ShapeType tetromino){
        switch (tetromino){
            case NoShape: default:
                return Color.CLEAR;
            case ZShape:
                return Color.TEAL;
            case SShape:
                return Color.ORANGE;
            case LineShape:
                return Color.PURPLE;
            case TShape:
                return Color.BLUE;
            case SquareShape:
                return Color.PINK;
            case LShape:
                return Color.YELLOW;
            case MirroredLShape:
                return Color.CYAN;
        }
    }
}
