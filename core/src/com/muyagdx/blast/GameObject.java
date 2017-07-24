package com.muyagdx.blast;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameObject {
	static final int STATIONARY = 0;
	static final int ONEMOVE = 1;
	static final int CYCLING = 2;
	static final int WAITING = 3;
	static final int EATING = 4;
	static final int TIRED = 5;
	static final int DYING = 6;
	static final int DEAD = 7;
	static final int HITTING = 8;
	static final int DAMAGED = 9; //TODO use these object states to draw texture according to state
	static final int ALIVE = 10;
	static final int DONEMOVING = 11;
	private String name;
	private Rectangle rect;
	private int state;
	private long lastTime;
	private Array<Sound> sounds; 
	private Array<Music> musicList;
	private Texture image;
	private TextureRegion[] keyFrames; //TODO investigate the effect of final
	private TextureRegion[] animateKeyFrames;
	private float frameDuration;
	private boolean selected;
	private boolean disabled;
	private int directionX;
	private long moveTime;
	private int speed;
	private int luminance;
	private int luminanceIncDec;
	private float animateTime;
	private int animateState;
	private int lifeCount;
	private int life;
	
	public GameObject(){
		rect = new Rectangle();
		state = 0;
		animateState = STATIONARY;
		sounds = new Array<Sound>();
		musicList = new Array<Music>();
		frameDuration = 0;
		disabled = false;
		selected = false;
		lastTime = 0;
		directionX = directionY = MathUtils.random(0, 1); 
		moveTime = 0;
		speed = 0;
		luminance = 0;
		luminanceIncDec = 1;
		setLifeCount(1);
		setLife(255);
	}
	public GameObject(String n){
		//constructor type
		
		name = new String(n);
		rect = new Rectangle();
		state = 0;
		animateState = STATIONARY;
		sounds = new Array<Sound>();
		musicList = new Array<Music>();
		frameDuration = 0;
		disabled = false;
		selected = false;
		lastTime = 0;
		directionX = directionY = MathUtils.random(0, 1); 
		moveTime = 0;
	}
	
	public TextureRegion[] getKeyFrames() {
		return keyFrames;
	}
	public void setKeyFrames(TextureRegion[] k) {
		keyFrames = k;
	}
	public int getAnimateState() {
		return animateState;
	}
	public void setAnimateState(int s) {
		animateState = s;
	}
	
	public void enable(){
		disabled = false;
	}
	public void disable(int i){
		if(i == 0) disabled = true;
		else disabled = false;
	}
	public boolean isDisabled(){
		return disabled;
	}
	
	public void deselect(){
		selected = false;
	}
	public void select(){
		selected = true;
	}
	public boolean isSelected(){
		return selected;
	}
	public float getAnimateTime() {
		return animateTime;
	}
	public void setAnimateTime(float a) {
		animateTime = a;
	}
	public void setAnimated (float duration, TextureRegion... keyframes) {
		frameDuration = duration;
		animateKeyFrames = keyframes;
	}

	public TextureRegion getKeyFrame (int mode) {
		int frameNumber = (int)(animateTime / frameDuration);
		//if(frameNumber >= animateKeyFrames.length  && mode != CYCLING) animateState = DONEMOVING;
		if (mode == CYCLING){ 
			frameNumber = frameNumber % animateKeyFrames.length;
		} 
		else{
			frameNumber = Math.min(animateKeyFrames.length - 1, frameNumber);
		}	
		return animateKeyFrames[frameNumber];
	}
	
	public TextureRegion animate(float t){
		animateTime = t;
		TextureRegion keyFrame;
		switch (animateState) {
		case CYCLING:
			keyFrame = getKeyFrame(2); 
			break;
		case ONEMOVE:
			keyFrame = getKeyFrame(1);
			break;
//		case DONEMOVING:
//			keyFrame = getKeyFrame(1);
//			break;
		default:
			keyFrame = new TextureRegion();
			keyFrame.setRegion(image);
		}
		return keyFrame;
	}
	
	
	public Texture getImage(){
		return image;
	}
	public TextureRegion getImageFrame(int index){
		if(index < keyFrames.length) return keyFrames[index];
		else return null;
	}
	public Sprite getSpriteFrame(int index){
		if(index < keyFrames.length){
			Sprite gameObjectSprite = new Sprite(keyFrames[index]);
			return gameObjectSprite;
		}
		else return null;
	}
	public void setImage(String name){
		image = new Texture(Gdx.files.internal(name));
	}
	public void setImageFrames(int divideX, int divideY){ //TODO need to set beginning and ending frames for different animations
		keyFrames = new TextureRegion[divideX*divideY];
		for(int i=0; i < divideX; i++)
			for(int j=0; j < divideY; j++)
				keyFrames[j+i*divideY] = new TextureRegion(image,i*image.getWidth()/divideX, j*image.getHeight()/divideY, image.getWidth()/divideX,image.getHeight()/divideY);
	}
	
	public void setState(int s){
		state = s;
	}
	public int getState(){
		return state;
	}
	public void setName(String n){
		name = new String(n);
	}
	public String getName(){
		return name;
	}
	public void setRect(Rectangle r){
		rect = r;
	}
	public Rectangle getRect(){
		return rect;
	}
	public float getX(){
		return rect.x;
	}
	public float getY(){
		return rect.y;
	}
	public void setX(int x){
		//rect.x=x;
		
		rect.set(x, rect.getY(), rect.getWidth(), rect.getHeight());
	}
	public void setY(int y){
		rect.y=y;
	}
	public void setX(float x){
		rect.x=x;
	}
	public void setY(float y){
		rect.y=y;
	}
	public void enlarge(int x){
		rect.height *= x;
		rect.width *=x;
	}
	public void shrink(int x){
		rect.height = (int) rect.height/x;
		rect.width =(int) rect.width/x;
		
	}
	public void move(int dx, int dy){
		rect.x += dx;
		rect.y += dy;
	
	}
	public void move(float dx, float dy){
		rect.x += dx;
		rect.y += dy;
	
	}
	public void setLastTime (long delta){
		lastTime = delta;
	}
	public long getLastTime (){
		return lastTime;
	}
	public void playSound(int n){ // n = index of track
		if (n >= 0 && n < sounds.size)  sounds.get(n).play(); 
		
	}
	public void playRandSound(){ // n = index of track
		
		sounds.random().play();
	}
	
	public void playMusicLooping(int n){
		if(n >=0 && n < musicList.size){
		Music temp = musicList.get(n);
		temp.setLooping(true);
		//musicList.items[n].play();
		temp.play();
		}
	}
	public void pauseMusic(int n){
		musicList.get(n).pause();
	}
	public void setVolume(int n, float v){
		musicList.get(n).setVolume(v);
	}
	public void stopMusic(int n){
		musicList.get(n).setLooping(false);
		musicList.get(n).stop();
	}
	public int addSound(String fileName){ // write a sound map to add the same sound type to same index
		sounds.add(Gdx.audio.newSound(Gdx.files.internal(fileName))); // set filename
		return sounds.size;
	}
	public int getSounds(){ //returns count of sound files
		return sounds.size;
	}
	public void removeSounds(){
		sounds.removeAll(sounds, false);
	}
	public void removeMusic(){
		musicList.removeAll(musicList, false);
	}
	public int addMusic(String fileName){
		
		musicList.add(Gdx.audio.newMusic(Gdx.files.internal(fileName))); // set filename
		return musicList.size;
	}
	public int getMusicCount(){
		return musicList.size;
	}
	public void playMusic(int n){
		musicList.get(n).setLooping(false);
		musicList.get(n).play();
	}
	//@override 
	public void dispose(){
		if (image != null) image.dispose();
		if (musicList.size != 0) musicList.removeAll(musicList, false);
		if (sounds.size != 0) sounds.removeAll(sounds, false);
		
	}
	public long getLastMoveTime(){
		return moveTime;
	}
	public void setLastMoveTime(long t){
		moveTime = t;
	}
	public void moveRandomVert(){
		int direction =  MathUtils.random(0, 1);
		if(TimeUtils.nanoTime() - getLastMoveTime() > 2000000000){
			directionY = direction;
			setLastMoveTime(TimeUtils.nanoTime());
		}
		   if (directionY == 0) //up
			   {
			   this.setY(this.getY() + MathUtils.random(10, 32)); 
			   if (this.getY()>=460) this.setY(0); // TODO change according to current screen res
			   }
		   else if (directionY == 1) //down
		   {
			   this.setY(this.getY() - MathUtils.random(10, 32));
			   if (this.getY()<=0) this.setY(480);
		   }
	}
public void moveRandomHor(){
	
	   int direction =  MathUtils.random(0, 1);
	   if(TimeUtils.nanoTime() - getLastMoveTime() > 2000000000){
			directionX = direction;
			setLastMoveTime(TimeUtils.nanoTime());
		}
	   if (directionX == 0) 
		   {
		   //float x= this.getX() + MathUtils.random(10, 32);
//		   this.setX(this.getX() + MathUtils.random(10, 32)); 
//		   if (this.getX()>=790) this.setX(0); // TODO change according to current screen res
		   setX(getX() + MathUtils.random(1, 5)); 
		   if (getX()>=790) setX(0); // TODO change according to current screen res
		   
		   }
	   else
	   {
		   //this.setX(this.getX() - MathUtils.random(10, 32));
		   //if (this.getX()<=0) this.setX(800);
		   setX(getX() - MathUtils.random(1, 15));
		   if (getX()<=0) setX(800);
		   
	   }
   
	}
	public void moveRandom(){  //TODO make 4 if statements
		int direction =  MathUtils.random(0, 1);
		if(TimeUtils.nanoTime() - getLastMoveTime() > 2000000000){
			directionX = direction;
			setLastMoveTime(TimeUtils.nanoTime());
		}
	   if (directionX == 0) //up
		   {
		   this.setY(this.getY() + MathUtils.random(10, 32)); 
		   if (this.getY()>=460) this.setY(0); // TODO change according to current screen res
		   }
	   else //down
	   {
		   this.setY(this.getY() - MathUtils.random(10, 32));
		   if (this.getY()<=0) this.setY(480);
	   }

	   int directionY =  MathUtils.random(0, 1);
	   if(TimeUtils.nanoTime() - getLastMoveTime() > 2000000000){
			directionY = direction;
			setLastMoveTime(TimeUtils.nanoTime());
		}
	   if (directionY == 0) 
	   {
	   //float x= this.getX() + MathUtils.random(10, 32);
	   this.setX(this.getX() + MathUtils.random(10, 32)); 
	   if (this.getX()>=790) this.setX(0); // TODO change according to current screen res
	   }
	   else
	   {
	   this.setX(this.getX() - MathUtils.random(10, 32));
	   if (this.getX()<=0) this.setX(800);
	   }
	}	
	
	public void moveRightRandom(int max){
		this.setX(this.getX() + MathUtils.random(0, max)); 
		   if (this.getX()>=790) this.setX(0); // TODO change according to current screen res
	 }
	public void moveLeftRandom(int max){
		this.setX(this.getX() - MathUtils.random(0, max)); 
		if (this.getX()<=0) this.setX(800);
	 }
	public int getDirectionX() {
		return directionX;
	}
	public void setDirectionX(int d) {
		directionX = d;
	}
	private int directionY;
	public int getDirectionY() {
		return directionY;
	}
	public void setDirectionY(int d) {
		directionY = d;
	}
public void autoMoveX(int limit1, int limit2){
	setX(getX()-2 * directionX);
    if(getX() <= limit1){
    	setX(limit1 + 1);
    	directionX *= -1;
    }
    if(getX() >= limit2){
    	setX(limit2 - 1);
    	directionX *= -1;
    }
}
public void autoMoveY(int limit1, int limit2){
	setY(getY()-2 * directionY);
    if(getY() <= limit1){
    	setY(limit1 + 1);
    	directionY *= -1;
    }
    if(getY() >= limit2){
    	setY(limit2+1);
    	directionY *= -1;
    }
}
public void sinMove(int width, int begin, int speed){
	setX((int)(begin +width/2 + ( Math.sin((TimeUtils.nanoTime()+speed)/500000000.0) * width/2)));
}
public void cosMove(int width, int begin, int speed){
	setX((int)(begin +width/2 + ( Math.cos((TimeUtils.nanoTime()+speed)/500000000.0) * width/2)));
}
public void lineMove(float speed){
	setX((int)(getX() + directionX*speed));
	setY((int)(getY() + directionY*speed));
}
public void sinMoveY(int height, int begin, int speed){
	setY((int)(begin +height/2 + ( Math.sin((TimeUtils.nanoTime()+speed)/500000000.0) * height/2)));
}
public void cosMoveY(int height, int begin, int speed){
	setY((int)(begin +height/2 + ( Math.cos((TimeUtils.nanoTime()+speed)/500000000.0) * height/2)));
}
public void autoMoveX(int limit1, int limit2, int speed){
	setX(getX()- speed * directionX);
    if(getX() <= limit1){
    	setX(limit1 + 1);
    	directionX *= -1;
    }
    if(getX() >= limit2){
    	setX(limit2 - 1);
    	directionX *= -1;
    }
}
public void autoMoveY(int limit1, int limit2, int speed){
	setY(getY()- speed * directionY);
    if(getY() <= limit1){
    	setY(limit1 + 1);
    	directionY *= -1;
    }
    if(getY() >= limit2){
    	setY(limit2+1);
    	directionY *= -1;
    }
}
public void autoMove1WayX(int limit, int speed){
	setX(getX()- speed * directionX);
    if(directionX == -1 && getX() >= limit){
    	directionX = 0;
    }
    if(directionX == 1 && getX() <= limit){
    	directionX = 0;
    }
}
public void autoMove1WayY(int limit, int speed){
	setY(getY()- speed * directionY);
    if(directionY == -1 && getY() >= limit){
    	directionY = 0;
    }
    if(directionY == 1 && getY() <= limit){
    	directionY = 0;
    }
}
public void moveWallX(int distance, int wall){
	setX(getX() + distance * directionX);
    if(getX() <= wall || getX() >= wall){
    	setX(wall);
    }
}
public void moveWallY(int distance, int wall){
	setY(getY()- distance * directionY);
    if(getY() <= wall || getY() >= wall){
    	setY(wall);
    }
}
public void moveToOppositeSideX(int distance, int a, int b){ /* moves till exits range of a or b then appears in the opposite end*/
	setX(getX()- distance * directionX);
    if(getY() <= a){
    	setX(b);
    }
    if(getY() >= b){
    	setX(a);
    }
}
public void moveToOppositeSideY(int distance, int a, int b){
	setY(getY()- distance * directionY);
    if(getY() <= a){
    	setY(b);
    }
    if(getY() >= b){
    	setY(a);
    }
}

public void moveNoWallX(int distance){
	setX(getX() + distance * directionX);
}
public void moveNoWallY(int distance){
	setY(getY()- distance * directionY);
}
public int getSpeed() {
	return speed;
}
public void setSpeed(int s) {
	speed = s;
}
public void increaseSpeed(int s) {
	speed += s;
}
public void decreaseSpeed(int s) {
	speed -= s;
}
public int getLuminance() {
	return luminance;
}
public void setLuminance(int luminance) {
	this.luminance = luminance;
}
public int getLuminanceIncDec() {
	return luminanceIncDec;
}
public void setLuminanceIncDec(int luminanceIncDec) {
	this.luminanceIncDec = luminanceIncDec;
}
public void sinLuminance(long amount, int max, int min) {
	setLuminance((int)(min + max/2 + ( Math.sin((TimeUtils.nanoTime()+amount)/500000000.0) * max/2)));
}
public void cycleLuminance(int amount){
	luminance = luminance - amount * luminanceIncDec;
    if(luminance <= 30){
    	luminance = 30;
    	luminanceIncDec *= -1;
    }
    if(luminance >= 100){
    	luminance = 100;
    	luminanceIncDec *= -1;
    }
}
public void cycleLuminance(int amount, int max, int min){
	luminance = luminance - amount * luminanceIncDec;
    if(luminance <= min){
    	luminance = min;
    	luminanceIncDec *= -1;
    }
    if(luminance >= max){
    	luminance = max;
    	luminanceIncDec *= -1;
    }
}
public boolean hit(Rectangle object){
	return this.getRect().overlaps(object);
}
public void incLuminance(int l, int max){
	luminance = luminance + l ;
    if(luminance <= max){
    	luminance = max;
    }
}
public void decLuminance(int l, int min){
	luminance = luminance - l;
    if(luminance >= min){
    	luminance = min;
    }
}
public int getLife() {
	return life;
}
public void setLife(int life) {
	this.life = life;
}
public int getLifeCount() {
	return lifeCount;
}
public void setLifeCount(int lifeCount) {
	this.lifeCount = lifeCount;
}
}
