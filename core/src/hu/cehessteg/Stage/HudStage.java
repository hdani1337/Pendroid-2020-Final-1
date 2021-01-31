package hu.cehessteg.Stage;

import hu.cehessteg.Hud.Pause;
import hu.cehessteg.Hud.TextBox;
import hu.cehessteg.TetrisClasses.Board;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;

public class HudStage extends PrettyStage {
    public static AssetList assetList = new AssetList();
    static {
        //add assets here
    }

    //public static BallStage stage;//Hátha kell a GameStageből valami
    private Pause pause;
    private TextBox scoreBoard;
    private TextBox nextPiece;

    public HudStage(MyGame game) {
        super(game);
    }

    @Override
    public void assignment() {
        pause = new Pause(game);
        scoreBoard = new TextBox(game,"0",TextBox.RETRO_FONT,1.5f);
        nextPiece = new TextBox(game,"Next: -NULL-",TextBox.RETRO_FONT);
    }

    @Override
    public void setSizes() {
        pause.setSize(100,100);
    }

    @Override
    public void setPositions() {
        pause.setPosition(getViewport().getWorldWidth()-pause.getWidth()-15,getViewport().getWorldHeight()-pause.getHeight()-15);
        scoreBoard.setPosition(getViewport().getWorldWidth()/2-scoreBoard.getWidth()/2,getViewport().getWorldHeight()-scoreBoard.getHeight()-15);
        nextPiece.setPosition(15,getViewport().getWorldHeight()-nextPiece.getHeight()-15);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(scoreBoard);
        addActor(pause);
        addActor(nextPiece);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(scoreBoard.text != GameStage.point+"") {
            scoreBoard.setText(GameStage.point + "");
            setPositions();
        }
        if(nextPiece.text != "Next: " + Board.nextPiece.name()){
            nextPiece.setText("Next: " + Board.nextPiece.name());
            setPositions();
        }
    }
}
