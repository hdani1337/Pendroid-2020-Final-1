package hu.cehessteg.ballgame.Hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import hu.cehessteg.ballgame.Stage.OptionsStage;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IPrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.ballgame.BallGame.muted;
import static hu.cehessteg.ballgame.BallGame.preferences;
import static hu.cehessteg.ballgame.Hud.TextBox.RETRO_FONT;
import static hu.cehessteg.ballgame.Hud.TextBox.TEXTBOX_TEXTURE;
import static hu.cehessteg.ballgame.Hud.TextBox.VERDANA_FONT;

public class OptionSwitch extends MyGroup implements IPrettyStage {
    public static String BUTTON_TEXTURE = "pic/gombok/play.png";

    private OneSpriteStaticActor decrement;
    private OneSpriteStaticActor background;
    private OneSpriteStaticActor increment;
    private MyLabel text;

    private int indexCounter;
    private int indexMax;
    private int indexMin;

    private OptionSwitchType type;

    public OptionSwitch(MyGame game, OptionSwitchType type) {
        super(game);
        this.type = type;
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    @Override
    public void assignment() {
        switch (type){
            case BALLTYPE:
                indexMax = 4;
                indexMin = 1;
                indexCounter = OptionsStage.ballType;
                break;
            case MUTE:
                indexMin = 0;
                indexMax = 1;
                indexCounter = (muted)?0:1;
                break;
            case BALLCOUNT:
                indexMax = 3;
                indexMin = 1;
                indexCounter = OptionsStage.ballCount;
        }

        if(indexCounter == 0) indexCounter = indexMin;

        decrement = new OneSpriteStaticActor(game,BUTTON_TEXTURE);
        increment = new OneSpriteStaticActor(game,BUTTON_TEXTURE);
        background = new OneSpriteStaticActor(game,TEXTBOX_TEXTURE);

        text = new MyLabel(game, "", new Label.LabelStyle(game.getMyAssetManager().getFont((type==OptionSwitchType.BALLCOUNT)?RETRO_FONT:VERDANA_FONT), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
            }
        };

        indexCounterChanged();

        decrement.setRotation(180);
    }

    @Override
    public void setSizes() {
        decrement.setSize(120,120);
        increment.setSize(120,120);
        text.setFontScale(2);
        if(type == OptionSwitchType.BALLCOUNT) text.setFontScale(1.75f);
        text.setAlignment(Align.center);
        background.setSize((getMaxRowWidth()+1)*36,256);
    }

    @Override
    public void setPositions() {
        decrement.setPosition(0,0);
        background.setPosition(decrement.getX()+decrement.getWidth()+24,decrement.getY()+decrement.getHeight()/2-background.getHeight()/2);
        text.setAlignment(Align.center);
        text.setPosition(background.getX()+background.getWidth()/2-text.getWidth()/2,background.getY()+background.getHeight()/2-text.getHeight()/2);
        increment.setPosition(background.getX()+background.getWidth()+24,0);
    }

    @Override
    public void addListeners() {
        decrement.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(decrement.isVisible()) {
                    if (indexCounter > indexMin) {
                        indexCounter--;
                        if(indexCounter == indexMin) decrement.setVisible(false);
                        if(!increment.isVisible()) increment.setVisible(true);
                    }
                    else indexCounter = indexMin;
                    indexCounterChanged();
                }
            }
        });

        increment.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(increment.isVisible()) {
                    if (indexCounter < indexMax) {
                        indexCounter++;
                        if(indexCounter == indexMax) increment.setVisible(false);
                        if(!decrement.isVisible()) decrement.setVisible(true);
                    }
                    else indexCounter = indexMax;
                    System.out.println(indexCounter);
                    indexCounterChanged();
                }
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(background);
        addActor(text);
        addActor(decrement);
        addActor(increment);

        text.setTouchable(null);
        background.setTouchable(null);

        background.setZIndex(0);
        text.setZIndex(10);
        increment.setZIndex(1000);
        decrement.setZIndex(1000);
    }

    private void indexCounterChanged(){
        switch (type){
            case BALLTYPE:
                switch (indexCounter){
                    case 1: default:{
                        decrement.setVisible(false);
                        text.setText("Labda fajtája\nFocilabda");
                        break;
                    }
                    case 2:{
                        text.setText("Labda fajtája\nRöplabda");
                        break;
                    }
                    case 3:{
                        text.setText("Labda fajtája\nKosárlabda");
                        break;
                    }
                    case 4:{
                        increment.setVisible(false);
                        text.setText("Labda fajtája\nBaseball-labda");
                        break;
                    }
                }
                OptionsStage.ballType = indexCounter;
                break;
            case MUTE:
                switch (indexCounter){
                    case 1: default:{
                        increment.setVisible(false);
                        text.setText("Némítás állapota\nNincs némítva");
                        muted = false;
                        break;
                    }
                    case 0:{
                        decrement.setVisible(false);
                        text.setText("Némítás állapota\nNémítva");
                        muted = true;
                        break;
                    }
                }
                break;
            case BALLCOUNT:
                switch (indexCounter){
                    case 1: default:{
                        decrement.setVisible(false);
                        text.setText("Labdák mennyisége\n1");
                        break;
                    }
                    case 2:{
                        text.setText("Labdák mennyisége\n2");
                        break;
                    }
                    case 3:{
                        increment.setVisible(false);
                        text.setText("Labdák mennyisége\n3");
                        break;
                    }
                }
                OptionsStage.ballCount = indexCounter;
                break;
        }
        setSizes();
        setPositions();
        if(getStage()!=null&&getStage() instanceof OptionsStage){
            ((OptionsStage)getStage()).setPositions();
        }
    }

    private ArrayList<Integer> getMatrixSizes(){
        ArrayList<Integer> sizes = new ArrayList();
        sizes.add(0);
        for (int i = 3; i <= 8; i++){
            for (int j = 3; j <= 6; j++){
                sizes.add(i*10+j);
            }
        }
        return sizes;
    }

    private int getMaxRowWidth(){
        int temp = 0;
        int max = 0;
        for (int i = 0; i < this.text.getText().length(); i++){
            if(text.getText().charAt(i) != '\n') temp++;
            else temp = 0;
            if(temp > max) max = temp;
        }
        return max;
    }

    public void setAlpha(float alpha) {
        decrement.setColor(1,1,1,alpha);
        increment.setColor(1,1,1,alpha);
        background.setColor(1,1,1,alpha);
        text.setColor(1,1,1,alpha);
    }

    @Override
    public float getWidth() {
        return decrement.getWidth()+background.getWidth()+increment.getWidth()+48;
    }
}
