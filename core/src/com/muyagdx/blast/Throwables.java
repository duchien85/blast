package com.muyagdx.blast;

//import String;

public class Throwables extends GameObject {

	static final int NORMAL = 0;
	static final int SUPER = 1; //more speed + more power
	static final int WEAK = 2;
	static final int INVISIBLE = 3;
	static final int HYPER = 4; //can remove enemy's weapon and not get removed (breakthrough weapons and/or objects)
	//TODO add more
	private int type; // good throwable = 0 , bad throwable = 1
	
	private int power; // item power scale 0 to x
	private int speed; // item movement speed scale 1 to x
	private int size; // item drawable multiplier scale 1 to 4
	private int ammoCount;
	
	public Throwables(int t){
		//constructor type
		super();
		type = t;
		power = 1;
		speed = 1;
		size = 1;
		setAmmoCount(1);
	}
	
	
	public void setPower(int p){
		power = p;
	}
	public void incPower(int p){
		power += p;
	}
	public void decPower(int p){
		power -= p;
		if (power<0) power = 0;
	}
	public void setSpeed(int s){
		speed = s;
	}
	public void setSize(int s){
		size = s;
	}
	public void incSpeed(int s){
		speed += s;
	}
	public void decSpeed(int s){
		speed -= s;
		if (speed<1) speed = 1;
	}
	public int getPower(){
		return power;
	}
	public int getSpeed(){
		return speed;
	}
	public int getSize(){
		return size;
	}
	public int getType(){
		return type;
	}


	public void increaseAmmo(int i) {
		setAmmoCount(getAmmoCount() + i);
	}
	public void decreaseAmmo(int i) {
		setAmmoCount(getAmmoCount() - i);
		if (ammoCount<=0) setAmmoCount(0);
	}

	public int getAmmoCount() {
		return ammoCount;
	}


	public void setAmmoCount(int a) {
		ammoCount = a;
	}
}
