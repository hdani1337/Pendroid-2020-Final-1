package hu.cehessteg.Screen;

import hu.cehessteg.Stage.MenuStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class MenuScreen extends MyScreen {
    public static AssetList assetList = new AssetList();
    static {

    }

    public MenuScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new MenuStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
