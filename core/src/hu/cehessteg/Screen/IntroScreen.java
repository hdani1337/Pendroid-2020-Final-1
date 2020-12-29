package hu.cehessteg.ballgame.Screen;

import hu.cehessteg.ballgame.BallGame;
import hu.cehessteg.ballgame.Stage.IntroStage;
import hu.cehessteg.ballgame.Stage.WeatherBackground;
import hu.cehessteg.ballgame.Stage.WeatherForeGround;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.PermanentTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.PermanentTimerListener;

import static hu.cehessteg.ballgame.BallGame.preferences;
import static hu.cehessteg.ballgame.BallGame.weatherAct;
import static hu.cehessteg.ballgame.BallGame.weatherBackground;
import static hu.cehessteg.ballgame.BallGame.weatherCalculation;
import static hu.cehessteg.ballgame.BallGame.weatherForeGround;

public class IntroScreen extends MyScreen {
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(IntroStage.class,assetList);
        assetList.collectAssetDescriptor(WeatherBackground.class, assetList);
        assetList.collectAssetDescriptor(WeatherForeGround.class, assetList);
    }

    public IntroScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        ((BallGame)game).createWeather();
        addTimer(weatherAct);
        addStage(weatherBackground,0,false);
        addStage(weatherForeGround,1,false);
        addStage(new IntroStage(game),2,false);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
