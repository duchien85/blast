package com.muyagdx.blast;

import box2dLight.PointLight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;



public class ModeSelect implements Screen {

		final Blast game;
		OrthographicCamera camera;
	    private GameObject[] modeButton;
		private Stage background;
	    private GameObject backToMain;
		private GameObject arrowUp;
		private GameObject arrowDown;
		private GameObject[] lightRects;
		private FontLocalization fontLang;
		
		public ModeSelect(final Blast gam) {
			this.game = gam;
			game.rayHandler.setAmbientLight(0.5f, 0.5f, 0.5f, 0.1f);
			game.settings.getPreferences();
//			fontLang = new FontLocalization();
//			game.font =  fontLang.getFontLocale(game.settings.getLanguage());
			background = new Stage();
			background.setDirectionX(1); /* direction 1 moves to the left if using automove (rethink that)*/
			backToMain = new GameObject();
			arrowUp = new GameObject();
			arrowDown = new GameObject();
			backToMain.setImage("quit3.png");
			backToMain.setRect(new Rectangle(0,410,100,64));
			backToMain.addSound("bump1.wav");
			arrowUp.setImage("arrow.png");
			arrowDown.setImage("arrow.png");
			arrowUp.setRect(new Rectangle(150,450,500,30));
			arrowDown.setRect(new Rectangle(150,10,500,30));
			camera = new OrthographicCamera();
	        camera.setToOrtho(false, 800, 480);
	        // TODO selected mode should have help image loaded in the left or right side
	        background.setImage("purpleroses.png"); //TODO replace with a proper mode select background
	        background.setRect(new Rectangle(0,0,800,480));
	        background.addMusic("maintheme.mp3");
	        background.playMusicLooping(0);
	        lightRects = new GameObject[game.settings.getModeCount()];
	        modeButton = new GameObject[game.settings.getModeCount()];
	        for(int i = 0; i < game.settings.getModeCount(); i++){
	        	modeButton[i] = new GameObject(); 
	        	lightRects[i] = new GameObject();
	        	lightRects[i].setDirectionX(1);
	        	lightRects[i].setDirectionY(1);
	        	modeButton[i].disable(Integer.parseInt(game.settings.getModeStatus(i)));
	        	modeButton[i].setImage("menuitem.png"); 
	        	modeButton[i].setName(game.settings.getModeNames()[i]);
			    modeButton[i].setRect(new Rectangle(180, 400 - i*50, 440, 50));	
			    lightRects[i].setRect(new Rectangle(180, 400 - i*50, 440, 50));
	        }
		}
		
