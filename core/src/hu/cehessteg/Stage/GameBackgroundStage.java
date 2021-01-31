package hu.cehessteg.Stage;

import com.badlogic.gdx.utils.viewport.Viewport;

import hu.cehessteg.Actor.GridActor;
import hu.cehessteg.TetrisClasses.Board;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.PrettySimpleStage;

public class GameBackgroundStage extends PrettyStage {
    public GameBackgroundStage(MyGame game) {
        super(new ResponseViewport(Board.BOARD_WIDTH), game);
    }

    @Override
    public void assignment() {

    }

    @Override
    public void setSizes() {

    }

    @Override
    public void setPositions() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        for (int i = 0; i < getViewport().getWorldHeight(); i++) {
            for (int j = 0; j < Board.BOARD_WIDTH; j++) {
                addActor(new GridActor(game,j,i));
            }
        }

        System.out.println(getViewport().getWorldHeight());
    }
}
