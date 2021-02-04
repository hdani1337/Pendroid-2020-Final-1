package hu.cehessteg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;

/**
 * Ezt az osztályt azért hoztam létre, hogy könnyebben hozzáférhessek a hanganyagokhoz, illetve egységesen tölthessem be őket
 * Minden hang és zene 1-1 statikus példány, így nem kell őket stagenként létrehozni
 * **/
public class SoundManager {
    /**Game egy példánya, hogy később könnyen hozzáférhessek**/
    public static TetrisGame game;

    /**
     * Hangok elérési útjai
     * **/
    public static final String FALLSOUND = "sound/fall.mp3";
    public static final String CLEARSOUND = "sound/clear.mp3";
    public static final String STEPSOUND = "sound/step.mp3";
    public static final String LOSESOUND = "sound/lose.mp3";

    /**
     * Zenék elérési útjai
     * **/
    public static final String MENUMUSIC = "sound/gameMusic.mp3";
    public static final String GAMEMUSIC = "sound/gameMusic.mp3";

    /**
     * Hangok
     * **/
    public static Sound fallSound;
    public static Sound clearSound;
    public static Sound stepSound;
    public static Sound loseSound;

    /**
     * Zenék
     * **/
    public static Music menuMusic;
    public static Music gameMusic;

    /**
     * Objektumok létrehozása
     * **/
    public static void assign(){
        if(game != null) {
            /**
             * Hang objektumok létrehozása
             * **/
            fallSound = game.getMyAssetManager().getSound(FALLSOUND);
            clearSound = game.getMyAssetManager().getSound(CLEARSOUND);
            stepSound = game.getMyAssetManager().getSound(STEPSOUND);
            loseSound = game.getMyAssetManager().getSound(LOSESOUND);

            /**
             * Zene objektumok létrehozása
             * **/
            menuMusic = game.getMyAssetManager().getMusic(MENUMUSIC);
            gameMusic = game.getMyAssetManager().getMusic(GAMEMUSIC);
        }
    }

    /**
     * Betöltés AssetList átadásával
     * **/
    public static void load(AssetList assetList) {
        if(assetList != null) {
            /**
             * Hangok betöltése
             * **/
            assetList.addSound(FALLSOUND);
            assetList.addSound(CLEARSOUND);
            assetList.addSound(STEPSOUND);
            assetList.addSound(LOSESOUND);

            /**
             * Zenék betöltése
             * **/
            assetList.addMusic(MENUMUSIC);
            assetList.addMusic(GAMEMUSIC);
            Gdx.app.log("SoundManager","!!! WARNING !!! CALL assign() WHEN THE LOADING HAS BEEN FINISHED");
        }
    }

    /**
     * Objektumok kiürítése
     * Ennek meghívása után lehetőleg ne hivatkozzunk rájuk mert NullPointert dobnak :D
     * **/
    public static void dispose(){
        /**
         * Hang objektumok nullásáza
         * **/
        clearSound = null;
        fallSound = null;
        stepSound = null;
        loseSound = null;

        /**
         * Zene objektumok nullázása
         * **/
        menuMusic = null;
        gameMusic = null;
    }
}