		@Override
	    public void render(float delta){
			Gdx.gl.glClearColor(0, 0, 0.5f, 1);
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
		    if(game != null){
		    game.font.setScale(2, 2);
		    camera.update();
		    game.batch.setProjectionMatrix(camera.combined);
		    game.batch.begin();
		    game.batch.setShader(null);
		    game.batch.disableBlending();
		    game.batch.draw(background.getImage(), background.getX(), 0, 1600, 480);
		    game.batch.enableBlending();
		    game.batch.draw(backToMain.getImage(), backToMain.getX(), backToMain.getY());
		    // TODO buttons need a bordering glow
		    game.batch.draw(arrowUp.getImage(), arrowUp.getX(), arrowUp.getY(), arrowUp.getRect().width, arrowUp.getRect().height); 
		    game.batch.draw(arrowDown.getImage(), arrowDown.getX(), arrowDown.getY(), arrowDown.getRect().width, arrowDown.getRect().height, 0, 0, 400, 200, false, true);
		    for(int i = 0; i < game.settings.getModeCount(); i++){
		    	if(modeButton[i].getY() >= arrowDown.getY()+40 && modeButton[i].getY()+50 <= arrowUp.getY()){
		    		game.batch.draw(modeButton[i].getImage(),modeButton[i].getX(),modeButton[i].getY(), modeButton[i].getRect().width, modeButton[i].getRect().height);
		    		if(modeButton[i].isDisabled()) game.font.setColor(0.3f, 0.5f, 0.5f, 1);
		    		else game.font.setColor(0.8f, 0.8f, 0.8f, 1);
				    game.font.draw(game.batch, modeButton[i].getName(), modeButton[i].getX()+modeButton[i].getRect().width/2-game.font.getBounds(modeButton[i].getName()).width/2, modeButton[i].getY() + 38);
		    		
		    	}
		    }
		    game.batch.end();   
		    //if(game.rayHandler.lightList.size>500)//TODO make light width increase and decrease 
		    	game.rayHandler.removeAll();//Clear when lights are above certain number
		    game.rayHandler.setAmbientLight(0.1f);
		    game.rayHandler.setShadows(true);
		    for(int i=0; i < modeButton.length; i++){
		    	if(!modeButton[i].isDisabled() && modeButton[i].getY() >= arrowDown.getY()+arrowDown.getRect().height && modeButton[i].getY()+modeButton[i].getRect().height <= arrowUp.getY()){
			    	new PointLight(game.rayHandler, 500, new Color(0.8f,0.8f,0.8f,0.8f), background.getLuminance(), lightRects[i].getX(), lightRects[i].getY()+25);
		    		new PointLight(game.rayHandler, 500, new Color(0.8f,0.8f,0.8f,0.8f), background.getLuminance(), modeButton[i].getRect().width + 2*modeButton[i].getX() - lightRects[i].getX(), modeButton[i].getRect().height + 2*modeButton[i].getY() - lightRects[i].getY()-25);
		    	}
		    }
		    new PointLight(game.rayHandler, 8, new Color(0.8f,0.8f,0.8f,0.8f), backToMain.getLuminance(), backToMain.getX()+backToMain.getRect().width/2, backToMain.getY()+backToMain.getRect().height/2);
		    game.rayHandler.setCombinedMatrix(camera.combined);
		    game.rayHandler.updateAndRender();
		    if (Gdx.input.justTouched()) {
		    	Vector3 touch = new Vector3();
		    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		          camera.unproject(touch);
		          if (arrowUp.getRect().contains(new Rectangle(touch.x,touch.y,1,1)) && modeButton[0].getY()>=450){
		      		  for(int i=0; i<modeButton.length ; i++){
		      			  modeButton[i].setY(modeButton[i].getY()-modeButton[i].getRect().height);
		      			  lightRects[i].setY(lightRects[i].getY()-modeButton[i].getRect().height);
		      		  }
			    	}
		          else if (arrowDown.getRect().contains(new Rectangle(touch.x,touch.y,1,1)) && modeButton[modeButton.length-1].getY()<40){
		      		  for(int i=0; i<modeButton.length; i++){
		      			  modeButton[i].setY(modeButton[i].getY()+modeButton[i].getRect().height);
		      			  lightRects[i].setY(lightRects[i].getY()+modeButton[i].getRect().height);
		      		  }
		      		  
			    	}
		          else if (backToMain.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		        	  backToMain.playSound(0);
		        	  game.setScreen(new MainMenuScreen(game));
		        	  dispose();
		    	}
		    	  else{
		        	  for(int i = 0; i < game.settings.getModeCount(); i++){
		            // TODO play audio
		        	  if (modeButton[i].getRect().contains(new Rectangle(touch.x,touch.y,1,1)) && !modeButton[i].isDisabled()){
		        		  setGame(i);
		        	  }
		        	  }
		          }
		    }
		    background.autoMoveX(-300, 0);
		    for(int i=0; i<modeButton.length ;i++){
		    //lightRects[i].autoMoveX((int)modeButton[i].getX(), (int)(modeButton[i].getX() + modeButton[i].getRect().width), 5);
		    	lightRects[i].sinMove((int)modeButton[i].getRect().width/2,  (int)(modeButton[i].getX()+modeButton[i].getRect().width/2), 1);
			 	//lightRects[i].autoMoveY((int)modeButton[i].getY(), (int)(modeButton[i].getY() + modeButton[i].getRect().height), 5);
		    }
		    //lightRects[0].cosMove((int)modeButton[0].getRect().width,  (int)(modeButton[0].getX()+modeButton[0].getRect().width/2), 1);

		    }
		    background.cycleLuminance(1, 100, 30);
		    backToMain.sinLuminance(TimeUtils.nanoTime(), 120, 20);
		}
		public void setGame(int selectedMode){
			switch(selectedMode){
			case 0:
				game.setScreen(new LevelSelect(game, selectedMode));
      		  	dispose();
      		  	break;
			case 1:
				game.setCurrentPlayer(MathUtils.random(0, game.settings.getCharCount()-1));
				game.setCurrentOpponent(MathUtils.random(0, game.settings.getOppCount()-1));
				game.setCurrentWeapon(MathUtils.random(0, game.settings.getWeapCount()-1));
				game.setCurrentStage(MathUtils.random(0, game.settings.getStageCount()-1));
				game.setCurrentMusic(game.getCurrentStage());
				game.setScreen(new GameScreen(game, selectedMode));
				dispose();
				break;
			case 2://TODO connect to network and start a vs game
			case 3://TODO create a game with love weapons and NPC characters
			case 4://TODO Time attack
			case 5://TODO Survival
			case 6://TODO Tutorial
			case 7://TODO Select from Bonus games
			case 8://TODO Settings
				game.setScreen(new SettingsScreen(game, 8));
				dispose();
				break;
//			case 9://testing score screen
//				game.setState(GameScreen.PASSED);
//				game.setScreen(new EndLevelScreen(game, 0, new ScoreCalculator(100, 2000000000, 1, 1, 1)));
//				dispose();
//				break;
			case 10: 
				backToMain.playSound(0);
				Gdx.app.exit();
				dispose(); //TODO confirm message
				break;
			default:
				game.setCurrentPlayer(0);
				game.setCurrentOpponent(0);
				game.setCurrentWeapon(0);
				game.setCurrentStage(0);
				game.setCurrentMusic(game.getCurrentStage());
				game.setScreen(new GameScreen(game, 9));
				dispose();
				break;
			}
		}
		 public void dispose(){
			 	background.stopMusic(0);
		    	background.dispose();
		    	backToMain.dispose();
//		    	fontLang.dispose();
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

	

