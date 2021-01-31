package hu.cehessteg.Stage;

import hu.cehessteg.Actor.NextActor;
import hu.cehessteg.Hud.Pause;
import hu.cehessteg.Hud.TextBox;
import hu.cehessteg.TetrisClasses.Board;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;

public class HudStage extends PrettyStage {
    public static AssetList assetList = new AssetList();
    static {
        //add assets here
    }

    //public static BallStage stage;//Hátha kell a GameStageből valami
    private Pause pause;
    private TextBox scoreBoard;
    private OneSpriteStaticActor frameActor;
    public static NextActor nextActor;

    public HudStage(MyGame game) {
        super(game);
    }

    @Override
    public void assignment() {
        pause = new Pause(game);
        scoreBoard = new TextBox(game,"  0  ",TextBox.RETRO_FONT,1.5f);
        frameActor = new OneSpriteStaticActor(game,"keret.png");
        nextActor = new NextActor(game);
    }

    @Override
    public void setSizes() {
        pause.setSize(120,120);
        frameActor.setSize((getViewport().getWorldWidth()/frameActor.getWidth())*frameActor.getWidth(),(getViewport().getWorldWidth()/frameActor.getWidth())*frameActor.getHeight());
    }

    @Override
    public void setPositions() {
        frameActor.setY(getViewport().getWorldHeight()-frameActor.getHeight());
        pause.setPosition(getViewport().getWorldWidth()-pause.getWidth()-36,frameActor.getY() + (getViewport().getWorldHeight()-frameActor.getY())/2 - pause.getHeight()/2);
        scoreBoard.setPosition(getViewport().getWorldWidth()/2-scoreBoard.getWidth()/2,frameActor.getY() + (getViewport().getWorldHeight()-frameActor.getY())/2 - scoreBoard.getHeight()/2);
        nextActor.setPosition(36,frameActor.getY() + (getViewport().getWorldHeight()-frameActor.getY())/2 - nextActor.getHeight()/2);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(frameActor);
        addActor(scoreBoard);
        addActor(pause);
        addActor(nextActor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(scoreBoard.text != "  "+GameStage.point+"  ") {
            scoreBoard.setText("  "+GameStage.point + "  ");
            setPositions();
        }
        if(nextActor.shapeType != Board.nextPiece) {
            nextActor.update(Board.nextPiece);
            nextActor.setPosition(36,frameActor.getY() + (getViewport().getWorldHeight()-frameActor.getY())/2 - nextActor.getHeight()/2);
        }
    }
}
