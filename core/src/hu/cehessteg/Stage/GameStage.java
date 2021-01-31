package hu.cehessteg.Stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import java.util.ArrayList;

import hu.cehessteg.Actor.BlockActor;
import hu.cehessteg.TetrisClasses.Board;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.PositionRule;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.PrettySimpleStage;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

public class GameStage extends PrettySimpleStage {
    private ArrayList<BlockActor> blockActors;
    private ArrayList<BlockActor> currentTetromino;
    private OneSpriteStaticActor controlActor;
    private Board board;
    private boolean controllable;

    public static int point;

    public GameStage(MyGame game) {
        super(new ResponseViewport(10),game);
    }

    @Override
    public void assignment() {
        board = new Board();
        board.BOARD_HEIGHT = (int) getViewport().getWorldHeight();
        board.start();
        blockActors = new ArrayList<>();
        currentTetromino = new ArrayList<>();
        for (int x = 0; x < board.BOARD_WIDTH; x++)
            for (int y = 0; y < board.BOARD_HEIGHT; y++)
                blockActors.add(new BlockActor(game, board, new Vector2(x, y),world));
        controllable = true;

        for (int i = 0; i < 4; i++)
            currentTetromino.add(new BlockActor(game, board, new Vector2(board.curX + board.curPiece.x(i),board.curY - board.curPiece.y(i)),world));

        controlActor = new OneSpriteStaticActor(game, BlockActor.BLOCK_TEXTURE);
        controlActor.setColor(0,0,0,0);
    }

    @Override
    public void setSizes() {
        controlActor.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
    }

    @Override
    public void setPositions() {

    }

    @Override
    public void addListeners() {
        Vector2 dragStart = new Vector2(0,0);

        controlActor.addListener(new DragListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if(x-dragStart.x > 2){
                    board.control("d");
                }else if(x-dragStart.x < -2){
                    board.control("a");
                }else if(y-dragStart.y < -2.5){
                    board.control(" ");
                }else if(y-dragStart.y < 2.5){
                    if(controllable) {
                        board.control("s");
                        moved();
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dragStart.x = x;
                dragStart.y = y;
                return super.touchDown(event, x, y, pointer, button);
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
        for (BlockActor b : currentTetromino)
            addActor(b);
        addActor(controlActor);
    }



    @Override
    public void afterInit() {
        super.afterInit();
        addTimers();
    }

    private void addTimers(){
        addTimer(new TickTimer(0.25f,true,new TickTimerListener(){
            @Override
            public void onRepeat(TickTimer sender) {
                super.onRepeat(sender);
                if(!board.isGameOver) {
                    board.update();
                    for (BlockActor b : blockActors) b.update();
                    updateCurrentTetromino();
                    point = board.numLinesRemoved;
                }
            }
        }));
    }

    private void updateCurrentTetromino(){
        ArrayList<Vector2> newCoordinates = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            newCoordinates.add(new Vector2(board.curX + board.curPiece.x(i),board.curY - board.curPiece.y(i)));
            currentTetromino.get(i).position.x = newCoordinates.get(i).x*currentTetromino.get(i).size;
            currentTetromino.get(i).position.y = newCoordinates.get(i).y*currentTetromino.get(i).size;
            currentTetromino.get(i).update(board.curPiece.getShape());
            ((SimpleWorldHelper)currentTetromino.get(i).getActorWorldHelper()).getBody().moveToFixTime(currentTetromino.get(i).position.x,currentTetromino.get(i).position.y,0.25f,PositionRule.LeftBottom);
        }
    }

    private void moved(){
        controllable = false;
        addTimer(new TickTimer(0.1f, false, new TickTimerListener() {
            @Override
            public void onStop(Timer sender) {
                super.onStop(sender);
                controllable = true;
            }
        }));
    }
}
