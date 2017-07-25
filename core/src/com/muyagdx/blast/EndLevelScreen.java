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

public class EndLevelScreen implements Screen {
	final Blast game;
	OrthographicCamera camera;
    private GameObject[] infoBlocks; 
	private Stage background;
    private GameObject backToMain;
	private GameObject next;
	private GameObject hide; //TODO add different
	private GameObject retry;
	private GameObject[] lightRects;
	private ScoreCalculator score;
	private int[] scoreValues;
	private int previousState;
	private int opponentIndex;
	private FontLocalization fontLang;
	
	public EndLevelScreen(final Blast gam, int gameMode, ScoreCalculator sc) {
		this.game = gam;
		score = sc;
		game.rayHandler.setAmbientLight(0.5f, 0.5f, 0.5f, 0.1f);
		game.settings.getPreferences();
//		fontLang = new FontLocalization();
//		game.font =  fontLang.getFontLocale(game.settings.getLanguage());
		background = new Stage();
		background.setMode(gameMode);
		background.setDirectionX(1); /* direction 1 moves to the left if using automove (rethink that)*/
		background.setImage("trees.png"); //TODO replace with a proper mode background, maybe image of winner in stage
        background.setRect(new Rectangle(0,0,800,480));
        background.addMusic("maintheme.mp3"); //TODO replace with mode theme
        background.playMusicLooping(0);
        backToMain = new GameObject();
		backToMain.setImage("quit2.png");
		backToMain.setRect(new Rectangle(480,10,64,64));
		backToMain.addSound("bump1.wav");
		next = new GameObject();
		hide = new GameObject();
		retry = new GameObject();
		next.setImage("buttonframe.png");
		hide.setImage("buttonframe.png");
		retry.setImage("buttonframe.png");
		next.setName("Next");
		hide.setName("Hide");
		retry.setName("Retry");
		next.setRect(new Rectangle(700,10,84,48)); //TODO change all rects to have screen resolution based variables
		hide.setRect(new Rectangle(580,10,84,48));
		retry.setRect(new Rectangle(480,220,84,48));
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        lightRects = new GameObject[6];
        infoBlocks = new GameObject[6];
        for(int i = 0; i < 6; i++){ //TODO change to actual count of score items (time, health, power, speed, difficulty) + blocks for unlocked items
        	infoBlocks[i] = new GameObject(); 
        	lightRects[i] = new GameObject();
        	lightRects[i].setDirectionX(1);
        	lightRects[i].setDirectionY(1);
        	infoBlocks[i].setImage("scoreframe.png"); 
        	infoBlocks[i].setRect(new Rectangle(200, 400 - i*50, 440, 50));	
		    lightRects[i].setRect(new Rectangle(500, 400 - i*50, 200, 50));
        }
        infoBlocks[0].setName("Time: ");
        infoBlocks[1].setName("Power: ");
        infoBlocks[2].setName("Speed: ");
        infoBlocks[3].setName("Health: ");
        infoBlocks[4].setName("Difficulty: ");
        infoBlocks[5].setName("Total: ");
        scoreValues = new int[6];
        scoreValues[0] = (int) score.getTimeScore();
	    scoreValues[1] = (int) score.getPowerScore();
	    scoreValues[2] = (int) score.getSpeedScore();
	    scoreValues[3] = (int) score.getHealthScore();
	    scoreValues[4] = (int) score.getDifficultyScore();
	    scoreValues[5] = (int) score.getTotal();
	    opponentIndex = 0;
	    Gdx.app.log("INFO", "Created End Level Screen, Mode:" + background.getMode() + ", State:" + game.getState());
	}


