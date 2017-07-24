package com.muyagdx.blast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SettingsScreen implements Screen {
	final Blast game;
	OrthographicCamera camera;
    private GameObject[] infoBlocks; 
	private Stage background;
    private GameObject backToMain;
	private GameObject next;
	private GameObject[] lightRects;
	private String selectedLanguage;
	private GameObject enSelection;
	private GameObject arSelection;
	private GameObject jpSelection;
	private FontLocalization fontLang;

	public SettingsScreen(final Blast gam, int gameMode) {
		this.game = gam;
		game.rayHandler.setAmbientLight(0.5f, 0.5f, 0.5f, 0.1f);
		game.settings.getPreferences();
		fontLang = new FontLocalization();
		game.font =  fontLang.getFontLocale(game.settings.getLanguage());
		selectedLanguage = game.settings.getLanguage();
		Gdx.app.log("INFO", "Laguage: " + selectedLanguage);
		background = new Stage();
		background.setMode(gameMode);
		background.setDirectionX(1); /* direction 1 moves to the left if using automove (rethink that)*/
		backToMain = new GameObject();
		backToMain.setImage("quit2.png");
		backToMain.setRect(new Rectangle(0,410,64,64));
		enSelection = new GameObject();
		enSelection.setName("English");
		enSelection.setImage("buttonframe.png");
		enSelection.setRect(new Rectangle(450,400,140,50)); 
		arSelection = new GameObject();
		arSelection.setName("Arabic");
		arSelection.setImage("buttonframe.png");
		arSelection.setRect(new Rectangle(450,400,140,50));
		jpSelection = new GameObject();
		jpSelection.setName("Japanese");
		jpSelection.setImage("buttonframe.png");
		jpSelection.setRect(new Rectangle(600,400,140,50)); 
		next = new GameObject();
		next.setImage("buttonframe.png");
		next.setName("Save");
		next.setRect(new Rectangle(680,10,84,44)); //TODO change all rects to have screen resolution based variables
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        background.setImage("purpleroses.png"); //TODO replace with a proper settings background
        background.setRect(new Rectangle(0,0,800,480));
        background.addMusic("maintheme.mp3"); //TODO replace with settings theme
        background.playMusicLooping(0);
        lightRects = new GameObject[1];
        infoBlocks = new GameObject[1];
        for(int i = 0; i < 1; i++){ //TODO change 1 to actual count of user controlled settings items 
        	infoBlocks[i] = new GameObject(); 
        	lightRects[i] = new GameObject();
        	lightRects[i].setDirectionX(1);
        	lightRects[i].setDirectionY(1);
        	//infoBlocks[i].setImage("menuitem.png"); 
        	infoBlocks[i].setRect(new Rectangle(80, 400 - i*50, 440, 50));	
		    lightRects[i].setRect(new Rectangle(80, 400 - i*50, 440, 50));
        }
        infoBlocks[0].setName("Language ");
//        infoBlocks[1].setName("Power: ");
//        infoBlocks[2].setName("Speed: ");
//        infoBlocks[3].setName("Health: ");
//        infoBlocks[4].setName("Difficulty: ");
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
		    game.batch.setShader(null);
		    game.batch.disableBlending();
		    game.batch.draw(background.getImage(), background.getX(), 0, 800, 480);
		    game.batch.enableBlending(); 
		    game.font.draw(game.batch, infoBlocks[0].getName(), infoBlocks[0].getX(), infoBlocks[0].getY() + 36);
		    if (selectedLanguage.equals("En"))//TODO why is the shader not working
		    	game.batch.setShader(null);
		    else
		    	game.batch.setShader(GrayscaleShader.grayscaleShader);
		    game.batch.draw(enSelection.getImage(), enSelection.getX(), enSelection.getY(), enSelection.getRect().width, enSelection.getRect().height);
		    game.font.draw(game.batch, enSelection.getName(), enSelection.getX() + 5, enSelection.getY() + 36);
		    if (selectedLanguage.equals("Ar"))
	    		game.batch.setShader(null);
		    else
	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
//		    game.batch.draw(arSelection.getImage(), arSelection.getX(), arSelection.getY(), arSelection.getRect().width, arSelection.getRect().height); 
//		    game.font.draw(game.batch, arSelection.getName(), arSelection.getX() + 5, arSelection.getY() + 36);
		    if (selectedLanguage.equals("Jp"))
	    		game.batch.setShader(null);
		    else
	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
		    game.batch.draw(jpSelection.getImage(), jpSelection.getX(), jpSelection.getY(), jpSelection.getRect().width, jpSelection.getRect().height); 
		    game.font.draw(game.batch, jpSelection.getName(), jpSelection.getX() + 5, jpSelection.getY() + 36);
		    game.batch.setShader(null);
		    game.batch.draw(next.getImage(), next.getX(), next.getY(), next.getRect().width, next.getRect().height); 
		    game.font.draw(game.batch, next.getName(), next.getX() + 5, next.getY() + 36);
		    game.batch.draw(backToMain.getImage(), backToMain.getX(), backToMain.getY(), backToMain.getRect().width, backToMain.getRect().height);
		    game.batch.end();   
		    if (Gdx.input.justTouched()) {
		    	Vector3 touch = new Vector3();
		    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		          camera.unproject(touch);
		          if (enSelection.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		        	  arSelection.deselect();
		        	  jpSelection.deselect();
		        	  enSelection.select();
		      		selectedLanguage = "En";
			    	}
//		          else if (arSelection.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
//		        	  enSelection.deselect();
//		        	  jpSelection.deselect();
//		        	  arSelection.select();
//		        	  selectedLanguage = "Ar";
//			    	}
		          else if (jpSelection.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		        	  enSelection.deselect();
		        	  arSelection.deselect();
		        	  jpSelection.select();
		        	  selectedLanguage = "Jp";
			    	}
		          else if (next.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		      		  game.settings.setLanguage(selectedLanguage);
		      		  Gdx.app.log("INFO", "Saved language: " + selectedLanguage);
		        	  game.setScreen(new ModeSelect(game));
		        	  dispose();
			    	}
		          else if (backToMain.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		      		  //TODO return to main without saving
		        	  game.setScreen(new ModeSelect(game));
		        	  dispose();
			    	}     
		    }
		}

		 public void dispose(){
			 	background.stopMusic(0);
		    	background.dispose();
		    	fontLang.dispose();
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
