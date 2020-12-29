package hu.cehessteg.ballgame.Stage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


import hu.cehessteg.ballgame.Hud.Logo;
import hu.cehessteg.ballgame.Hud.OptionSwitch;
import hu.cehessteg.ballgame.Hud.OptionSwitchType;
import hu.cehessteg.ballgame.Hud.TextBox;
import hu.cehessteg.ballgame.SoundManager;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

import static hu.cehessteg.ballgame.BallGame.muted;
import static hu.cehessteg.ballgame.BallGame.preferences;
import static hu.cehessteg.ballgame.Stage.InfoStage.BACKBUTTON_TEXTURE;

public class OptionsStage extends PrettyStage {
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(TextBox.class,assetList);
        assetList.collectAssetDescriptor(Logo.class,assetList);
    }

    public static int ballType = preferences.getInteger("ballType");
    public static int ballCount = preferences.getInteger("ballCount");

    private Logo optionsLogo;

    private OneSpriteStaticActor backButton;
    private OptionSwitch ballTypeButton;
    private OptionSwitch ballCountButton;
    private OptionSwitch muteButton;

    private boolean setBack;

    public OptionsStage(MyGame game) {
        super(game);
    }

    @Override
    public void assignment() {
        SoundManager.assign();
        if(!muted && SoundManager.menuMusic != null)
            SoundManager.menuMusic.play();
        setBack = false;
        backButton = new OneSpriteStaticActor(game,BACKBUTTON_TEXTURE);
        ballTypeButton = new OptionSwitch(game, OptionSwitchType.BALLTYPE);
        ballCountButton = new OptionSwitch(game, OptionSwitchType.BALLCOUNT);
        muteButton = new OptionSwitch(game, OptionSwitchType.MUTE);
        optionsLogo = new Logo(game, Logo.LogoType.OPTIONS);
    }

    @Override
    public void setSizes() {
        backButton.setSize(180,180);
    }

    @Override
    public void setPositions() {
        backButton.setRotation(180);
        backButton.setPosition(getViewport().getWorldWidth() - backButton.getWidth()-16,16);
        optionsLogo.setPosition(getViewport().getWorldWidth()/2 - optionsLogo.getWidth()/2, getViewport().getWorldHeight() - optionsLogo.getHeight()*1.15f);
        ballTypeButton.setPosition(getViewport().getWorldWidth()/2-ballTypeButton.getWidth()/2,getViewport().getWorldHeight()*0.52f);
        ballCountButton.setPosition(getViewport().getWorldWidth()/2-ballCountButton.getWidth()/2,getViewport().getWorldHeight()*0.32f);
        muteButton.setPosition(getViewport().getWorldWidth()/2-muteButton.getWidth()/2,getViewport().getWorldHeight()*0.7f);
    }

    @Override
    public void addListeners() {
        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                preferences.putInteger("ballType",ballType);
                preferences.putInteger("ballCount",ballCount);
                preferences.putBoolean("muted",muted);
                preferences.flush();
                setBack = true;
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(optionsLogo);
        addActor(ballCountButton);
        addActor(ballTypeButton);
        addActor(muteButton);
        addActor(backButton);


    }
    //endregion
    //region Act metódusai
    float alpha = 0;
    float bgAlpha = 1;
    @Override
    public void act(float delta) {
        super.act(delta);
        if(!setBack) fadeIn();
        else fadeOut();
    }

    /**
     * Áttűnéssel jönnek be az actorok
     * **/
    private void fadeIn(){
        if (alpha < 0.95) alpha += 0.025;
        else alpha = 1;
        setAlpha();
    }

    /**
     * Áttűnéssel mennek ki az actorok
     * **/
    private void fadeOut(){
        if (alpha > 0.05) {
            setAlpha();
            alpha -= 0.05;
        } else {
            //Ha már nem látszanak akkor megyünk vissza a menübe
            alpha = 0;
            setAlpha();
            game.setScreenBackByStackPopWithPreloadAssets(new LoadingStage(game));
            addTimer(new TickTimer(1,false,new TickTimerListener(){
                @Override
                public void onTick(Timer sender, float correction) {
                    super.onTick(sender, correction);
                    setBack = false;
                }
            }));
        }
    }

    /**
     * Actorok átlátszóságának egyidejű beállítása
     * **/
    private void setAlpha(){
        optionsLogo.setAlpha(alpha);
        backButton.setAlpha(alpha);
        muteButton.setAlpha(alpha);
        ballTypeButton.setAlpha(alpha);
        ballCountButton.setAlpha(alpha);
    }
    //endregion
}