		@Override
	    public void render(float delta){
			switch(game.getState()){
			case GameBossScreen.PASSED: 
				renderNext(delta);
				break;
			case GameBossScreen.BONUS:
				renderBonus(delta);
				break;
//			case 5:
//				renderBonus(delta);
//				break;
//				//bonus level
//			case 6: //play movie
//			case 7: //record
			case GameBossScreen.RETRY:
				renderRetry(delta);
				break;
			case GameBossScreen.ENDMOVIE:
				renderPlay(delta);
				break;
			case GameBossScreen.DISPLAY:
				renderDisplay(delta);
				break;
			default:
				//TODO render error screen
				Gdx.app.log("Error", "game state not valid: " + game.getState() + "game mode: " + background.getMode());
				game.setState(GameBossScreen.RETRY);
				break;
			}
		}
		public void renderNext(float delta){
			Gdx.gl.glClearColor(0, 0, 0.5f, 1);
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
		    game.font.setColor(0.9f, 0.96f, 0.89f, 1);
		    game.font.setScale(2, 2);
		    //add music
		    camera.update();
		    game.batch.setProjectionMatrix(camera.combined);
		    game.batch.begin();
		    game.batch.setShader(null);
		    game.batch.disableBlending();
		    game.batch.draw(background.getImage(), background.getX(), 0, 800, 480);
		    game.batch.enableBlending();
		    game.batch.draw(backToMain.getImage(), backToMain.getX(), backToMain.getY(), backToMain.getRect().width, backToMain.getRect().height);
		    // TODO score blocks need a bordering glow
		    game.batch.draw(next.getImage(), next.getX(), next.getY(), next.getRect().width, next.getRect().height); 
		    game.batch.draw(hide.getImage(), hide.getX(), hide.getY(), hide.getRect().width, hide.getRect().height);
		    game.font.draw(game.batch, next.getName(), next.getX()+next.getRect().width/2-game.font.getBounds(next.getName()).width/2, next.getY() + 38);
    		game.font.draw(game.batch, hide.getName(), hide.getX()+hide.getRect().width/2-game.font.getBounds(hide.getName()).width/2, hide.getY() + 38);
		    
		    for(int i = 0; i < 5; i++){
		    		//game.batch.draw(infoBlocks[i].getImage(),infoBlocks[i].getX(),infoBlocks[i].getY(), infoBlocks[i].getRect().width, infoBlocks[i].getRect().height);
		    		game.font.draw(game.batch, infoBlocks[i].getName(), infoBlocks[i].getX(), infoBlocks[i].getY() + 38);
		    		game.font.draw(game.batch, String.valueOf(scoreValues[i]), infoBlocks[i].getX() + 350, infoBlocks[i].getY() + 38);
		    }
		    game.font.setScale(2.2f, 2.2f);
		    game.batch.draw(infoBlocks[5].getImage(), infoBlocks[5].getX() - 10, infoBlocks[5].getY());
		    game.font.draw(game.batch, infoBlocks[5].getName(), infoBlocks[5].getX(), infoBlocks[5].getY() + 38);
    		game.font.draw(game.batch, String.valueOf(scoreValues[5]), infoBlocks[5].getX() + 350, infoBlocks[5].getY() + 38);
		    game.batch.end();   
		    if(game.rayHandler.lightList.size>10)//TODO make light width increase and decrease 
		    	game.rayHandler.removeAll();//Clear when lights are above certain number
		    //RayHandler.useDiffuseLight(true);
		    game.rayHandler.setShadows(true);
		    for(int i=0; i < infoBlocks.length; i++){
		    		new PointLight(game.rayHandler, 500, new Color(0,0,1,1), background.getLuminance(), lightRects[i].getX(), lightRects[i].getY()+25);
		    		//new PointLight(game.rayHandler, 500, new Color(0,0,1,1), background.getLuminance(), infoBlocks[i].getRect().width + 2*infoBlocks[i].getX() - lightRects[i].getX(), infoBlocks[i].getRect().height + 2*infoBlocks[i].getY() - lightRects[i].getY()-25);
		    }
		    game.rayHandler.setCombinedMatrix(camera.combined, 600, 20, 300, 900);
		    game.rayHandler.setAmbientLight(0.6f, 0.6f, 0.45f, 0.9f);
		    game.rayHandler.updateAndRender();
		    if (Gdx.input.justTouched()) {
		    	Vector3 touch = new Vector3();
		    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		          camera.unproject(touch);
		          if (next.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		      		  backToMain.playSound(0);
		        	  setScreen(background.getMode());
			    	}
		          else if (hide.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		        	  previousState = game.getState();
		        	  game.setState(GameBossScreen.DISPLAY);
			    	}
		          else if (backToMain.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		        	  backToMain.playSound(0);
		        	  //set state to zero
//		        	  game.setState(0);
//		        	  game.setScreen(new MainMenuScreen(game));
		        	  game.backToMain(); 
		        	  //stop music
		        	  dispose();
		    	}
		    }
		    for(int i=0; i<infoBlocks.length ;i++){
		    	lightRects[i].sinMove((int)infoBlocks[i].getRect().width/5,  (int)(infoBlocks[i].getX()+infoBlocks[i].getRect().width/2+120), 1);
			}
		    background.cycleLuminance(1, 120, 60);
		}
		
