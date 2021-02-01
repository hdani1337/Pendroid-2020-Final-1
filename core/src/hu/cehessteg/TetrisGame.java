package hu.cehessteg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hu.cehessteg.Screen.GameScreen;
import hu.cehessteg.Screen.IntroScreen;
import hu.cehessteg.Stage.LoadingStage;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

import static hu.cehessteg.Stage.OptionsStage.difficulty;
import static hu.cehessteg.Stage.OptionsStage.size;

public class TetrisGame extends MyGame {

	public TetrisGame(){
	}

	public TetrisGame(boolean debug){
		super(debug);
	}

	public static Preferences preferences;//Mentés
	public static boolean muted;//Némítva van e a játék

	@Override
	public void create() {
		super.create();
		SoundManager.game = this;
		setLoadingStage(new LoadingStage(this));
		setScreen(new IntroScreen(this));
		try {
			preferences = Gdx.app.getPreferences("tetrisSave");
			muted = preferences.getBoolean("muted");
			Gdx.app.getGraphics().setTitle("Tetris");
			Gdx.app.getGraphics().setResizable(false);

			//setDisplay();
		}catch (NullPointerException e){
			/**Ha NullPointert kapunk, akkor még nincsenek mentett adatok**/
		}
	}
}