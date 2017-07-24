package com.muyagdx.blast;

public class Character extends GameObject {

	static final int PLAYER = 0;
	static final int OPPONENT = 1;
	static final int NOC = 2;
	
	@SuppressWarnings("unused")
	private int type;
	public Throwables weapon;
	
	public Character(int t) {
		super();
		type = t;
		weapon = new Throwables(0);
		
	}
	public Character(int t, int weapont) {
		super();
		type = t;
		weapon = new Throwables(weapont);
		
	}
	public Character(int t, String n) {
		super(n);
		type = t;		
		weapon = new Throwables(0);
		
	}
	
	public Throwables getWeapon() {
		return weapon;
	}
	public void setWeapon(Throwables w){
		weapon = w;
	}
	
}
