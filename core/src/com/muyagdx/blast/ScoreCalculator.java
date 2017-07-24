package com.muyagdx.blast;



public class ScoreCalculator {
//TODO calculations of match score 
	private float healthScore;
	private float timeScore;
	private float speedScore;
	private float powerScore;
	private float difficultyScore;
	public ScoreCalculator(int health, double time, int speed, int power, int difficulty){
		setHealthScore(health);
		setTimeScore((float)(time/1000000.0));
		setSpeedScore(speed);
		setPowerScore(power);
		setDifficultyScore(difficulty);
	}
	public float getTotal(){
		return (healthScore * 100 / 255) + Math.max(100, timeScore) + ((speedScore+powerScore) * 100 / 20) + (difficultyScore * 100 / 5); 
	}
	public float getHealthScore() {
		return healthScore;
	}
	public void setHealthScore(float healthScore) {
		this.healthScore = healthScore;
	}
	public float getTimeScore() {
		return timeScore;
	}
	public void setTimeScore(float timeScore) {
		this.timeScore = timeScore;
	}
	public float getSpeedScore() {
		return speedScore;
	}
	public void setSpeedScore(float speedScore) {
		this.speedScore = speedScore;
	}
	public float getPowerScore() {
		return powerScore;
	}
	public void setPowerScore(float powerScore) {
		this.powerScore = powerScore;
	}
	public float getDifficultyScore() {
		return difficultyScore;
	}
	public void setDifficultyScore(float difficultyScore) {
		this.difficultyScore = difficultyScore;
	}
}
