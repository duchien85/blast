

package com.muyagdx.blast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MovieScreen implements Screen {
	final Blast game;
	OrthographicCamera camera;
    private GameObject[] infoBlocks; //TODO change to information block (score and unlockables)
	private Stage background;
    //private GameObject backToMain;
	private GameObject next;
	private GameObject previous;
	private GameObject[] lightRects;
	//private int endLevelMode;
	//private ScoreCalculator score;
	public MovieScreen(final Blast gam, int gameMode) {
		this.game = gam;
		game.rayHandler.setAmbientLight(0.5f, 0.5f, 0.5f, 0.1f);
		game.settings.getPreferences();
		background = new Stage();
		background.setMode(gameMode);
		background.setDirectionX(1); /* direction 1 moves to the left if using automove (rethink that)*/
		next = new GameObject();
		previous = new GameObject();
		next.setImage("arrow.png");
		previous.setImage("arrow.png");
		next.setRect(new Rectangle(680,10,64,32)); //TODO change all rects to have screen resolution based variables
		previous.setRect(new Rectangle(750,10,64,32));
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        background.setImage("purpleroses.png"); //TODO replace with a proper video background
        background.setRect(new Rectangle(0,0,800,480));
        background.addMusic("maintheme.mp3"); //TODO replace with current video theme
        background.playMusicLooping(0);
        lightRects = new GameObject[game.settings.getModeCount()];
        infoBlocks = new GameObject[game.settings.getModeCount()];
        
	}





		
		@Override
	    public void render(float delta){//TODO play video file or animate sprites 
			Gdx.gl.glClearColor(0, 0, 0.5f, 1);
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
		    game.font.setColor(0.9f, 0.96f, 0.89f, 1);
		    game.font.setScale(2, 2);
		    camera.update();
		    game.batch.setProjectionMatrix(camera.combined);
		    game.batch.begin();
//		    game.batch.setShader(null);
//		    game.batch.disableBlending();
//		    game.batch.draw(background.getImage(), background.getX(), 0, 800, 480);
//		    game.batch.enableBlending();
		    game.batch.draw(next.getImage(), next.getX(), next.getY(), next.getRect().width, next.getRect().height); //TODO make visible when screen is touched
//		    game.batch.draw(previous.getImage(), previous.getX(), previous.getY(), previous.getRect().width, previous.getRect().height, 0, 0, 400, 200, false, true);
		    game.batch.end();   
		    if (Gdx.input.justTouched()) {
		    	Vector3 touch = new Vector3();
		    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		          camera.unproject(touch);
		          if (next.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		      		  //TODO set screen depending on current gamestate and mode
		        	  //backToMain.playSound(0);
		        	  game.setScreen(new MainMenuScreen(game));
		        	  dispose();
			    	}
		          
		    }
		}

		 public void dispose(){
			 	background.stopMusic(0);
		    	background.dispose();
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
		    }



}

	

