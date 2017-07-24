package com.muyagdx.blast;

import com.badlogic.gdx.Gdx;


public class Stage extends GameObject {
	final static int STORY = 0; //Use in rendering instead of game mode
	final static int BONUS = 1;
	final static int VS = 2;
	final static int LOVE = 3;
	final static int TIMEATTACK = 4;
	final static int SURVIVAL = 5;
	final static int TUTORIAL = 6;
	final static int RIDDLE = 7;
	
	public GameObject[] sprites;
	private int mode; //TODO change to Type
	private int isBoss; //0 = none, 1 = boss, 2 = boss and miniboss, 3 = miniboss
	public int stageWidth;
	public int stageHeight;
	
	public Stage() {
		// TODO 
		super();
		setSprites(new GameObject[0]);
		setMode(-1);	
		setBoss(0);
	}

	public Stage(String n) {
		super(n);
		setSprites(new GameObject[0]); 
	}
	public Stage(int m) {
		super();
		setSprites(new GameObject[0]);
		setMode(m);
		setBoss(0);
		stageWidth = Gdx.graphics.getWidth();
		stageHeight = Gdx.graphics.getHeight();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int m) {
		mode = m;
	}

	public GameObject[] getSprites() {
		return sprites;
	}

	public void setSprites(GameObject[] s) {
		sprites = s;
	}

	public int hasBoss() {
		return isBoss;
	}

	public void setBoss(int isBoss) {
		this.isBoss = isBoss;
	}

	public void setStage(int stageIndex) {//TODO set collision walls, harmful and safe and destroyable and exploding
		switch(stageIndex){
			case 0:
				sprites = new GameObject[3];
				sprites[0] = new GameObject();
				sprites[1] = new GameObject();
				sprites[2] = new GameObject();
	//			sprites[0].setRect(new Rectangle());
				sprites[1] = new GameObject();
				sprites[2] = new GameObject();
				
		}
	}
	
}
