package hu.cehessteg.Hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import hu.cehessteg.Stage.OptionsStage;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IPrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.Hud.TextBox.RETRO_FONT;
import static hu.cehessteg.Hud.TextBox.TEXTBOX_TEXTURE;
import static hu.cehessteg.Hud.TextBox.VERDANA_FONT;
import static hu.cehessteg.TetrisGame.muted;

public class OptionSwitch extends MyGroup implements IPrettyStage {
    public static String RIGHT_TEXTURE = "buttons/back.png";
    public static String LEFT_TEXTURE = "buttons/back2.png";

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
            case MUTE:
                indexMin = 0;
                indexMax = 1;
                indexCounter = (muted)?0:1;
                break;
            case DIFFICULTY:
                indexMax = 4;
                indexMin = 1;
                indexCounter = OptionsStage.difficulty;
                break;
        }

        if(indexCounter == 0) indexCounter = indexMin;

        decrement = new OneSpriteStaticActor(game,LEFT_TEXTURE);
        increment = new OneSpriteStaticActor(game,RIGHT_TEXTURE);
        background = new OneSpriteStaticActor(game,TEXTBOX_TEXTURE);

        text = new MyLabel(game, "", new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
            }
        };

        indexCounterChanged();
    }

    @Override
    public void setSizes() {
        decrement.setSize(112,112);
        increment.setSize(112,112);
        text.setAlignment(Align.center);
        background.setSize((getMaxRowWidth()+1)*28,150);
    }

    @Override
    public void setPositions() {
        decrement.setPosition(0,0);
        background.setPosition(decrement.getX()+decrement.getWidth()+24,decrement.getY()+decrement.getHeight()/2-background.getHeight()/2);
        text.setAlignment(Align.center);
        text.setFontScale(1.25f);
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
            case DIFFICULTY:
                switch (indexCounter){
                    case 1: default:{
                        decrement.setVisible(false);
                        text.setText("Nehézség\nKönnyű");
                        break;
                    }
                    case 2:{
                        text.setText("Nehézség\nNormál");
                        break;
                    }
                    case 3:{
                        text.setText("Nehézség\nNehéz");
                        break;
                    }
                    case 4:{
                        increment.setVisible(false);
                        text.setText("Nehézség\nLehetetlen");
                        break;
                    }
                }
                OptionsStage.difficulty = indexCounter;
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
