package hu.cehessteg.ballgame.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import hu.cehessteg.ballgame.Hud.Logo;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.ballgame.Hud.TextBox.TEXTBOX_TEXTURE;
import static hu.cehessteg.ballgame.Hud.TextBox.VERDANA_FONT;
import static hu.cehessteg.ballgame.Stage.WeatherBackground.PLAYFIELD_TEXTURE;

public class InfoStage extends PrettyStage {
    public static String BACKBUTTON_TEXTURE = "pic/gombok/play.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(BACKBUTTON_TEXTURE);
        assetList.addFont(VERDANA_FONT, VERDANA_FONT, 32, Color.WHITE, AssetList.CHARS);
    }

    public InfoStage(MyGame game) {
        super(game);
    }

    private OneSpriteStaticActor textBg;
    private MyLabel text;
    private OneSpriteStaticActor back;
    private OneSpriteStaticActor playfieldActor;
    private Logo infoLogo;

    private boolean setBack;
    //endregion
    //region Absztrakt metódusok
    @Override
    public void assignment() {
        //SoundManager.assign();
        //if(!muted)
        //    SoundManager.menuMusic.play();
        back = new OneSpriteStaticActor(game, BACKBUTTON_TEXTURE);
        back.setRotation(180);
        textBg = new OneSpriteStaticActor(game,TEXTBOX_TEXTURE);
        infoLogo = new Logo(game, Logo.LogoType.INFO);

        String infoText = "Ez az alkalmazás a Pendroid versenyre készült.\n" +
                "Ennek az egyszerű játéknak a célja csupán annyi,\n" +
                "hogy minél tovább pattogtasd a labdát.\n" +
                "Minden egyes labdához érés után egy pontot\n" +
                "kap a játékos. Ha a labda földet ér\n" +
                "vagy kimegy a képből, akkor véget ér a játék.\n" +
                "Továbbá az Opciók menüpontban kedvünkre\n" +
                "választhatunk a rendelkezésre álló labdákból,\n" +
                "illetve hány labdával szeretnénk játszani egyszerre\n" +
                "amik mentésre is kerülnek, így a kilépés után is\n" +
                "megmaradnak a kiválasztott állapotok.\n\n" +
                "Jó játékot kíván a Céhessteg csapata!";

        text = new MyLabel(game, infoText, new Label.LabelStyle(game.getMyAssetManager().getFont(VERDANA_FONT), Color.WHITE)) {
            @Override
            public void init() {

            }
        };

        playfieldActor = new OneSpriteStaticActor(game,PLAYFIELD_TEXTURE);
        playfieldActor.setSize(getViewport().getWorldWidth(),(getViewport().getWorldWidth()/playfieldActor.getWidth())*playfieldActor.getHeight());
    }

    @Override
    public void setSizes() {
        infoLogo.setSize(infoLogo.getWidth()*0.9f,infoLogo.getHeight()*0.9f);
        textBg.setSize(text.getWidth()+120,text.getHeight()+140);
        back.setSize(180,180);
    }

    @Override
    public void setPositions() {
        back.setPosition(getViewport().getWorldWidth() - back.getWidth()-16,16);
        infoLogo.setPosition(getViewport().getWorldWidth()/2 - infoLogo.getWidth()/2,getViewport().getWorldHeight() - infoLogo.getHeight()*1.1f);
        text.setAlignment(Align.center);
        text.setPosition(getViewport().getWorldWidth()/2-text.getWidth()/2,playfieldActor.getHeight()*0.75f);
        textBg.setPosition(text.getX()-60,text.getY()-70);
    }

    @Override
    public void addListeners() {
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setBack = true;
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(infoLogo);
        addActor(textBg);
        addActor(text);
        addActor(playfieldActor);
        addActor(back);
    }
    //endregion
    //region Act metódusai
    float alpha = 0;
    float bgAlpha = 1;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!setBack) {
            //Áttűnéssel jönnek be az actorok
            if (alpha < 0.95) alpha += 0.025;
            else alpha = 1;
            setAlpha();
        }
        else
        {
            //Áttűnéssel mennek ki az actorok
            if (alpha > 0.05) {
                setAlpha();
                alpha -= 0.05;
                if(bgAlpha<0.95) bgAlpha+= 0.05;

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

        if(bgAlpha>0.65 && !setBack){
            bgAlpha-=0.025;

        }
    }

    /**
     * Actorok átlátszóságának egyidejű beállítása
     * **/
    private void setAlpha(){
        infoLogo.setAlpha(alpha);
        back.setAlpha(alpha);
        textBg.setAlpha(alpha);
        text.setColor(text.getColor().r,text.getColor().g,text.getColor().b,alpha);
    }
    //endregion
}
