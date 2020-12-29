package hu.cehessteg.ballgame.Screen;

import hu.cehessteg.ballgame.Actor.Ball;
import hu.cehessteg.ballgame.Stage.BallStage;
import hu.cehessteg.ballgame.Stage.GameOverStage;
import hu.cehessteg.ballgame.Stage.HudStage;
import hu.cehessteg.ballgame.Stage.MenuStage;
import hu.cehessteg.ballgame.Stage.PauseStage;
import hu.cehessteg.ballgame.Stage.WeatherBackground;
import hu.cehessteg.ballgame.Stage.WeatherForeGround;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

import static hu.cehessteg.ballgame.BallGame.weatherAct;
import static hu.cehessteg.ballgame.BallGame.weatherBackground;
import static hu.cehessteg.ballgame.BallGame.weatherForeGround;

public class GameScreen extends MyScreen {
    public static AssetList assetList = new AssetList();
    static {
        //assetList.collectAssetDescriptor(CardStage.class, assetList);
        assetList.collectAssetDescriptor(HudStage.class, assetList);
        assetList.collectAssetDescriptor(GameOverStage.class, assetList);
        assetList.collectAssetDescriptor(PauseStage.class, assetList);
        assetList.collectAssetDescriptor(WeatherBackground.class, assetList);
        assetList.collectAssetDescriptor(Ball.class, assetList);
    }

    public GameScreen(MyGame game) {
        super(game);
    }

    public BallStage ballStage;

    @Override
    protected void afterAssetsLoaded() {
        addStage(weatherBackground,0,false);
        addTimer(weatherAct);
        //addStage(new TableStage(game),0,false);
        ballStage = new BallStage(game);
        HudStage.stage = ballStage;
        addStage(ballStage,1,true);
        addStage(weatherForeGround,1,false);
        addStage(new HudStage(game),2, true);
        addStage(new PauseStage(game),3, true);
        addStage(new GameOverStage(game),4, true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