		public void renderBonus(float delta){ //TODO change layout to fit scoreboard leading to bonus game (e.g. add button start bonus and change next to skip bonus)
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
		    game.batch.draw(backToMain.getImage(), backToMain.getX(), backToMain.getY(), backToMain.getRect().width, backToMain.getRect().height);
		    // TODO score blocks need a bordering glow
		    // Create and Draw bonus button and make next skip bonus
		    game.batch.draw(next.getImage(), next.getX(), next.getY(), next.getRect().width, next.getRect().height); 
		    game.batch.draw(hide.getImage(), hide.getX(), hide.getY(), hide.getRect().width, hide.getRect().height);
		    game.font.draw(game.batch, next.getName(), next.getX()+next.getRect().width/2-game.font.getBounds(next.getName()).width/2, next.getY() + 38);
    		game.font.draw(game.batch, hide.getName(), hide.getX()+hide.getRect().width/2-game.font.getBounds(hide.getName()).width/2, hide.getY() + 38);
		    
		    for(int i = 0; i < 5; i++){
		    	//game.batch.draw(infoBlocks[i].getImage(),infoBlocks[i].getX(),infoBlocks[i].getY(), infoBlocks[i].getRect().width, infoBlocks[i].getRect().height);
		    	game.font.draw(game.batch, infoBlocks[i].getName(), infoBlocks[i].getX(), infoBlocks[i].getY() + 38);
		    	game.font.draw(game.batch, String.valueOf(scoreValues[i]), infoBlocks[i].getX() + 350, infoBlocks[i].getY() + 38);
		    }
		    game.font.setScale(2.2f, 2.2f);
		    game.font.draw(game.batch, infoBlocks[5].getName(), infoBlocks[5].getX(), infoBlocks[5].getY() + 38);
		    game.font.draw(game.batch, String.valueOf(scoreValues[5]), infoBlocks[5].getX() + 350, infoBlocks[5].getY() + 38);
		    game.batch.end();   
		    if(game.rayHandler.lightList.size>10)//TODO make light width increase and decrease 
		    	game.rayHandler.removeAll();//Clear when lights are above certain number
		    game.rayHandler.setShadows(true);
		    for(int i=0; i < infoBlocks.length; i++){
		    	new PointLight(game.rayHandler, 500, new Color(0,0,1,1), background.getLuminance(), lightRects[i].getX(), lightRects[i].getY()+25);
	    		//new PointLight(game.rayHandler, 500, new Color(0,0,1,1), background.getLuminance(), infoBlocks[i].getRect().width + 2*infoBlocks[i].getX() - lightRects[i].getX(), infoBlocks[i].getRect().height + 2*infoBlocks[i].getY() - lightRects[i].getY()-25);		    
		    }
		    game.rayHandler.setCombinedMatrix(camera.combined, 600, 20, 300, 900);
		    game.rayHandler.setAmbientLight(0.6f, 0.6f, 0.45f, 0.9f);
		    if (Gdx.input.justTouched()) {
		    	Vector3 touch = new Vector3();
		    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		          camera.unproject(touch);
		          if (next.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		      		  backToMain.playSound(0);
		        	  setScreen(background.getMode()); //TODO skip bonus
			    	}
		          else if (hide.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		        	  previousState = game.getState();
		        	  game.setState(GameBossScreen.DISPLAY);
			    	}
		          else if (backToMain.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		        	  backToMain.playSound(0);
		        	//set state to zero
		        	  game.backToMain();
		        	  background.stopMusic(0);
		        	  dispose();
		    	}
		    }
		    for(int i=0; i<infoBlocks.length ;i++){
		    	lightRects[i].sinMove((int)infoBlocks[i].getRect().width/5,  (int)(infoBlocks[i].getX()+infoBlocks[i].getRect().width/2+120), 1);
			}
		    background.cycleLuminance(1, 120, 60);
		}
		
