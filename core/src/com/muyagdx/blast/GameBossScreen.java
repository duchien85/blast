package com.muyagdx.blast;


//import android.view.MotionEvent;

import java.util.Iterator;

import box2dLight.PointLight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

// Idea 1 : The hulk family: my kind family members get frustrated with bad neighbors/acquaintances and each has a stage
//      a) chase shooting then face boss
//		b) Sword vs sword nudge
//		c) holding the other guy and throwing punches and slaps
//		d) spitting
//		e) ...
// Idea 2 : Kids fighting robots
// Idea 3 : Hit or reward people in your gallery
// Idea 4 : Fight your fears (robots, animals, birds, ghosts, arachnids, insects, height, speed, death, terrorism, poverty, disease, public speaking, socializing, etc..)
// Some fears are faced by confronting them, some by embracing them, some by ignoring them, etc...
// things appear blocking your way, some harm u and some slow and some stop and some push back
// Idea 5 : Famous characters with funny features (german batman, gay robocop, ronald mcbolocks, transformers who become animals, etc..)
// Idea 6 : Side by side drive by with crazy kids driving, after crashing the enemy player starts walking and tries to approach enemy who keeps shooting
// Idea 7 : Each character has a personality, the girl is cute but turns to a devil when angry, one of the boys hates every feature of the game and himself and everyone, the other one is silent and has no opinion
// Idea 8 : Use real girls to record girl's voice, in three languages Arabic, English and Japanese
public class GameBossScreen implements Screen {
	static final int READY = 0;
	static final int ACTION = 1;
	static final int PAUSED = 2;
	static final int PASSED = 3;
	static final int BONUS = 4;
	static final int MOVIE = 5;
	static final int RECORD = 6;
	static final int PLAYBACK = 7;
	static final int RETRY = 8;
	static final int ENDMOVIE = 9;
	static final int DISPLAY = 10;
	final Blast game;
	
	private GameObject resume;
	private GameObject pause;
	private GameObject quit;
	private OrthographicCamera camera;
	private Vector3 touchPos;
	private Array<Rectangle> bonuses;
	private Array<Rectangle> specialbonuses;
	private Array<Throwables> throwableA;  
	private Array<Throwables> throwableB;
	//TODO create static fields for frametimes
	private int bonusCollected; // TODO create level class and add these parameters, game class should have totals
	private int specialsGathered;
	private boolean isPerfect;
	private Character player;
	private Opponent opponent;
	private Stage background;
	long readyTime;
	private Rectangle playerAura;
	private Vector3 movePlayer;
	private GameObject blueCircle;
	private int touchControl; // 0 = no control, 1 = move player, 2 = collect weapon, 3 = fire weapon, 4 = pause
	private Texture lifeBar;
	private ScoreCalculator score;

