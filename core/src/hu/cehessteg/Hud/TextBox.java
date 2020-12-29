package hu.cehessteg.ballgame.Hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IPrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class TextBox extends MyGroup implements IPrettyStage {
    //region AssetList
    public static final String TEXTBOX_TEXTURE = "pic/ui/textBG.png";
    public static final String RETRO_FONT = "font/fontstyle.ttf";
    public static final String VERDANA_FONT = "font/voyage.ttf";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(TEXTBOX_TEXTURE);
        assetList.addFont(VERDANA_FONT, VERDANA_FONT, 32, Color.WHITE, AssetList.CHARS);
    }
    //endregion
    //region Változók
    public String text;//Szöveg
    private OneSpriteStaticActor textBackground;//Szöveg háttere
    private MyLabel textLabel;//Szöveg label
    private float scale;//Méretezési skála
    private String fontType;//Betűtípus
    //endregion
    //region Konstruktorok
    /**
     * SKÁLÁZÁS NÉLKÜLI KONSTRUKTOR
     * **/
    public TextBox(MyGame game, String text) {
        this(game,text, VERDANA_FONT, 1);
    }

    /**
     * FŐ KONSTRUKTOR
     * **/
    public TextBox(MyGame game, String text, String fontType, float scale){
        super(game);
        this.text = text;
        this.scale = scale;
        this.fontType = fontType;
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }
    //endregion
    //region Interfész metódusai
    @Override
    public void assignment() {
        textBackground = new OneSpriteStaticActor(game, TEXTBOX_TEXTURE);
        textLabel = new MyLabel(game, text, new Label.LabelStyle(game.getMyAssetManager().getFont(fontType), Color.WHITE)) {
            @Override
            public void init() {

            }
        };
    }

    @Override
    public void setSizes() {
        textBackground.setSize((getMaxRowWidth()+1)*21, textLabel.getHeight()*1.4f);
        setScales();
    }

    @Override
    public void setPositions() {
        if(textBackground != null && textLabel != null) {
            textLabel.setAlignment(Align.center);
            textBackground.setPosition(0, 0);
            textLabel.setPosition(textBackground.getX() + textBackground.getWidth() / 2 - textLabel.getWidth() / 2, textBackground.getY() + textBackground.getHeight() / 2 - textLabel.getHeight() / 2);
        }
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(textBackground);
        addActor(textLabel);
    }
    //endregion
    //region Override-ok
    /**
     * VISSZAADJA A HÁTTÉR SZÉLESSÉGÉT
     * **/
    @Override
    public float getWidth() {
        return (textBackground!=null)?textBackground.getWidth():0;
    }

    /**
     * VISSZAADJA A HÁTTÉR MAGASSÁGÁT
     * **/
    @Override
    public float getHeight() {
        return (textBackground!=null)?textBackground.getHeight():0;
    }

    /**
     * SZÖVEG HÁTTÉR SZÉLESSÉGÉNEK MÓDOSÍTÁSA
     * **/
    @Override
    public void setWidth(float width) {
        if(textBackground != null) textBackground.setWidth(width);
        setPositions();
    }

    /**
     * SZÖVEG HÁTTÉR MÉRETÉNEK MÓDOSÍTÁSA
     * **/
    @Override
    public void setSize(float width, float height) {
        textBackground.setWidth(width);
        textBackground.setHeight(height);
        setPositions();
    }
    //endregion
    //region Egyéb metódusok
    /**
     * TEXTBOX ÁTLÁTSZÓSÁGÁNAK BEÁLLÍTÁSA
     * **/
    public void setAlpha(float alpha){
        textBackground.setAlpha(alpha);
        textLabel.setColor(textLabel.getColor().r,textLabel.getColor().g,textLabel.getColor().b,alpha);
    }

    /**
     * MÉRETEK MÓDOSÍTÁSA SKÁLA ALAPJÁN
     * A SKÁLÁT A KONSTRUKTORBAN KELL ÁTADNI, ALAPESETBEN EZ 1 MARAD
     * **/
    private void setScales(){
        textBackground.setSize(textBackground.getWidth()*scale, textBackground.getHeight()*scale);
        textLabel.setFontScale(scale);
    }

    /**
     * TEXTBOX SZÖVEGÉNEK MÓDOSÍTÁSA
     * **/
    public void setText(String text){
        this.text = text;
        removeActors();
        assignment();
        setSizes();
        setPositions();
        addActors();
    }

    /**
     * TEXTBOX TÖRLÉSE
     * **/
    private void removeActors(){
        textBackground.remove();
        textLabel.remove();
    }

    /**
     * VISSZAADJA A LEGHOSSZABB SOR HOSSZÁT A SZÖVEGBŐL
     * **/
    public int getMaxRowWidth(){
        int temp = 0;
        int max = 0;
        for (int i = 0; i < this.text.length(); i++){
            if(text.charAt(i) != '\n') temp++;
            else temp = 0;
            if(temp > max) max = temp;
        }
        return max;
    }

    /**
     * LABEL SZÍNÉNEK MÓDOSÍTÁSA
     * **/
    public void setColor(Color color){
        textLabel.setColor(color);
    }

    @Override
    public void setPositionCenter() {
        if(getStage() != null)
            setPosition(getStage().getViewport().getWorldWidth()/2-this.getWidth()/2,getStage().getViewport().getWorldHeight()/2-this.getHeight()/2);
    }
    //endregion
}
