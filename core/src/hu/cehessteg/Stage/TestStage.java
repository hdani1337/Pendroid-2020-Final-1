package hu.cehessteg.Stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.ArrayList;

import hu.cehessteg.Actor.BlockActor;
import hu.cehessteg.TetrisClasses.Board;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

public class TestStage extends PrettyStage {
    ArrayList<BlockActor> blockActors;
    Board board;
    boolean controllable;

    public TestStage(MyGame game) {
        super(game);
    }

    @Override
    public void assignment() {
        board = new Board();
        blockActors = new ArrayList<>();
        for (int x = 0; x < board.BOARD_WIDTH; x++)
            for (int y = 0; y < board.BOARD_HEIGHT; y++)
                blockActors.add(new BlockActor(game, board, new Vector2(x, y)));
        controllable = true;
    }

    @Override
    public void setSizes() {

    }

    @Override
    public void setPositions() {

    }

    @Override
    public void addListeners() {
        addListener(new InputListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(controllable) {
                    board.control(event.getCharacter() + "");
                    controllable = false;
                    addTimer(new TickTimer(0.1f, false, new TickTimerListener() {
                        @Override
                        public void onStop(Timer sender) {
                            super.onStop(sender);
                            controllable = true;
                        }
                    }));
                }
                return super.keyUp(event, keycode);
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        for (BlockActor b : blockActors)
            addActor(b);

        addTimer(new TickTimer(0.25f,true,new TickTimerListener(){
            @Override
            public void onRepeat(TickTimer sender) {
                super.onRepeat(sender);
                board.update();
                ArrayList<Vector2> currentCoordinates = new ArrayList<>();
                for (int i = 0; i < 4; i++)
                    currentCoordinates.add(new Vector2(board.curX + board.curPiece.x(i),board.curY - board.curPiece.y(i)));

                for (BlockActor b : blockActors) {
                    boolean same = false;
                    for (Vector2 v : currentCoordinates) {
                        if(v.x == b.position.x && v.y == b.position.y){
                            same = true;
                            break;
                        }
                    }

                    if(same) b.update(board.curPiece.getShape());
                    else b.update();
                }
            }
        }));
    }
}
