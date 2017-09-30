package com.muyagdx.blast;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.graphics.Texture;
//TODO add game modes screen before level select
public class LevelSelect implements Screen {
//	
	final Blast game;
	OrthographicCamera camera;
    final private Array<GameObject> players;
    final private Array<GameObject> enemies;
    // TODO bonus levels
    final private Array<GameObject> weapons;
    final private Array<GameObject> stages;
	private int selectedPlayer;
	private int selectedEnemy;
	private int selectedWeapon;
	private GameObject start;
	private int playerCount; 
    private int opponentCount;
    private int weaponCount;
    private int stageCount;
    private Stage background;
    private GameObject circleIcon;
	private GameObject backToMain;
	private int selectedStage;
	private FontLocalization fontLang;
	
	
	public LevelSelect(final Blast gam, int mode) {
		game = gam;
		game.settings.getPreferences();
//		fontLang = new FontLocalization();
//		game.font =  fontLang.getFontLocale(game.settings.getLanguage());
		background = new Stage();
		background.setMode(mode); 
		background.setDirectionX(1);
		backToMain = new GameObject();
		backToMain.setImage("quit3.png");
		backToMain.setRect(new Rectangle(0,410,100,64));
		circleIcon = new GameObject();
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        players = new Array<GameObject>();
        enemies = new Array<GameObject>();
        weapons = new Array<GameObject>();
        stages = new Array<GameObject>();
        selectedPlayer = selectedEnemy = selectedWeapon = selectedStage = -1;
        // TODO selected character should have his image loaded in the left or right side
        background.setImage("purpleroses.png"); //TODO replace with a proper game select background
        background.setRect(new Rectangle(0,0,800,480));
        circleIcon.setImage("dropbutton2.png");
        start = new GameObject();
	    start.setImage("start.png");
	    start.setRect(new Rectangle(180, 20, 440, 50));
	    playerCount = game.settings.getCharCount();
	    opponentCount = game.settings.getOppCount();
	    weaponCount = game.settings.getWeapCount();
	    stageCount = game.settings.getStageCount();
	    //TODO change layout to select from three rotating controls
	    //TODO change rects to appear in better positions, may need to set them according to mode
	    for (int i = 0; i < playerCount; i++){// TODO make one method takes gameobject and loads rects  
	    	players.add(new GameObject());
	    	players.get(i).setRect(new Rectangle(i*100 + 20,220,86,86));
	    }
	    for (int i = 0; i < opponentCount; i++){  
	    	enemies.add(new GameObject());
			enemies.get(i).setRect(new Rectangle(i*100 + 20,120,86,86));
	    }
	    for (int i = 0; i < weaponCount; i++){  
		    weapons.add(new GameObject());
		    weapons.get(i).setRect(new Rectangle(700 - i*100,220,86,86));
	    }
	    for (int i = 0; i < stageCount; i++){  
		    stages.add(new GameObject());
		    stages.get(i).setRect(new Rectangle(i*100 + 420,100,86,86));
	    }
		    loadSelectables();
	    
	}
	public void loadSelectables(){
		
		for (int i = 0; i < playerCount; i++){ 
			players.get(i).setImage(game.settings.getCharFileName(i));
			players.get(i).setImageFrames(2, 1);
			players.get(i).setName(game.settings.getCharName(i));
		}
		Gdx.app.log("INFO", players.get(0).getName());
		for (int i = 0; i < opponentCount; i++){
			enemies.get(i).setImage(game.settings.getOppFileName(i));
			enemies.get(i).setImageFrames(3, 1);
			enemies.get(i).setName(game.settings.getOppName(i));
		}

		for (int i = 0; i < weaponCount; i++){
			weapons.get(i).setImage(game.settings.getWeapFileName(i));
			weapons.get(i).setImageFrames(8, 1);
			weapons.get(i).setName(game.settings.getWeapName(i));
		}
		for (int i = 0; i < stageCount; i++){
			stages.get(i).setImage(game.settings.getStageFileName(i));
			stages.get(i).setName(game.settings.getStageName(i));
		}
		
	}
	@Override
	public void render(float delta){
		switch(background.getMode()){
		case 0:
			selectPlayerAndWeapon(delta);
  		  	break;
		case 2:
			//TODO Set selections for VS and connect to network 
			break;
		case 3://TODO love game selections
		case 4://TODO Time attack selections
		case 5://TODO Survival selections
		case 6://TODO Tutorial selections
			selectAll(delta);//TODO add case 6 in gamescreen to show dynamic help and prevent loading endlevel
			break;
		case 7://TODO Select from Bonus games
		case 8://TODO Settings
		case 9://testing score screen
		case 10: 
		default:
			break;
		}
	}
    public void selectPlayerAndWeapon(float delta){
		Gdx.gl.glClearColor(0, 0, 0.5f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
	    game.font.setScale(2, 2);
	    game.font.setColor(0.3f, 0.5f, 0.5f, 1);
	    camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
	    game.batch.begin();
	    game.batch.setShader(null);
	    game.batch.disableBlending();
	    game.batch.draw(background.getImage(), background.getX(), 0, 1600, 480);
	    //TODO load selected character and opponent full images
	    game.batch.enableBlending();
	    game.batch.draw(backToMain.getImage(), backToMain.getX(), backToMain.getY());
	    // TODO rotate buttons
	    game.batch.draw(circleIcon.getImage(), 170, 420, 48, 48); 
	    game.batch.draw(circleIcon.getImage(), 170, 360, 48, 48);
	    
	    game.font.draw(game.batch, game.settings.getModeNames()[background.getMode()], 225, 450); // TODO animate font and move it to a proper place (use images better)
	    game.font.draw(game.batch, "Select your hero!", 220, 400);
	    
	    game.font.setColor(0.7f, 0, 0.7f, 1);
	    game.font.setScale(1.5f, 1.5f);
	    //TODO light the selected
	    for (int i = 0; i < playerCount; i++) 
	    {
	    	if (players.get(i).isSelected()){
	    		game.batch.setShader(null);
	    		game.font.draw(game.batch, players.get(i).getName(), players.get(i).getX(), players.get(i).getY()-10);
	    	}
	    	else
	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
	    	 // TODO make frames
	    	game.batch.draw(players.get(i).getImageFrame(0), players.get(i).getX(), players.get(i).getY(), 86, 86);
	    	
	    }
	    
//	    for (int i = 0; i < opponentCount; i++)
//	    {
//	    	if (enemies.get(i).isSelected()){
//	    		game.batch.setShader(null);
//	    		game.font.draw(game.batch, enemies.get(i).getName(), enemies.get(i).getX(), enemies.get(i).getY()-10);
//	    	}
//	    	else
//	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
//	    	
//	    	game.batch.draw(enemies.get(i).getImageFrame(0), enemies.get(i).getX(), enemies.get(i).getY(), 86, 86);
//	    }//TODO Show current opponent in another screen before fighting
	    
	    for (int i = 0; i < weaponCount; i++) 
	    {
	    	if (weapons.get(i).isSelected()){
	    		game.batch.setShader(null);
	    		game.font.draw(game.batch, weapons.get(i).getName(), weapons.get(i).getX(), weapons.get(i).getY()-10);
	    	}
	    	else
	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
	    	
	    	game.batch.draw(weapons.get(i).getImageFrame(0), weapons.get(i).getX(), weapons.get(i).getY(), 64, 64);
	    }
	    
//	    for (int i = 0; i < stageCount; i++) 
//	    {
//	    	if (stages.get(i).isSelected()){
//	    		game.batch.setShader(null);
//	    		game.font.draw(game.batch, stages.get(i).getName(), stages.get(i).getX(), stages.get(i).getY()-10);
//	    	}
//	    	else
//	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
//	    	
//	    	game.batch.draw(stages.get(i).getImage(), stages.get(i).getX(), stages.get(i).getY(), 86, 86);
//	    }
	    
	    if (selectedPlayer > -1 && selectedWeapon > -1 )
	    	game.batch.setShader(null);
    	else
    		game.batch.setShader(GrayscaleShader.grayscaleShader);
	    game.batch.draw(start.getImage(),start.getX(),start.getY());
	    game.batch.setColor(1,1,1,1);
	    game.batch.setShader(null);
	    game.batch.end();   
	    if (Gdx.input.justTouched()) {
	    	Vector3 touch = new Vector3();
	    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	          camera.unproject(touch);
	        
	    	if (backToMain.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
      		  game.setScreen(new ModeSelect(game));
      		  dispose();
	    	}
	    	  int counter = 0;
	          while (counter < playerCount){
	        	  Rectangle sPlayer = players.get(counter).getRect();
	        	  if (sPlayer.contains(new Rectangle(touch.x,touch.y,1,1))){
	        		  if (players.get(counter).isSelected()) {
	        			  players.get(counter).deselect();
	        			  selectedPlayer = -1;
	        		  }
	        		  else{
	        			  for(int i=0; i < playerCount; i++) players.get(i).deselect();
	        			  players.get(counter).select();
	        			  selectedPlayer = counter;
	        		}
	        	  }
	        	  counter++;
	          }
//	          counter = 0;
//	          while (counter < opponentCount){
//	        	  Rectangle sEnemy = enemies.get(counter).getRect();
//	        	  if (sEnemy.contains(new Rectangle(touch.x,touch.y,1,1))){
//	        		  if (enemies.get(counter).isSelected()) {
//	        			  enemies.get(counter).deselect();
//	        			  selectedEnemy = -1;
//	        		  }
//	        		  else{
//	        			  for(int i=0; i < opponentCount; i++) enemies.get(i).deselect();
//	        			  enemies.get(counter).select();
//	        			  selectedEnemy = counter;
//	        		}
//	        	  }
//	        	  counter++;
//	          }
//TODO change weapon selection to be ingame feature, depending on defeated opponents
	          counter = 0;
	          while (counter < weaponCount){
	        	  Rectangle sWeapon = weapons.get(counter).getRect();
	        	  if (sWeapon.contains(new Rectangle(touch.x,touch.y,1,1))){
	        		  if (weapons.get(counter).isSelected()) {
	        			  weapons.get(counter).deselect();
	        			  selectedWeapon = -1;
	        		  }
	        		  else{
	        			  for(int i=0; i < weaponCount; i++) weapons.get(i).deselect();
	        			  weapons.get(counter).select();
	        			  selectedWeapon = counter;
	        		}
	        	  }
	        	  counter++;
	          }      
//	          counter = 0;
//	          while (counter < stageCount){
//	        	  Rectangle sStage = stages.get(counter).getRect();
//	        	  if (sStage.contains(new Rectangle(touch.x,touch.y,1,1))){
//	        		  if (stages.get(counter).isSelected()) {
//	        			  stages.get(counter).deselect();
//	        			  selectedStage = -1;
//	        		  }
//	        		  else{
//	        			  for(int i=0; i < stageCount; i++) stages.get(i).deselect();
//	        			  stages.get(counter).select();
//	        			  selectedStage = counter;
//	        		}
//	        	  }
//	        	  counter++;
//	          }
	          
	          if (selectedPlayer > -1 && selectedWeapon > -1)
	          {
	        	  // TODO play names as audio
	        	  if (start.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
	        		  game.setCurrentPlayer(selectedPlayer);
	        		  game.setCurrentOpponent(0);//TODO change the zeroes to read from settings current story position
	        		  game.setCurrentWeapon(selectedWeapon); 
	        		  game.setCurrentStage(0);
	        		  game.setCurrentMusic(0);
	        		  game.setScreen(new GameBossScreen(game, background.getMode())); 
	        		  //game.setScreen(new GameScreen(game));
	        		  dispose();
        	  }
	          }
        }
	    background.autoMoveX(-200, 0);
	}
    
    public void selectAll(float delta){
		Gdx.gl.glClearColor(0, 0, 0.5f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
	    game.font.setScale(2, 2);
	    game.font.setColor(0.3f, 0.5f, 0.5f, 1);
	    
	    // TODO put all game objects in one png and load by textureregion (rethink that)
	    camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
	    game.batch.begin();
	    game.batch.setShader(null);
	    //game.batch.setColor(0.1f,0.5f,0.3f,1);
	    game.batch.disableBlending();
	    game.batch.draw(background.getImage(), background.getX(), 0, 1600, 480);
	    //TODO load selected character and opponent full images
	    game.batch.enableBlending();
	    game.batch.draw(backToMain.getImage(), backToMain.getX(), backToMain.getY());
	    // TODO rotate buttons
	    game.batch.draw(circleIcon.getImage(), 170, 420, 48, 48); 
	    game.batch.draw(circleIcon.getImage(), 170, 360, 48, 48);
	    
	    game.font.draw(game.batch, game.settings.getModeNames()[background.getMode()], 225, 450); // TODO animate font and move it to a proper place (use images better)
	    game.font.draw(game.batch, "Select if you dare...!", 220, 400);
	    
	    game.font.setColor(0.7f, 0, 0.7f, 1);
	    game.font.setScale(1.5f, 1.5f);
	    //TODO light the selected
	    for (int i = 0; i < playerCount; i++) 
	    {
	    	if (players.get(i).isSelected()){
	    		game.batch.setShader(null);
	    		game.font.draw(game.batch, players.get(i).getName(), players.get(i).getX(), players.get(i).getY()-10);
	    	}
	    	else
	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
	    	 // TODO make frames
	    	game.batch.draw(players.get(i).getImageFrame(0), players.get(i).getX(), players.get(i).getY(), 86, 86);
	    	
	    }
	    
	    for (int i = 0; i < opponentCount; i++)
	    {
	    	if (enemies.get(i).isSelected()){
	    		game.batch.setShader(null);
	    		game.font.draw(game.batch, enemies.get(i).getName(), enemies.get(i).getX(), enemies.get(i).getY()-10);
	    	}
	    	else
	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
	    	
	    	game.batch.draw(enemies.get(i).getImageFrame(0), enemies.get(i).getX(), enemies.get(i).getY(), 86, 86);
	    }
	    
	    for (int i = 0; i < weaponCount; i++) 
	    {
	    	if (weapons.get(i).isSelected()){
	    		game.batch.setShader(null);
	    		game.font.draw(game.batch, weapons.get(i).getName(), weapons.get(i).getX(), weapons.get(i).getY()-10);
	    	}
	    	else
	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
	    	
	    	game.batch.draw(weapons.get(i).getImageFrame(0), weapons.get(i).getX(), weapons.get(i).getY(), 64, 64);
	    }
	    
	    for (int i = 0; i < stageCount; i++) 
	    {
	    	if (stages.get(i).isSelected()){
	    		game.batch.setShader(null);
	    		game.font.draw(game.batch, stages.get(i).getName(), stages.get(i).getX(), stages.get(i).getY()-10);
	    	}
	    	else
	    		game.batch.setShader(GrayscaleShader.grayscaleShader);
	    	
	    	game.batch.draw(stages.get(i).getImage(), stages.get(i).getX(), stages.get(i).getY(), 86, 86);
	    }
	    
	    // TODO load available opponents and weapons in two rotating rings
	    // Load random selection button (may call it instant fight)
	    if (selectedEnemy > -1 && selectedPlayer > -1 && selectedWeapon > -1 && selectedStage > -1)
	    	game.batch.setShader(null);
    	else
    		game.batch.setShader(GrayscaleShader.grayscaleShader);
	    game.batch.draw(start.getImage(),start.getX(),start.getY());
	    game.batch.setColor(1,1,1,1);
	    game.batch.setShader(null);
	    game.batch.end();   
	    if (Gdx.input.justTouched()) {
	    	Vector3 touch = new Vector3();
	    	  touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	          camera.unproject(touch);
	        
	    	if (backToMain.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
      		  game.setScreen(new ModeSelect(game));
      		  dispose();
	    	}
	    	  int counter = 0;
	          while (counter < playerCount){
	        	  Rectangle sPlayer = players.get(counter).getRect();
	        	  if (sPlayer.contains(new Rectangle(touch.x,touch.y,1,1))){
	        		  if (players.get(counter).isSelected()) {
	        			  players.get(counter).deselect();
	        			  selectedPlayer = -1;
	        		  }
	        		  else{
	        			  for(int i=0; i < playerCount; i++) players.get(i).deselect();
	        			  players.get(counter).select();
	        			  selectedPlayer = counter;
	        		}
	        	  }
	        	  counter++;
	          }
	          counter = 0;
	          while (counter < opponentCount){
	        	  Rectangle sEnemy = enemies.get(counter).getRect();
	        	  if (sEnemy.contains(new Rectangle(touch.x,touch.y,1,1))){
	        		  if (enemies.get(counter).isSelected()) {
	        			  enemies.get(counter).deselect();
	        			  selectedEnemy = -1;
	        		  }
	        		  else{
	        			  for(int i=0; i < opponentCount; i++) enemies.get(i).deselect();
	        			  enemies.get(counter).select();
	        			  selectedEnemy = counter;
	        		}
	        	  }
	        	  counter++;
	          }

	          counter = 0;
	          while (counter < weaponCount){
	        	  Rectangle sWeapon = weapons.get(counter).getRect();
	        	  if (sWeapon.contains(new Rectangle(touch.x,touch.y,1,1))){
	        		  if (weapons.get(counter).isSelected()) {
	        			  weapons.get(counter).deselect();
	        			  selectedWeapon = -1;
	        		  }
	        		  else{
	        			  for(int i=0; i < weaponCount; i++) weapons.get(i).deselect();
	        			  weapons.get(counter).select();
	        			  selectedWeapon = counter;
	        		}
	        	  }
	        	  counter++;
	          }      
	          counter = 0;
	          while (counter < stageCount){
	        	  Rectangle sStage = stages.get(counter).getRect();
	        	  if (sStage.contains(new Rectangle(touch.x,touch.y,1,1))){
	        		  if (stages.get(counter).isSelected()) {
	        			  stages.get(counter).deselect();
	        			  selectedStage = -1;
	        		  }
	        		  else{
	        			  for(int i=0; i < stageCount; i++) stages.get(i).deselect();
	        			  stages.get(counter).select();
	        			  selectedStage = counter;
	        		}
	        	  }
	        	  counter++;
	          }
	          
	          if (selectedEnemy > -1 && selectedPlayer > -1 && selectedWeapon > -1 && selectedStage > -1)
	          {
	        	  // TODO play names as audio
	        	  if (start.getRect().contains(new Rectangle(touch.x,touch.y,1,1))){
	        		  game.setCurrentPlayer(selectedPlayer);
	        		  game.setCurrentOpponent(selectedEnemy);
	        		  game.setCurrentWeapon(selectedWeapon); 
	        		  game.setCurrentStage(selectedStage);
	        		  game.setCurrentMusic(selectedStage);
	        		  game.setScreen(new GameScreen(game, background.getMode())); 
	        		  //game.setScreen(new GameScreen(game));
	        		  dispose();
        	  }
	          }
        }
	    background.autoMoveX(-200, 0);
	}
    
	 public void dispose(){
	    	start.dispose();
	    	background.dispose();
	    	backToMain.dispose();
	    	circleIcon.dispose();
//	    	fontLang.dispose();
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
