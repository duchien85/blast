package com.muyagdx.blast;



import box2dLight.RayHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;


public class Blast extends Game implements ApplicationListener {
	static final int READY = 0;//TODO clean states
	static final int ACTION = 1;
	static final int PAUSED = 2;
	static final int PASSED = 3;
	static final int BONUS = 4;
	static final int MOVIE = 5;
	static final int RECORD = 6;
	static final int PLAYBACK = 7;
	static final int SETTINGSERROR = 8;
	static final int BRIEF = 9;
	static final int NOGESTURE = 10;
	static final int UPGESTURE = 11;
	static final int DOWNGESTURE = 12;
	static final int RIGHTGESTURE = 13;
	static final int LEFTGESTURE = 14;
	static final int PANGESTURE = 15;
	
	public RayHandler rayHandler;
	public World world;
	public Settings settings ;
	private int state;
	public SpriteBatch batch;
	public BitmapFont font;
	public int gesture;
	private int currentPlayer;
	private int currentOpponent;
	private int currentWeapon;
	private int currentMusic;
	private int currentStage;
	public int screenWidth;
	public int screenHeight;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		gesture = NOGESTURE;
		state = 0;
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.5f, 0.5f, 0.5f, 0.1f);
		rayHandler.setCulling(true);	
		rayHandler.setBlurNum(1);
		Gdx.input.setInputProcessor(new GestureDetect(new GestureDetect.DirectionListener() {
			@Override
			public void onUp() {
				gesture = UPGESTURE;
			}
			@Override
			public void onRight() {
				gesture = RIGHTGESTURE;
			}
			@Override
			public void onLeft() {
				gesture = LEFTGESTURE;
			}
			@Override
			public void onDown() {
				gesture = DOWNGESTURE;
			}
		}));	 
//		InputMultiplexer im = new InputMultiplexer();
//        //GestureDetector gd = new GestureDetector(this);
//        im.addProcessor(gd);
//        im.addProcessor(this);
//        Gdx.input.setInputProcessor(im);
		this.setSettings(new Settings());
        this.setScreen(new MainMenuScreen(this));
	}
	@Override
	public void render() {
		try {
			super.render();
		}catch (Exception e) {
			Gdx.app.log("ERROR", "Could not render");
			e.printStackTrace();
		}

	}
	public int getState(){
		return state;
	}

	@Override
	public void dispose() {
		try {
			batch.dispose();
			Gdx.app.log("INFO", "Dispose batch");
		} catch (Exception e) {
			Gdx.app.log("ERROR", "Could not dispose batch");
			e.printStackTrace();
		} //TODO Why gives sigsegv error?
		try {
			font.dispose();
			Gdx.app.log("INFO", "Dispose font");
		} catch (Exception e) {
			Gdx.app.log("ERROR", "Could not dispose font");
			e.printStackTrace();
		}
		try {
			rayHandler.dispose(); 
			Gdx.app.log("INFO", "Dispose light");
		} catch (Exception e) {
			Gdx.app.log("ERROR", "Could not dispose light");
			e.printStackTrace();
		}
		
	}
	public void setState(int s){
		state = s;
	}
	public Settings getSettings() {
		return settings;
	}
	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public int getCurrentOpponent() {
		return currentOpponent;
	}
	public void setCurrentOpponent(int currentOpponent) {
		this.currentOpponent = currentOpponent;
	}
	public int getCurrentWeapon() {
		return currentWeapon;
	}
	public void setCurrentWeapon(int currentWeapon) {
		this.currentWeapon = currentWeapon;
	}
	public int getCurrentStage() {
		return currentStage;
	}
	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}
	public int getCurrentMusic() {
		return currentMusic;
	}
	public void setCurrentMusic(int currentMusic) {
		this.currentMusic = currentMusic;
	}
	public void backToMain(){
		this.setState(0);
		this.setScreen(new MainMenuScreen(this));
	}
}