		public void renderRetry(float delta){ //TODO change layout to fit retry screen (e.g. add player hurt full image and load winner image on clicking retry and remove next)
			Gdx.gl.glClearColor(0, 0, 0.5f, 1);
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
		    hide.setRect(new Rectangle(380, 220, 86, 48));
		    backToMain.setRect(new Rectangle(310, 220, 64, 64));
		    game.font.setColor(0.9f, 0.96f, 0.89f, 1);
		    game.font.setScale(2, 2);
		    camera.update();
		    game.batch.setProjectionMatrix(camera.combined);
		    game.batch.begin();
		    game.batch.setShader(null);
		    game.batch.disableBlending();
		    game.batch.draw(background.getImage(), background.getX(), 0, 800, 480);
		    game.batch.enableBlending();
		    game.batch.draw(backToMain.getImage(), backToMain.getX(), backToMain.getY(), backToMain.getRect().width, backToMain.getRect().height);
		    game.batch.draw(retry.getImage(), retry.getX(), retry.getY(), retry.getRect().width, retry.getRect().height); 
		    game.batch.draw(hide.getImage(), hide.getX(), hide.getY(), hide.getRect().width, hide.getRect().height);
		    game.font.draw(game.batch, hide.getName(), hide.getX()+hide.getRect().width/2-game.font.getBounds(hide.getName()).width/2, hide.getY() + 38);
    		game.font.draw(game.batch, retry.getName(), retry.getX()+retry.getRect().width/2-game.font.getBounds(retry.getName()).width/2, retry.getY() + 38);
		    game.batch.end();   
		    if (Gdx.input.justTouched()) {
		    	Vector3 touch = new Vector3();
		    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		          camera.unproject(touch);
		          if (retry.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		      		  backToMain.playSound(0);
		        	  setScreen(background.getMode());//TODO why didnt work? 
		        	  //TODO add button randomize in Instant Fight mode disappears in other modes
			    	}
		          else if (hide.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		      		  previousState = game.getState();
		        	  game.setState(GameBossScreen.DISPLAY);
			    	}
		          else if (backToMain.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
		        	  backToMain.playSound(0);
		        	//set state to zero
		        	  game.backToMain();
		        	  background.stopMusic(0);
		        	  dispose();
		    	}
		    }
		}
		
		public void renderDisplay(float delta){
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
		    game.batch.end();   
		    if (Gdx.input.justTouched()) {
		    	Vector3 touch = new Vector3();
		    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		          camera.unproject(touch);
		          game.setState(previousState);
		    }
		}
		
		public void renderPlay(float delta){
			background.setImage(game.settings.getOppFileName(opponentIndex));
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
		    game.batch.end();   
		    if (Gdx.input.justTouched()) {
		    	Vector3 touch = new Vector3();
		    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		          camera.unproject(touch);
		          game.setScreen(new MainMenuScreen(game));
		    }
		    if(opponentIndex < game.settings.getOppCount()) opponentIndex++;
		    else opponentIndex=0;
		}
		
