package com.muyagdx.blast;

//import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen{
	final Blast game;
	OrthographicCamera camera;
    private GameObject background;
    private GameObject quit;
	private GameObject blueCircle;
	private FontLocalization fontLang;
	
	public MainMenuScreen(final Blast gam) {
		//TODO add quit to main menu
        game = gam;
        game.settings.getPreferences();
//		fontLang = new FontLocalization();
//		game.font =  fontLang.getFontLocale(game.settings.getLanguage());
		background = new GameObject();
        background.addSound("drum1.wav");
        quit = new GameObject();	
        background.setImage("collage1small.png");
        background.setRect(new Rectangle(0,0,800,480));
        background.addMusic("maintheme.mp3");
        background.playMusicLooping(0);
        quit.setImage("exit.png");
        quit.setRect(new Rectangle(0,410,100,64));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        blueCircle = new GameObject();
        blueCircle.setImage("dropbutton.png");
    }
    @Override
    public void render(float delta){
    	if(game != null && quit != null && background != null){
    	Gdx.gl.glClearColor(1, 1, 0.5f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
	    game.font.setScale(2, 2);
	    game.font.setColor(0.9f, 0.7f, 0.7f, 1);
	    camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
	    game.batch.begin();
	    game.batch.disableBlending();
	    game.batch.draw(background.getImage(), background.getX(), background.getY());
	    game.batch.enableBlending();
	    game.batch.draw(blueCircle.getImage(),510,310);
	    game.batch.draw(blueCircle.getImage(),510,160);
	    game.batch.draw(blueCircle.getImage(),200,310);
	    game.batch.draw(blueCircle.getImage(),200,160);
	    game.batch.draw(quit.getImage(), quit.getX(), quit.getY());
	    if(game.settings.getLanguage().equals("Jp")){
	    	game.font.setScale(3, 3);
		    game.font.draw(game.batch, "恐怖の世界にようこそ!", 165, 300);
		    game.font.setScale(2, 2);
		    game.font.draw(game.batch, "戦いましょう?!", 250, 240);
	    }
		else{
			game.font.setScale(3, 3);
		    game.font.draw(game.batch, "Welcome to the world of fear!", 200, 300); // TODO animate font
		    game.font.setScale(2, 2);
		    game.font.draw(game.batch, "Ready to fight yours?!", 220, 240);
	    }
	    game.batch.setColor(1,1,1,1);
	    game.batch.end();   
	    if (Gdx.input.justTouched()) {
	    	Vector3 touch = new Vector3();
	    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	          camera.unproject(touch);
	        
	    	if (quit.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
    			//TODO really quit? if yes do quit 
	    		Gdx.app.exit();
	    		try{
	    			dispose();
	    			Gdx.app.log("INFO", "Dispose app");
	    		}catch(Exception e){
	    			Gdx.app.log("INFO", "Could not dispose app");
	    		}
	    		
            }
	    	else{
	    	background.playSound(0);
	    	game.setScreen(new ModeSelect(game));
	    	dispose();
	    	}
        }
	    background.sinMoveY(background.getImage().getHeight() - 480, 480 - background.getImage().getHeight(), 1);
    	}
    }
    public void dispose(){
    	background.stopMusic(0);
    	if(quit != null && background != null){
        	background.dispose();
        	quit.dispose();
    	}
    }
    @Override
    public void pause() {
    	
    }

    @Override
    public void resume() {
    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    	//game.dispose();
    	//dispose();
    }

}