	public GameBossScreen(final Blast gam, int selectedMode){//TODO move selectemode to Blast CLASS
		this.game = gam;
		isPerfect = true;
		touchControl = 0;
		movePlayer = new Vector3();
		player = new Character(0);
		opponent = new Opponent(1);
		player.weapon = new Throwables(0);
		opponent.weapon = new Throwables(0);
		lifeBar = new Texture(Gdx.files.internal("bar.png"));
		background = new Stage(0); 
		background.setMode(selectedMode);
		background.sprites = new GameObject[2];
		background.sprites[0] = new GameObject();
		background.sprites[0].setName("SPEEDUP");
		background.sprites[0].setImage("bonusframes.png");
		background.sprites[0].setImageFrames(5, 1);
		background.sprites[0].setAnimateState(GameObject.CYCLING);
		background.sprites[0].setAnimated(200000000, background.sprites[0].getKeyFrames());
		background.sprites[0].setAnimateTime(TimeUtils.nanoTime());
		background.sprites[0].addSound("gulp1.wav");//TODO change sound
		background.sprites[1] = new GameObject();
		background.sprites[1].setName("POWERUP");
		background.sprites[1].setImage("yellowdropbutton.png");
		background.sprites[1].addSound("movingcar.wav");//TODO change sound
		pause = new GameObject();
		pause.addSound("bump1.wav");
		pause.setRect(new Rectangle(745, 370, 64, 64));
		readyTime = TimeUtils.nanoTime();
		player.setImage(game.settings.getCharFileName(game.getCurrentPlayer()));
		player.setName(game.settings.getCharName(game.getCurrentPlayer()));
		opponent.setImage(game.settings.getOppFileName(game.getCurrentOpponent()));
		opponent.setName(game.settings.getOppName(game.getCurrentOpponent()));
		player.weapon.setImage(game.settings.getWeapFileName(game.getCurrentWeapon()));
		player.weapon.setName(game.settings.getWeapName(game.getCurrentWeapon()));
		player.weapon.setImageFrames(8, 1);
		player.weapon.setAnimateState(GameObject.CYCLING);
		player.weapon.setAnimated(500000000, player.weapon.getImageFrame(0), player.weapon.getImageFrame(1), player.weapon.getImageFrame(2), player.weapon.getImageFrame(3));
		opponent.weapon.setImage(game.settings.getWeapFileName(game.getCurrentOpponent()));
		opponent.weapon.setImageFrames(8, 1);
		opponent.weapon.setName(game.settings.getWeapName(game.getCurrentOpponent()));
		opponent.weapon.setAnimateState(GameObject.CYCLING);
		opponent.weapon.setAnimated(500000000, opponent.weapon.getImageFrame(0), opponent.weapon.getImageFrame(1), opponent.weapon.getImageFrame(2), opponent.weapon.getImageFrame(3));
		background.setImage(game.settings.getStageFileName(game.getCurrentStage()));
		background.setName(game.settings.getStageName(game.getCurrentStage()));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);		     
		pause.setImage("pausebutton.png");
		player.setRect(new Rectangle(800/2-64/2,20,64,64));
		player.setImageFrames(2, 1);
		player.addSound(game.settings.getCharSoundFile(game.getCurrentPlayer(), 0));
		player.addSound(game.settings.getCharSoundFile(game.getCurrentPlayer(), 1));
		playerAura = new Rectangle(800/2-100/2,0,100,100);
		//TODO create opponent aura to get it hit instead of whole body
		//TODO load textures over opponent image and strip them when hit
		opponent.setRect(new Rectangle(800/2-opponent.getImage().getWidth()/6,480-opponent.getImage().getHeight()/2, opponent.getImage().getWidth()/6, opponent.getImage().getHeight()/2));
		opponent.setImageFrames(3, 1);
		opponent.setAnimated(200000000, opponent.getImageFrame(0), opponent.getImageFrame(1));
		opponent.addSound(game.settings.getOppSoundFile(game.getCurrentOpponent(), 0));
		opponent.addSound(game.settings.getOppSoundFile(game.getCurrentOpponent(), 1));
		player.weapon.addSound(game.settings.getWeapSoundFile(game.getCurrentWeapon(), 0));
		opponent.weapon.addSound(game.settings.getWeapSoundFile(game.getCurrentOpponent(), 0));
		// TODO load sounds for:  confirm sound, back sound, player and opponent sounds for talk 
		bonuses = new Array<Rectangle>();
		specialbonuses = new Array<Rectangle>();
		throwableA = new Array<Throwables>();
		throwableB = new Array<Throwables>();
		background.setRect(new Rectangle(0,0,800,480)); // TODO make width and height variable 
		int bgMusicNo = background.addMusic(game.settings.getStageMusicFile(game.getCurrentMusic()));
		Gdx.app.log("INFO", "Current Music: " + game.getCurrentMusic());
		if (bgMusicNo > 0) background.playMusicLooping(0);
		background.sprites[0].setLastTime(TimeUtils.nanoTime());
		background.sprites[1].setLastTime(TimeUtils.nanoTime());
		player.weapon.setLastTime(TimeUtils.nanoTime());
		opponent.weapon.setLastTime(TimeUtils.nanoTime());
		player.setDirectionX(0);
		player.setSpeed(1);
		opponent.setAnimateState(GameObject.CYCLING);
		game.gesture = Blast.NOGESTURE;
	}

	@Override
	public void render(float delta) {
		switch(game.getState()){
		case 0: 
			renderReady(delta);
			break;
		case 1:
			renderAction(delta);
			break;
		case 2:
			if(resume == null){
				resume = new GameObject();
				quit = new GameObject();
				quit.addSound("bump1.wav");
				resume.setRect(new Rectangle(400, 220, 64, 64));
				resume.setImage("lightdropbutton.png");
				quit.setImage("quit3.png");
				quit.setRect(new Rectangle(290, 220, quit.getImage().getWidth(), quit.getImage().getHeight()));
			}
			renderPaused(delta);
			break;
		case 3:
			renderWin(delta);
			break;
		case 4:
			renderBonus(delta);
			break;
			//end screen leads to bonus level
		case 5:
		case 6: //play movie
		case 7: //record
		case 8: //retry
			renderRetry(delta);
			break;
		default:
			render(delta);
			break;
		}


	}

	public void renderReady(float delta){
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		/* load selected level, player, character, weapons in their default positions   */
		blueCircle = new GameObject();
		blueCircle.setImage("dropbutton2.png");
		game.batch.setProjectionMatrix(camera.combined);
		renderBG();
		renderObjects();
		if(Gdx.input.justTouched()) {
			game.setState(ACTION);
			background.setLastTime(TimeUtils.nanoTime());
		}
		// wait 3 seconds or press enter to start
		if(Gdx.input.isKeyPressed(Keys.ENTER) || ( TimeUtils.timeSinceNanos(readyTime)  > 3000000000.0)){
			game.setState(ACTION);
			background.setLastTime(TimeUtils.nanoTime());
			readyTime = TimeUtils.nanoTime();
		}
		/*     set player's automove default (automove means player moves towards the touch/mouseclick    */
		movePlayer.set(player.getX(), player.getY(), 0);
	}


	public void renderAction(float delta){
		Gdx.gl.glClearColor(0.3f, 0.35f, 0.2f, 0.3f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		renderBG();
		renderOpponent();
		renderPlayer();
		renderWeapon();
		renderOpponentWeapon();
		renderBonusItems();
		renderSpecialBonus();
		renderScore();
		renderLight();
		opponentAI();
		/*        set default touch position      */
		touchPos = new Vector3();
		touchPos.set(-600, -600, 0);
		touchListener();
		keyListener();
		/*       wall conditions    */
		if(player.getX() < 0) player.setX(0);
		if(player.getX() > 800 - 64) player.setX(800 - 64);
		/*      spawn stage items conditions   */
		if(TimeUtils.nanoTime() - background.sprites[0].getLastTime() > 6000000000.0) spawnBonus();
		if(TimeUtils.nanoTime() - background.sprites[1].getLastTime() > 10000000000.0) spawnSpecialBonus();
		moveBonusItems();
		moveSpecialBonus();
		moveWeapon();
		moveOpponentWeapon();
		/*     move BG        */ //TODO change to camera and set location as player reduces enemy's health, draw bg as comic with frames
		background.autoMoveY(-500, 0);
		/*     player moves towards the touch/mouseclick    */
		player.autoMove1WayX((int)movePlayer.x-32, player.getSpeed());
		/*     player's aura moves with him/her    */
		playerAura.x=player.getX() - 18;
		/*     player moves towards the touch/mouseclick    */
		player.cycleLuminance(2, 100, 20);
	}
	
	private void touchListener(){
		if(Gdx.input.isTouched()) { //TODO stop the extra effect of touch 
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if(pause.hit(new Rectangle(touchPos.x,touchPos.y,1,1))){
				game.setState(PAUSED);
				pause.playSound(0);
				touchControl = 4;
				game.gesture = Blast.NOGESTURE;
			}
			
			else if (touchPos.y <= (player.getRect().height + player.getY()) && touchPos.y >= player.getY()){
				if(touchPos.x > player.getX()+player.getRect().width) player.setDirectionX(-1);
				else if(touchPos.x < player.getX()) player.setDirectionX(1);
				else player.setDirectionX(0);
				player.autoMove1WayX((int)touchPos.x-32, player.getSpeed());
				movePlayer.set(touchPos.x, touchPos.y, 0); 
				playerAura.x=player.getX() - 18;
				touchControl = 1;
				game.gesture = Blast.NOGESTURE;
			}
			
			//TODO use downgesture instead of playeraura single touch		
			else touchControl = 0;
			
		}
		if (touchControl==0 && (game.gesture == Blast.LEFTGESTURE || game.gesture == Blast.RIGHTGESTURE || game.gesture == Blast.UPGESTURE) && TimeUtils.nanoTime() - player.weapon.getLastTime() > 500000000 && throwableA.size < 15 && player.weapon.getAmmoCount()>0){
			//if ( game.gesture == Blast.UPGESTURE && TimeUtils.nanoTime() - player.weapon.getLastTime() > 500000000 && throwableA.size < 15 && player.weapon.getAmmoCount()>0){
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				spawnWeaponA(game.gesture);
				//TODO sometimes when a weapon is caught it gets fired without touching, check again
				touchControl = 3; 
			}
	}
	private void keyListener(){
		if(Gdx.input.isKeyPressed(Keys.ENTER) && (TimeUtils.nanoTime() - player.weapon.getLastTime() > 500000000) && throwableA.size < 15 && player.weapon.getAmmoCount()>0) {
			spawnWeaponA(new Vector3(player.getX(),player.getY(),0));
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)) game.setState(PAUSED);
		if(Gdx.input.isKeyPressed(Keys.Z)) player.move(-200 * Gdx.graphics.getDeltaTime() - player.getSpeed(), 0) ;
		if(Gdx.input.isKeyPressed(Keys.X)) player.move(200 * Gdx.graphics.getDeltaTime() + player.getSpeed(), 0) ;
		if(Gdx.input.isKeyPressed(Keys.S)){
			Iterator<Throwables> iterCollected = throwableB.iterator();
			while(iterCollected.hasNext()) {
				Throwables throwableBRect = iterCollected.next();
				if(playerAura.overlaps(throwableBRect.getRect())){
					iterCollected.remove();
					//TODO play sound
					player.weapon.increaseAmmo(1);
					if(player.weapon.getAmmoCount()>=5){
						player.weapon.setState(Throwables.SUPER);
						player.weapon.incPower(5);
					}
				}
			}
		}
	}
	private void opponentAI(){
		opponent.moveRandomHor();
		if(TimeUtils.nanoTime() - opponent.weapon.getLastTime() > 1000000000 && throwableB.size < 15) spawnWeaponB();
	}
	private void moveBonusItems(){
		Iterator<Rectangle> iter = bonuses.iterator();
		while(iter.hasNext()) {
			Rectangle bonus = iter.next();
			bonus.y -= 200 * Gdx.graphics.getDeltaTime();
			if(bonus.y + 64 < 0) iter.remove();
			if(bonus.overlaps(player.getRect())) {
				bonusCollected++;
				player.increaseSpeed(1);
				background.sprites[0].playSound(0);
				iter.remove();
			}
		}
	}
	private void moveSpecialBonus(){
		Iterator<Rectangle> iterSpecial = specialbonuses.iterator();
		while(iterSpecial.hasNext()) {
			Rectangle specialbonus = iterSpecial.next();
			specialbonus.y -= 200 * Gdx.graphics.getDeltaTime();
			if(specialbonus.y + 64 < 0) iterSpecial.remove();
			if(specialbonus.overlaps(player.getRect())) {
				specialsGathered++;
				player.weapon.increaseAmmo(2);
				player.weapon.incPower(1);
				background.sprites[1].playSound(0);
				iterSpecial.remove();
			}
		}
	}
	private void moveWeapon(){
		/*        Moving player's weapon      */
		Iterator<Throwables> iterThrowA = throwableA.iterator(); 
		while(iterThrowA.hasNext()) {
			Throwables throwableARect = iterThrowA.next(); 
			if(TimeUtils.nanoTime() - throwableARect.getLastTime() >= 400000000 && throwableARect.getState() == GameObject.HITTING){
				throwableARect.setState(GameObject.DEAD);
				iterThrowA.remove();
			}
			else if(throwableARect.getState() != GameObject.HITTING && throwableARect.getState() != GameObject.DEAD){
				throwableARect.lineMove(200 * Gdx.graphics.getDeltaTime());
				if(throwableARect.getY()  > 480 || throwableARect.getX() > 800 || throwableARect.getX() < 0){
					iterThrowA.remove();
				}
				else if(throwableARect.getRect().overlaps(opponent.getRect())) { 
					opponent.setLife(opponent.getLife() - 250*player.weapon.getPower());
					Gdx.app.log("INFO", "Power: "+player.weapon.getPower() + "/nOpLife: "+opponent.getLife());
					// TODO change opponent image to damaged or happy based on throwable type
					bonusCollected+=5; // TODO make it variable based on item type and power
					opponent.setLastTime(TimeUtils.nanoTime());
					throwableARect.setAnimated(100000000, player.weapon.getImageFrame(4), player.weapon.getImageFrame(5), player.weapon.getImageFrame(6), player.weapon.getImageFrame(7));
					throwableARect.setState(GameObject.HITTING);
					throwableARect.setLastTime(TimeUtils.nanoTime());
					throwableARect.setAnimateState(GameObject.ONEMOVE);
					opponent.playSound(0);//TODO change to hit sound (or happy sound when item is type 1)
					if(opponent.getLife() <= 0){
						game.setState(PASSED);
						// TODO 
						// set mode to playback, in renderplayback animate camera
						// animate enemy dying and player winning
						// handle if items hit player after winning
						// load intermediate movie in story mode
						// do the same when player is defeated
						// set this level as: Clear 
						// set % cleared to equal percentage gathered items + special missions
					}
				}
			}
		}
			
	}
	private void moveOpponentWeapon(){
		/*        Moving opponent's weapon      */	
		Iterator<Throwables> iterThrowB;
		Iterator<Throwables> iterThrowA;
		iterThrowB = throwableB.iterator();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		while(iterThrowB.hasNext()) {
			Throwables throwableBRect = iterThrowB.next();
			if(TimeUtils.nanoTime() - throwableBRect.getLastTime() >= 400000000 && throwableBRect.getState() == GameObject.HITTING){
				throwableBRect.setState(GameObject.DEAD);
				iterThrowB.remove();
			}
			else if(throwableBRect.getState() != GameObject.HITTING && throwableBRect.getState() != GameObject.DEAD){
				throwableBRect.move(0,-200 * Gdx.graphics.getDeltaTime());//TODO movement direction and speed depends on difficulty
				if(throwableBRect.getY() + 64 < 0) iterThrowB.remove();
				else if (playerAura.overlaps(throwableBRect.getRect()) && throwableBRect.getRect().overlaps(new Rectangle(touchPos.x,touchPos.y,1,1))){
					iterThrowB.remove();
					//TODO play sound
					player.weapon.increaseAmmo(1);
					player.weapon.setLastTime(TimeUtils.nanoTime());
					touchControl = 2;
					if(player.weapon.getAmmoCount()>=5) player.weapon.setState(Throwables.SUPER);
				}
				else if(throwableBRect.getRect().overlaps(player.getRect())) {
					// TODO change player image to damaged or happy
					isPerfect = false;
					throwableBRect.setAnimated(100000000, opponent.weapon.getImageFrame(4), opponent.weapon.getImageFrame(5), opponent.weapon.getImageFrame(6), opponent.weapon.getImageFrame(7));
					throwableBRect.setState(GameObject.HITTING);
					throwableBRect.setLastTime(TimeUtils.nanoTime());
					throwableBRect.setAnimateState(GameObject.ONEMOVE);
					if(player.weapon.getPower() > 1) player.weapon.decPower(1);
					player.setLife(player.getLife() - 250*opponent.weapon.getPower());
					if(player.getLife() <= 0) game.setState(RETRY); 
					if(player.getSounds() > 0) player.playSound(0); 
					opponent.playSound(1);
					opponent.weapon.playSound(0); //TODO change to hit sound (or happy sound when item is type 1)
					player.setLastTime(TimeUtils.nanoTime());
					
				}
				else{
					iterThrowA = throwableA.iterator();
					while(iterThrowA.hasNext() && iterThrowB != null) {
						Throwables throwableARect = iterThrowA.next();
						if(throwableARect.getRect().overlaps(throwableBRect.getRect())){ 
							bonusCollected+=1; 
							throwableARect.setAnimated(100000000, player.weapon.getImageFrame(4), player.weapon.getImageFrame(5), player.weapon.getImageFrame(6), player.weapon.getImageFrame(7));
							throwableARect.setState(GameObject.HITTING);	
							throwableARect.setLastTime(TimeUtils.nanoTime());
							throwableARect.setAnimateState(GameObject.ONEMOVE);
							throwableBRect.setAnimated(100000000, opponent.weapon.getImageFrame(4), opponent.weapon.getImageFrame(5), opponent.weapon.getImageFrame(6), opponent.weapon.getImageFrame(7));
							throwableBRect.setState(GameObject.HITTING);
							throwableBRect.setLastTime(TimeUtils.nanoTime());
							throwableBRect.setAnimateState(GameObject.ONEMOVE);
							player.weapon.playSound(0);
							opponent.weapon.playSound(0);
						}
					}
				}	 
			}
		}
	}
	private void moveHandicap(){
		
	}
	public void renderPaused(float delta){
		Gdx.gl.glClearColor(0.5f, 0.8f, 0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.setShader(GrayscaleShader.grayscaleShader);
		game.batch.draw(background.getImage(), background.getX(), background.getY());
		game.batch.setShader(null);
		game.font.setColor(0.9f, 0.5f, 0.5f, 1);
		game.batch.draw(blueCircle.getImage(), 745, 230);
		game.batch.draw(blueCircle.getImage(), 8, 230);
		game.font.draw(game.batch, String.valueOf(bonusCollected), 750, 270);
		game.font.draw(game.batch, String.valueOf(specialsGathered), 12, 270);
		game.font.draw(game.batch, "Waiting for your kind return...!", 250, 340);
		//TODO change options to menu items with text
		game.batch.draw(resume.getImage(), resume.getX(), resume.getY());  
		game.batch.draw(quit.getImage(), quit.getX(), quit.getY());
		game.batch.end();
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			game.setState(ACTION);
			resume.dispose();
			quit.dispose();
		}
		if(Gdx.input.isTouched()) {
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if (resume.getRect().contains(new Rectangle(touchPos.x,touchPos.y,1,1))){
				game.setState(ACTION);
				resume.dispose();
				quit.dispose();

			}
			else if (quit.getRect().contains(new Rectangle(touchPos.x,touchPos.y,1,1)))
			{
				game.setScreen(new MainMenuScreen(game)); // TODO load confirm screen		        		 
				background.stopMusic(0);
				quit.playSound(0);
				game.setState(READY);
				dispose();

			}
		}
		if(Gdx.input.isKeyPressed(Keys.ENTER)){
			game.setState(ACTION);
			resume.dispose();
			quit.dispose();

		}   
	}
	
	public void renderBG(){
		game.batch.disableBlending();
		game.batch.begin();
		game.batch.draw(background.getImage(), 0, background.getY(), 800, 1000);
		game.batch.end();
	}
	private void renderObjects() {
		game.batch.enableBlending();
		game.batch.begin();
		game.batch.draw(player.getImageFrame(0), player.getX(), player.getY());
		game.batch.draw(opponent.getImageFrame(0), opponent.getX(), opponent.getY(), 0, 0, opponent.getImage().getWidth()/3, opponent.getImage().getHeight(), 0.5f, 0.5f, 0);
		game.font.setScale(2);
		game.font.setColor(0.9f, 0.5f, 0.5f, 1);
		game.batch.draw(blueCircle.getImage(),745,430);
		game.batch.draw(blueCircle.getImage(),5,430);
		game.font.draw(game.batch, String.valueOf(bonusCollected), 750, 470);
		game.font.draw(game.batch, String.valueOf(specialsGathered), 12, 470);
		game.font.draw(game.batch, "Get Ready!", 370, 230); //animate text (zoom and countdown)
		game.batch.end();		
	}
	private void renderPlayer(){
		game.batch.begin();
		game.batch.enableBlending();
		/*       If player hit display second image in texture map for 2 secs   */
		// TODO display faces when u hit or get hit as a sideframe, and replace player sprite with full body sprites
		if(TimeUtils.nanoTime() - player.getLastTime() > 2000000000)
			game.batch.draw(player.getImageFrame(0), player.getX(), player.getY());
		else
			game.batch.draw(player.getImageFrame(1), player.getX(), player.getY());
		game.batch.end();
	}
	private void renderOpponent(){
		game.batch.begin();
		game.batch.enableBlending();
		//TODO flip image according to direction
		if(TimeUtils.nanoTime() - opponent.getLastTime() > 2000000000) {
			game.batch.draw(opponent.animate(TimeUtils.nanoTime()), opponent.getX(), opponent.getY(), 0, 0, opponent.getImage().getWidth()/3, opponent.getImage().getHeight(), 0.5f, 0.5f, 0);
		}
		else{
			//game.batch.draw(opponent.getImageFrame(0), opponent.getX(), opponent.getY(), opponent.getRect().width/2, opponent.getRect().height/2, opponent.getRect().width, opponent.getRect().height, 1, 1, 90);
			game.batch.draw(opponent.getImageFrame(2), opponent.getX(), opponent.getY(), 0, 0, opponent.getImage().getWidth()/3, opponent.getImage().getHeight(), 0.5f, 0.5f, 0);
		}
		game.batch.end();
	}
	private void renderWeapon(){
		game.batch.begin();
		game.batch.enableBlending();
		for(Throwables throwablesA: throwableA) {
			game.batch.draw(throwablesA.animate(TimeUtils.nanoTime() - throwablesA.getLastTime()), throwablesA.getX(), throwablesA.getY(), player.weapon.getImage().getWidth()/16, player.weapon.getImage().getHeight()/2, player.weapon.getImage().getWidth()/8, player.weapon.getImage().getHeight(), 1, 1, 180);
		}
		game.batch.end();
	}
	private void renderOpponentWeapon(){
		game.batch.begin();
		game.batch.enableBlending();
		for(Throwables throwablesB: throwableB) {
			game.batch.draw(throwablesB.animate(TimeUtils.nanoTime() - throwablesB.getLastTime()), throwablesB.getX(), throwablesB.getY(), opponent.weapon.getImage().getWidth()/16, opponent.weapon.getImage().getHeight()/2, opponent.weapon.getImage().getWidth()/8, opponent.weapon.getImage().getHeight(), 1, 1, 0);
			//game.batch.draw(opponent.weapon.animate(TimeUtils.nanoTime()), throwablesB.getX(), throwablesB.getY(), 64, 64);
		}
		game.batch.end();
	}
	private void renderBonusItems(){
		game.batch.begin();
		game.batch.enableBlending();
		for(Rectangle bonus: bonuses) {
			game.batch.draw(background.sprites[0].animate(TimeUtils.nanoTime()), bonus.x, bonus.y);
		}
		game.batch.end();
	}
	private void renderSpecialBonus(){
		game.batch.begin();
		game.batch.enableBlending();
		for(Rectangle special: specialbonuses) {
			game.batch.draw(background.sprites[1].getImage(), special.x, special.y);
		}
		game.batch.end();
	}
	private void renderHandicaps(){
		game.batch.begin();
		game.batch.enableBlending();
		game.batch.end();
	}
	private void renderScore(){
		game.batch.begin();
		game.batch.enableBlending();
		game.font.setColor(0.9f, 0.5f, 0.5f, 1);
		game.batch.draw(blueCircle.getImage(),745,430);
		game.batch.draw(blueCircle.getImage(),5,430);
		game.batch.draw(lifeBar, 20, 450, opponent.getLife(), 20);
		game.batch.draw(lifeBar, 800-player.getLife(), 450, player.getLife(), 20);
		game.batch.draw(pause.getImage(), pause.getX(), pause.getY());
		game.font.draw(game.batch, String.valueOf(bonusCollected), 750, 470); //TODO replace by speed bar, power bar and score
		game.font.draw(game.batch, String.valueOf(specialsGathered), 12, 470);  
		game.batch.end();
	}
	private void renderLight(){
		game.rayHandler.removeAll();
		if(player.weapon.getState()==Throwables.SUPER){
			game.rayHandler.setAmbientLight(0.3f, 0.3f, 0.5f, 0.1f);
			new PointLight(game.rayHandler, 200, new Color(0.8f,0.8f,0.8f,0.8f), player.getLuminance(), player.getX()+player.getRect().width/2, player.getY()+player.getRect().height/2);
			game.rayHandler.setCombinedMatrix(camera.combined);
			game.rayHandler.updateAndRender();  
		}
	}
		
	public void renderWin(float delta){
		player.playSound(1);//TODO add different win sound
		score = new ScoreCalculator(player.getLife(), TimeUtils.nanoTime()-background.getLastTime(), player.getSpeed(), player.weapon.getPower(), 1);
		background.stopMusic(0);
		if(isPerfect){
			game.setState(BONUS);
		}
		game.setScreen(new EndLevelScreen(game, background.getMode(), score)); // TODO difficulty based on settings and mode		        		 
		background.stopMusic(0);
		dispose();

	}
	public void renderRetry(float delta){
		background.stopMusic(0);
		score = new ScoreCalculator(player.getLife(), TimeUtils.nanoTime()-background.getLastTime(), player.getSpeed(), player.weapon.getPower(), 1);
		game.setScreen(new EndLevelScreen(game, background.getMode(), score)); 	        		 
		dispose(); // TODO check if retry can continue without restart
	}

	public void renderBonus(float delta){//TODO replace with a class and call it in endlevelscreen and modeselect
		score = new ScoreCalculator(player.getLife(), TimeUtils.nanoTime()-background.getLastTime(), player.getSpeed(), player.weapon.getPower(), 1);
		game.setState(PASSED);
//		background.stopMusic(0);
//		game.setScreen(new EndLevelScreen(game, background.getMode(), score)); 
//		//TODO Change to bonus screen (maybe random or depends on other parameters) and send correct mode
//		dispose();
	}

	@Override
	public void dispose() {
		background.dispose();
	}
	private void spawnSpecialBonus() {
		Rectangle specialbonus = new Rectangle();
		specialbonus.x = MathUtils.random(0, 800-64);
		specialbonus.y = 480;
		specialbonus.width = 64;
		specialbonus.height = 64;
		specialbonuses.add(specialbonus);
		background.sprites[1].setLastTime(TimeUtils.nanoTime());
		for(Throwables throwablesB: throwableB) {
			if(throwablesB.getRect().overlaps(specialbonus)) {
				specialbonuses.pop();
				return;
			}
		}
		for(Rectangle bonus: bonuses) {
			if(bonus.overlaps(specialbonus)){
				specialbonuses.pop();
				return;
			}
		}
	}

	private void spawnBonus() {
		Rectangle bonus = new Rectangle();
		bonus.x = MathUtils.random(0, 800-64);
		bonus.y = 480;
		bonus.width = 64;
		bonus.height = 64;
		bonuses.add(bonus);
		background.sprites[0].setLastTime(TimeUtils.nanoTime());
		for(Throwables throwablesB: throwableB) {
			if(throwablesB.getRect().overlaps(bonus)) {
				bonuses.pop();
				return;
			}
		}
		for(Rectangle sbonus: specialbonuses) {
			if(sbonus.overlaps(bonus)){
				bonuses.pop();
				return;
			}
		}
	}
	//TODO create control and method to fire 5 at once (rethink that)
	private void spawnWeaponA(Vector3 v) {
		Throwables throwingA = new Throwables(0);
		throwingA.setRect(new Rectangle(player.getX(),0,64,64));
		throwingA.setDirectionX(0);
		throwingA.setDirectionY(1);
		throwingA.setImage(game.settings.getWeapFileName(game.getCurrentWeapon()));
		throwingA.setImageFrames(8, 1);
		throwingA.setAnimated(100000000, player.weapon.getImageFrame(0), player.weapon.getImageFrame(1), player.weapon.getImageFrame(2), player.weapon.getImageFrame(3));
		//throwingA.setAnimateTime(TimeUtils.nanoTime());//remove
		throwingA.setAnimateState(GameObject.CYCLING);
		throwingA.setState(GameObject.ALIVE);
		throwingA.setLastTime(TimeUtils.nanoTime());
		throwableA.add(throwingA);
		player.weapon.setLastTime(TimeUtils.nanoTime());
		player.weapon.decreaseAmmo(1);
		if(player.weapon.getAmmoCount()==4){
			player.weapon.setState(Throwables.NORMAL);
			if(player.weapon.getPower()>5) player.weapon.decPower(5);
			else player.weapon.setPower(1);
		}
		throwingA.dispose();
	}
	private void spawnWeaponA(int gesture) {
		Throwables throwingA = new Throwables(0);
		throwingA.setRect(new Rectangle(player.getX(),0, 48, 64));
		// Don't use automove with this, it will ruin their directions
		//Gdx.app.log("INFO", "Gesture = " + gesture);
		if(gesture == Blast.RIGHTGESTURE) throwingA.setDirectionX(1);
		else if(gesture == Blast.LEFTGESTURE) throwingA.setDirectionX(-1);
		else if(gesture == Blast.UPGESTURE) throwingA.setDirectionX(0);
		throwingA.setDirectionY(1);
		game.gesture = Blast.NOGESTURE;
		throwingA.setAnimated(100000000, player.weapon.getImageFrame(0), player.weapon.getImageFrame(1), player.weapon.getImageFrame(2), player.weapon.getImageFrame(3));
		//throwingA.setAnimateTime(TimeUtils.nanoTime()); //remove
		throwingA.setLastTime(TimeUtils.nanoTime());
		throwingA.setAnimateState(GameObject.CYCLING);
		throwingA.setState(GameObject.ALIVE);
		throwableA.add(throwingA);
		player.weapon.setLastTime(TimeUtils.nanoTime());
		player.weapon.decreaseAmmo(1);
		if(player.weapon.getAmmoCount()==4){
			player.weapon.setState(Throwables.NORMAL);
			if(player.weapon.getPower()>5) player.weapon.decPower(5);
			else player.weapon.setPower(1);
		}
		throwingA.dispose();
	}
	private void spawnWeaponB() {
		Throwables throwingB = new Throwables(0);
		throwingB.setRect(new Rectangle(opponent.getX(),420,64,64));
		throwingB.setAnimated(100000000, opponent.weapon.getImageFrame(0), opponent.weapon.getImageFrame(1), opponent.weapon.getImageFrame(2), opponent.weapon.getImageFrame(3));
		//throwingB.setAnimateTime(TimeUtils.nanoTime()); //remove
		throwingB.setLastTime(TimeUtils.nanoTime());
		throwingB.setAnimateState(GameObject.CYCLING);
		throwingB.setState(GameObject.ALIVE);
		throwableB.add(throwingB);
		opponent.weapon.setLastTime(TimeUtils.nanoTime());
		for(Rectangle sbonus: specialbonuses) {
			if(sbonus.overlaps(throwingB.getRect())){
				throwableB.pop();
				throwingB.dispose();
				return;
			}
		}
		for(Rectangle bonus: bonuses) {
			if(bonus.overlaps(throwingB.getRect())){
				throwableB.pop();
				throwingB.dispose();
				return;
			}
		}
		throwingB.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// TODO set music to level and state

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}

