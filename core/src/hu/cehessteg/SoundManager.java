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


    /**
     * Zenék elérési útjai
     * **/
    public static final String MENUMUSIC = "sound/gameMusic.mp3";
    public static final String GAMEMUSIC = "sound/gameMusic.mp3";

    /**
     * Hangok
     * **/

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

        /**
         * Zene objektumok nullázása
         * **/
        menuMusic = null;
        gameMusic = null;
    }
}
