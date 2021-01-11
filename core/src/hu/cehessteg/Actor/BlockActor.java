package hu.cehessteg.Actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import hu.cehessteg.TetrisClasses.Board;
import hu.cehessteg.TetrisClasses.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class BlockActor extends OneSpriteStaticActor {
    public Board board;
    public int id;
    public Vector2 position;
    public int size = 64;

    public BlockActor(MyGame game, Board board, Vector2 position) {
        super(game, "badlogic.jpg");
        this.board = board;
        this.id = id;
        this.position = position;
        setSize(size,size);
        setPosition(position.x*size,position.y*size);
        update();
    }

    public void update(){
        update(board.shapeAt((int)position.x,(int)position.y));
    }

    public void update(ShapeType tetromino){
        switch (tetromino){
            case NoShape: default:
                setColor(Color.CLEAR);
                break;
            case ZShape:
                setColor(Color.TEAL);
                break;
            case SShape:
                setColor(Color.ORANGE);
                break;
            case LineShape:
                setColor(Color.PURPLE);
                break;
            case TShape:
                setColor(Color.BLUE);
                break;
            case SquareShape:
                setColor(Color.PINK);
                break;
            case LShape:
                setColor(Color.YELLOW);
                break;
            case MirroredLShape:
                setColor(Color.CYAN);
                break;

        }
    }
}