		public void setScreen(int mode){
			background.stopMusic(0);
			/*  Win cases    */
			if(game.getState() == GameBossScreen.PASSED){
				switch(mode){
				case 0:
					game.setCurrentOpponent(game.getCurrentOpponent()+1);
					if(game.getCurrentOpponent() >= game.settings.getOppCount()){
						game.setState(GameBossScreen.ENDMOVIE);
						game.setScreen(new MovieScreen(game, 0));  //TODO Check which to use, this one or renderPlay?
						dispose();
						break;
					}
					game.setCurrentStage(game.getCurrentStage()+1);
					game.setCurrentMusic(game.getCurrentStage());
					game.setState(GameBossScreen.READY);
					game.setScreen(new GameBossScreen(game, 0));
					dispose();
					break;
					
				case 1:/* play again random */
					game.setCurrentPlayer(MathUtils.random(0, game.settings.getCharCount()-1));
					game.setCurrentOpponent(MathUtils.random(0, game.settings.getOppCount()-1));
					game.setCurrentWeapon(MathUtils.random(0, game.settings.getWeapCount()-1));
					game.setCurrentStage(MathUtils.random(0, game.settings.getStageCount()-1));
					game.setCurrentMusic(game.getCurrentStage());
					game.setState(GameBossScreen.READY);
					game.setScreen(new GameBossScreen(game, mode));
					dispose();
					break;
							//			case 2://TODO create a game with love weapons and NPC characters
							//			case 3://TODO Time attack
							//			case 4://TODO Survival
							//			case 5://TODO Tutorial
							//			case 6://TODO Select from Bonus games
							//			case 7://TODO Settings
							//			case 8://TODO Network settings
				default:
					game.setState(GameBossScreen.READY);
					game.setScreen(new MainMenuScreen(game));
					dispose();
					break;
				}
			}
			/*   RETRY cases  */
			else if(game.getState() == GameBossScreen.RETRY){
				switch(mode){
				case 0:
					game.setState(GameBossScreen.READY);
					game.setScreen(new GameBossScreen(game, background.getMode()));
					dispose();
					break;
				case 1:/* randomize another match */
					game.setCurrentPlayer(MathUtils.random(0, game.settings.getCharCount()-1));
					game.setCurrentOpponent(MathUtils.random(0, game.settings.getOppCount()-1));
					game.setCurrentWeapon(MathUtils.random(0, game.settings.getWeapCount()-1));
					game.setCurrentStage(MathUtils.random(0, game.settings.getStageCount()-1));
					game.setCurrentMusic(game.getCurrentStage());
					game.setState(GameBossScreen.READY);
					game.setScreen(new GameBossScreen(game, 1));
					dispose();
					break;
					//			case 2://TODO create a game with love weapons and NPC characters
					//			case 3://TODO Time attack
					//			case 4://TODO Survival
					//			case 5://TODO Tutorial
					//			case 6://TODO Select from Bonus games
					//			case 7://TODO Settings
					//			case 8://TODO Network settings
				    //			case 9:
				default:
					game.setState(GameBossScreen.READY);
					game.setScreen(new MainMenuScreen(game));
					dispose();
					break;
				}
			}
			/* Bonus cases */
			else if(game.getState() == GameBossScreen.BONUS){
				switch(mode){
				case 0:
					game.setCurrentOpponent(game.getCurrentOpponent()+1);
					if(game.getCurrentOpponent() >= game.settings.getOppCount()){
						game.setState(GameBossScreen.ENDMOVIE);
						game.setScreen(new MovieScreen(game, 0));
						dispose();
						break;
					}
					game.setCurrentStage(game.getCurrentStage()+1);
					game.setCurrentMusic(game.getCurrentStage());
					game.setState(GameBossScreen.READY);
					game.setScreen(new GameBossScreen(game, 0));
					dispose();
					break;
					
				case 1:/* play again random */
					game.setCurrentPlayer(MathUtils.random(0, game.settings.getCharCount()-1));
					game.setCurrentOpponent(MathUtils.random(0, game.settings.getOppCount()-1));
					game.setCurrentWeapon(MathUtils.random(0, game.settings.getWeapCount()-1));
					game.setCurrentStage(MathUtils.random(0, game.settings.getStageCount()-1));
					game.setCurrentMusic(game.getCurrentStage());
					game.setState(GameBossScreen.READY);
					game.setScreen(new GameBossScreen(game, mode));
					dispose();
					break;
							//			case 2://TODO create a game with love weapons and NPC characters
							//			case 3://TODO Time attack
							//			case 4://TODO Survival
							//			case 5://TODO Tutorial
							//			case 6://TODO Select from Bonus games
							//			case 7://TODO Settings
							//			case 8://TODO Network settings
				default:
					game.setState(GameBossScreen.READY);
					game.setScreen(new MainMenuScreen(game));
					dispose();
					break;
				}
			}
			//TODO more IFs for other gamestates 
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

	

