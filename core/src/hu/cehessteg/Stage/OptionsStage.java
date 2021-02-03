package hu.cehessteg.Stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hu.cehessteg.Hud.Logo;
import hu.cehessteg.Hud.OptionSwitch;
import hu.cehessteg.Hud.OptionSwitchType;
import hu.cehessteg.Hud.TextBox;
import hu.cehessteg.SoundManager;
import hu.cehessteg.TetrisClasses.Board;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

import static hu.cehessteg.Stage.InfoStage.BACKBUTTON_TEXTURE;
import static hu.cehessteg.Stage.MenuStage.BACKGROUND_TEXTURE;
import static hu.cehessteg.TetrisGame.muted;
import static hu.cehessteg.TetrisGame.preferences;

public class OptionsStage extends PrettyStage {
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(TextBox.class,assetList);
        assetList.collectAssetDescriptor(Logo.class,assetList);
    }

    public static int difficulty = preferences.getInteger("difficulty");
    public static int size = preferences.getInteger("size");
    public static long highscore = preferences.getLong("highscore");

    private Logo optionsLogo;

    private OneSpriteStaticActor backButton;
    private OneSpriteStaticActor bg;
    private OptionSwitch muteButton;
    private Slider difficultySlider;
    private Slider sizeSlider;
    private TextBox sizeText;
    private TextBox difficultyText;

    private boolean setBack;

    public OptionsStage(MyGame game) {
        super(game);
    }

    @Override
    public void assignment() {
        bg = new OneSpriteStaticActor(game, BACKGROUND_TEXTURE);
        SoundManager.assign();
        if(!muted && SoundManager.menuMusic != null)
            SoundManager.menuMusic.play();
        setBack = false;
        if(difficulty == 0) difficulty = 1;
        if(size == 0) size = 10;
        backButton = new OneSpriteStaticActor(game,BACKBUTTON_TEXTURE);
        muteButton = new OptionSwitch(game, OptionSwitchType.MUTE);
        optionsLogo = new Logo(game, Logo.LogoType.OPTIONS);
        sizeText = new TextBox(game,"Pálya szélessége:\n"+size+"\n", TextBox.RETRO_FONT,1.25f);
        sizeSlider = new Slider(10, 25, 1, false, getSliderStyle());
        sizeSlider.setValue(size);
        sizeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                size = (int) sizeSlider.getValue();
                sizeText.setText("Pálya szélessége:\n"+size+"\n");
                setPositions();
            }
        });

        difficultyText = new TextBox(game,"Nehézség:\nNormál\n", TextBox.RETRO_FONT,1.25f);
        difficultySlider = new Slider(1, 4, 1, false, getSliderStyle());
        difficultySlider.setValue(difficulty);
        difficultySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultySliderOnChange();
            }
        });
        difficultySliderOnChange();
    }

    private void difficultySliderOnChange(){
        difficulty = (int) difficultySlider.getValue();
        String dif = "";
        if(difficulty == 1) dif = "Könnyü";
        else if(difficulty == 2) dif = "Normál";
        else if(difficulty == 3) dif = "Nehéz";
        else if(difficulty == 4) dif = "Lehetetlen";
        difficultyText.setText("Nehézség:\n"+dif+"\n");
        setPositions();
    }

    @Override
    public void setSizes() {
        backButton.setSize(180,180);
        sizeSlider.setWidth(300);
        difficultySlider.setWidth(200);
        bg.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
    }

    @Override
    public void setPositions() {
        backButton.setPosition(16,16);
        optionsLogo.setPosition(getViewport().getWorldWidth()/2 - optionsLogo.getWidth()/2, getViewport().getWorldHeight() - optionsLogo.getHeight()*1.15f);
        difficultySlider.setPosition(getViewport().getWorldWidth()/2-difficultySlider.getWidth()/2,getViewport().getWorldHeight()*0.425f);
        difficultyText.setPosition(difficultySlider.getX() + difficultySlider.getWidth()/2-difficultyText.getWidth()/2,difficultySlider.getY()-20);
        muteButton.setPosition(getViewport().getWorldWidth()/2-muteButton.getWidth()/2,getViewport().getWorldHeight()*0.625f);
        sizeSlider.setPosition(getViewport().getWorldWidth()/2-sizeSlider.getWidth()/2,getViewport().getWorldHeight()*0.225f);
        sizeText.setPosition(sizeSlider.getX() + sizeSlider.getWidth()/2-sizeText.getWidth()/2,sizeSlider.getY()-20);
    }

    @Override
    public void addListeners() {
        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                preferences.putInteger("difficulty",difficulty);
                preferences.putBoolean("muted",muted);
                preferences.putInteger("size",size);
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
        addActor(bg);
        addActor(optionsLogo);
        addActor(difficultyText);
        addActor(difficultySlider);
        addActor(muteButton);
        addActor(backButton);
        addActor(sizeText);
        addActor(sizeSlider);
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
        sizeSlider.setColor(1,1,1,alpha);
        difficultySlider.setColor(1,1,1,alpha);
        difficultyText.setAlpha(alpha);
        sizeText.setAlpha(alpha);
    }
    //endregion

    public Slider.SliderStyle getSliderStyle(){
        Slider.SliderStyle style;
        style = new Slider.SliderStyle();

        style.knob = new TextureRegionDrawable(new TextureRegion(game.getMyAssetManager().getTexture("buttons/haromszog.png")));
        style.background = new TextureRegionDrawable(new TextureRegion(game.getMyAssetManager().getTexture("colors/rainbow.png")));


        style.knob.setMinHeight(40);
        style.knob.setMinWidth(40);

        style.background.setTopHeight(16);
        return style;
    }
}
