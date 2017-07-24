package com.muyagdx.blast;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;

import java.sql.Time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class Settings {
	// TODO Save changes to files
	final Preferences prefs;
	//final String mainSettingsFile;
	//final String failMessage;
	//private String mainSettings;
	private int countCharacters;
	private int countOpponents;
	private int countWeapons;
	private int countStages;
	private int countModes;
	private int countCharacterSounds;
	private int countOpponentSounds;
	private int countWeaponSounds;
	
	private String language;
	private String[] characterNames;
	private String[] opponentNames;
	private String[] weaponNames;
	private String[] stageNames;
	private String[] modeNames;
	
	private String[] characterFileNames;
	private String[] opponentFileNames;
	private String[] weaponFileNames;
	private String[] stageFileNames;
	private String[] characterBGNames;
	private String[] opponentBGNames;
	private String[] stageMusicFileNames;
	private String[][] opponentSoundFileNames;
	private String[][] characterSoundFileNames;
	private String[][] weaponSoundFileNames;
	private String[] modeStatus;
	
	private double highestScore;
	private double singleMatches;
	private Time timePlayed;
	private double itemsCollected;
	private int charactersUsed;
	private int weaponsUsed;
	private double itemsFired;
	private Time quickestWin;
	private int highestDifficultyFinish;
	private int perfectMatches;
	
	static final String defaultLang = "En";
	static final String defaultCharCount = "3";
	static final String defaultCharSoundsCount = "2";
	static final String defaultOppoCount = "4";
	static final String defaultOppoSoundsCount = "2";
	static final String defaultWeapCount = "4";
	static final String defaultWeapSoundsCount = "1";
	static final String defaultStageCount = "4";
	static final String defaultModeCount = "11";
	//TODO use localization bundles instead (rethink that)
	static final String defaultCharNames = "05FARAH05KARIM04SEIF";
	static final String defaultCharNamesAr = "03فرح04كريم03سيف";
	static final String defaultCharNamesJp = "03ファラ03カリム03セイフ";
	static final String defaultOppoNames = "06GUNBAD13OCTOPUS PRIME10METAL FEAR08ROBOCOCK";
	static final String defaultWeapNames = "08FIREBALL09BLUECOMET08MISSILES05MINES";
	static final String defaultWeapNamesAr = "03نار04شهاب05صاروخ03لغم";
	static final String defaultWeapNamesJp = "01火04コメット04ロケット03マイン";
	
	static final String defaultStageNames = "06TEMPLE06MARINE05HOUSE05BEACH";
	static final String defaultModeNames = "05STORY13INSTANT FIGHT02VS04LOVE11TIME ATTACK08SURVIVAL08TUTORIAL05BONUS08SETTINGS07NETWORK04QUIT";
	static final String defaultModeNamesJp = "05ストーリー04ランダム01無01無01無01無01無01無02設定01無02終了";
	
	static final String defaultModeStatus = "11000000101";
	static final String defaultCFNames = "13farahface.png13karimface.png12seifface.png";
	static final String defaultOFNames = "17badboboframes.png17octopusframes.png19metalgearframes.png18robocockframes.png";
	static final String defaultWFNames = "12fireball.png13bluecomet.png17missileframes.png09mines.png";
	static final String defaultWSFNames = "11explode.wav10crash1.wav10burst1.wav12explode1.wav";
	static final String defaultCFFNames = "13farahfull.png13karimfull.png12seiffull.png";
	static final String defaultCSFNames = "101ouch1.wav10giggle.wav102gawd1.wav122winner1.wav103ouch1.wav123winner1.wav";
	static final String defaultOFFNames = "14BadRoboBig.png11octopus.png16metalgearbig.png15robocockbig.png";
	static final String defaultOSFNames = "11evilcry.wav13evillaugh.wav08clap.wav12octopus1.wav09tone1.wav10shock1.wav09robo1.wav09robo2.wav";
	static final String defaultSFNames = "15templesmall.png15marinesmall.png12collage2.png08sand.png";
	static final String defaultSMFNames = "10temple.mp310marine.mp316battleground.mp316battleground.mp3";//TODO add fourth stage and music
	static final int defaultTopScore = 1000000;
	
	
	public Settings(){// throws IOException {
		//mainSettingsFile = "blastconfigs/mainconfig";
		//failMessage = "Couldn't write log"; //TODO remove
//		if(readSettings() == -1){
//		writeDefaultSettings();
//	}
//		loadSettings();     // TODO Enable in desktop version only
		prefs =  Gdx.app.getPreferences("My Preferences");
		if(prefs.equals(null) || prefs.getString("Number of Characters") != defaultCharCount || prefs.getString("Number of Opponents") != defaultOppoCount || prefs.getString("Number of Weapons") != defaultWeapCount || prefs.getString("Number of Stages") != defaultStageCount || prefs.getString("Number of Modes") != defaultModeCount){
			writeDefaultPreferences();
		}
		getPreferences();
	}


//private int readSettings() throws IOException {
//	
//	try {
//		BufferedReader br;
//		FileReader filer = new FileReader(mainSettingsFile);
//		br = new BufferedReader(filer);
//		    	
//	    	StringBuilder sb = new StringBuilder();
//	        String line = br.readLine();
//
//	        while (line != null) {
//	            sb.append(line);
//	            sb.append(System.lineSeparator());
//	            line = br.readLine();
//	        }
//	        mainSettings = sb.toString();
//	        br.close();
//	        return 1;
//	    } catch (IOException e){
//	    	return -1;
//	    }
//	}
public String[] getCharNames(){
	return characterNames;
}
public String[] getOppNames(){
	return opponentNames;
}
public String[] getWeapNames(){
	return weaponNames;
}
public String[] getStageNames(){
	return stageNames;
}
public String[] getModeNames() {
	return modeNames;
}
public String getModeStatus(int i) {
	return modeStatus[i];
}
public String[] getCharFileNames() {
	return characterFileNames;
}
public String[] getOppFileNames(){
	return opponentFileNames;
}
public String[] getWeapFileNames(){
	return weaponFileNames;
}
public String[] getStageFileNames(){
	return stageFileNames;
}

public String getCharName(int i){
	if(i < characterNames.length) return characterNames[i];
	else return "Empty";
}
public String getOppName(int i){
	if(i < opponentNames.length) return opponentNames[i];
	else return "Empty";
}
public String getWeapName(int i){
	if(i < weaponNames.length) return weaponNames[i];
	else return "Empty";
}
public String getStageName(int i){
	if(i < stageNames.length) return stageNames[i];
	else return "Empty";
}

public String getCharFileName(int i) {
	if(i < characterFileNames.length) return characterFileNames[i];
	else return "empty.png";
}
public String getCharBGFileName(int i) {
	if(i < characterBGNames.length) return characterBGNames[i];
	else return "empty.png";
}
public String getCharSoundFile(int i, int j) {
	if(i*j < characterSoundFileNames.length) return characterSoundFileNames[i][j]; 
	else return "empty.wav";
}
public String getOppFileName(int i){
	if(i < opponentFileNames.length) return opponentFileNames[i];
	else return "empty.png";
}
public String getOppBGFileName(int i){
	if(i < opponentBGNames.length) return opponentBGNames[i];
	else return "empty.png";
}
public String getOppSoundFile(int i, int j){
	if(i*j < opponentSoundFileNames.length) return opponentSoundFileNames[i][j];
	else return "empty.wav";
}
public String getWeapFileName(int i){
	if(i < weaponFileNames.length) return weaponFileNames[i];
	else return "empty.png";
}
public String getWeapSoundFile(int i, int j){
	if(i*j < weaponSoundFileNames.length) return weaponSoundFileNames[i][j];
	else return "empty.wav";
}
public String getStageFileName(int i){
	if(i < stageFileNames.length) return stageFileNames[i];
	else {
		//TODO writeDefaultPreferences();
		return "empty.png";
	}
}
public String getStageMusicFile(int i) {
	if(i < stageMusicFileNames.length) return stageMusicFileNames[i];
	else return "empty.wav";
}

public void setCharNames(String[] names){
	characterNames = names;
}
public void setOppNames(String[] names){
	opponentNames = names;
}
public void setWeapNames(String[] names){
	weaponNames = names;
}
public void setStageNames(String[] names){
	stageNames = names;
}
public void setModeNames(String[] m) {
	modeNames = m;
}
public void setCharFileNames(String[] names){
	characterFileNames = names;
}
public void setOppFileNames(String[] names){
	opponentFileNames = names;
}
public void setWeapFileNames(String[] names){
	weaponFileNames = names;
}
public void setStageFileNames(String[] names){
	stageFileNames = names;
}

public void setCharName(int index, String name){
	characterNames[index] = name;
}
public void setOppName(int index, String name){
	opponentNames[index] = name;
}
public void setWeapName(int index, String name){
	weaponNames[index] = name;
}
public void setStageName(int index, String name){
	stageNames[index] = name;
}
public void addPlayer(String name){
	countCharacters++;
	String temp[] = new String [countCharacters];
	temp = characterNames;
	temp[characterNames.length] = name;
	characterNames = temp;
	temp = new String [countCharacters];
	temp = characterFileNames;
	temp[characterFileNames.length] = name;
	characterFileNames = temp;
	String all = "";
	for(int i=0; i < characterNames.length; i++){
	if (i < 10){
		all.concat("0");
		all = all.concat(String.valueOf(characterNames[i].length())).concat(characterNames[i]); //TODO check if causes errors
	}
	else all = all.concat(String.valueOf(characterNames[i].length())).concat(characterNames[i]); //TODO check if causes errors
	}
}
public void addOpponent(String name){
	countOpponents++;
	String temp[] = new String [countOpponents];
	temp = opponentNames;
	temp[opponentNames.length] = name;
	opponentNames = temp;
	temp = new String [countOpponents];
	temp = opponentFileNames;
	temp[opponentFileNames.length] = name;
	opponentFileNames = temp;
	String all = "";
	for(int i=0; i < opponentNames.length; i++){
	if (i < 10){
		all.concat("0");
		all = all.concat(String.valueOf(opponentNames[i].length())).concat(opponentNames[i]); //TODO check if causes errors
	}
	else all = all.concat(String.valueOf(opponentNames[i].length())).concat(opponentNames[i]); //TODO check if causes errors
	}
}
public void addWeapon(String name){
	countWeapons++;
	String temp[] = new String [weaponNames.length+1];
	temp = weaponNames;
	temp[weaponNames.length] = name;
	weaponNames = temp;
	temp = new String [countCharacters];
	temp = characterFileNames;
	temp[characterFileNames.length] = name;
	characterFileNames = temp;
	
	String all = "";
	for(int i=0; i < weaponNames.length; i++){
	if (i < 10){
		all.concat("0");
		all = all.concat(String.valueOf(weaponNames[i].length())).concat(weaponNames[i]); //TODO check if causes errors
	}
	else all = all.concat(String.valueOf(weaponNames[i].length())).concat(weaponNames[i]); //TODO check if causes errors
	}
}
//TODO add stage
//private int loadSettings() {
//	 char[] reader = new char[0];
//	 char[] count = new char[2];
//	 char[] nameLength = new char[2];
//	 
//	 mainSettings.getChars(0, 8, reader = new char[9], 0);
//	 if(reader.equals("Character")){
//		 mainSettings.getChars(9, 10, count, 0);
//		 countCharacters = Integer.parseInt(count.toString());
//		 characterNames = new String[countCharacters];
//		 int startIndex = 11;
//		 int endIndex = 12;
//		 for(int i = 0; i < countCharacters; i++){
//			 mainSettings.getChars(startIndex, endIndex, nameLength, 0);
//			 startIndex = endIndex + 1;
//			 endIndex = startIndex + Integer.parseInt(nameLength.toString());
//			 mainSettings.getChars(startIndex, endIndex, reader = new char[Integer.parseInt(nameLength.toString())], 0); // TODO clearly this will crash!
//			 characterNames[i].equals(reader);
//			 startIndex = endIndex + 1;
//			 endIndex = startIndex + 1;
//		 }
//		 mainSettings.getChars(startIndex, endIndex, reader = new char[2], 0);
//		 if(reader.equals("Op")){
//			 startIndex = endIndex + 1;
//			 endIndex = startIndex + 1;
//			 mainSettings.getChars(startIndex, endIndex, count, 0);
//			 countOpponents = Integer.parseInt(count.toString());
//			 opponentNames = new String[countOpponents];
//			 for(int i = 0; i < countOpponents; i++){
//				 mainSettings.getChars(startIndex, endIndex, nameLength, 0);
//				 startIndex = endIndex + 1;
//				 endIndex = startIndex + Integer.parseInt(nameLength.toString());
//				 opponentNames[i].equals(reader);
//				 startIndex = endIndex + 1;
//				 endIndex = startIndex + 1;
//			 }
//		 }
//		 else{
//			 System.out.print("Corrupt settings file: e data not found"); //TODO attempt writing a new settings file
//			 return -1;
//		 }
//		 mainSettings.getChars(startIndex, endIndex, reader = new char[2], 0);
//		 if(reader.equals("We")){
//			 startIndex = endIndex + 1;
//			 endIndex = startIndex + 1;
//			 mainSettings.getChars(startIndex, endIndex, count, 0);
//			 countWeapons = Integer.parseInt(count.toString());
//			 weaponNames = new String[countWeapons];
//			 for(int i = 0; i < countWeapons; i++){
//				 mainSettings.getChars(startIndex, endIndex, nameLength, 0);
//				 startIndex = endIndex + 1;
//				 endIndex = startIndex + Integer.parseInt(nameLength.toString());
//				 weaponNames[i].equals(reader);
//				 startIndex = endIndex + 1;
//				 endIndex = startIndex + 1;
//			 }
//		}
//		 else{
//			 System.out.print("Corrupt settings file: w data not found"); //TODO attempt writing a new settings file
//			 return -1;
//		 }
//		 return 1;
//	 }
//	 else{
//		 System.out.print("Corrupt settings file: w data not found"); //TODO attempt writing a new settings file
//		 return -1;
//	 }
//	        
//	}

public void writeDefaultPreferences(){
	prefs.putString("Language", defaultLang);
	prefs.putString("Number of Characters", defaultCharCount);
	prefs.putString("Number of Character Sounds", defaultCharSoundsCount);
	prefs.putString("Number of Enemies", defaultOppoCount);
	prefs.putString("Number of Enemy Sounds", defaultOppoSoundsCount);
	prefs.putString("Number of Weapons", defaultWeapCount);
	prefs.putString("Number of Weapon Sounds", defaultWeapSoundsCount);
	prefs.putString("Number of Stages",defaultStageCount);
	prefs.putString("Number of Modes",defaultModeCount);
	prefs.putString("Character Names",defaultCharNames);
	prefs.putString("Arabic Character Names",defaultCharNamesAr);
	prefs.putString("Japanese Character Names",defaultCharNamesJp);
	prefs.putString("Opponent Names",defaultOppoNames);
	prefs.putString("Weapon Names",defaultWeapNames);
	prefs.putString("Arabic Weapon Names",defaultWeapNamesAr);
	prefs.putString("Japanese Weapon Names",defaultWeapNamesJp);
	prefs.putString("Stage Names", defaultStageNames);
	prefs.putString("Mode Names", defaultModeNames);
	prefs.putString("Japanese Mode Names", defaultModeNamesJp);
	prefs.putString("Mode Status", defaultModeStatus);
	prefs.putString("Character File Names",defaultCFNames);
	prefs.putString("Character Sound File Names",defaultCSFNames);
	prefs.putString("Opponent File Names",defaultOFNames);
	prefs.putString("Opponent Sound File Names",defaultOSFNames);
	prefs.putString("Weapon File Names",defaultWFNames);
	prefs.putString("Weapon Sound File Names",defaultWSFNames);
	prefs.putString("Character BG File Names",defaultCFFNames);
	prefs.putString("Opponent BG File Names",defaultOFFNames);
	prefs.putString("Stage File Names",defaultSFNames);
	prefs.putBoolean("soundOn", true);
	prefs.putInteger("Top Score", defaultTopScore);
	prefs.flush();
}

public void writePreferences(String propertyName, String value){
	prefs.putString(propertyName, value);
	prefs.flush();
}

public void getPreferences(){
	countCharacters = Integer.parseInt(prefs.getString("Number of Characters", defaultCharCount));
	countCharacterSounds = Integer.parseInt(prefs.getString("Number of Character Sounds", defaultCharSoundsCount));
	countOpponents = Integer.parseInt(prefs.getString("Number of Enemies", defaultOppoCount));
	countOpponentSounds = Integer.parseInt(prefs.getString("Number of Enemy Sounds", defaultOppoSoundsCount));
	countWeapons = Integer.parseInt(prefs.getString("Number of Weapons", defaultWeapCount));
	countWeaponSounds = Integer.parseInt(prefs.getString("Number of Weapon Sounds", defaultWeapSoundsCount));
	countStages = Integer.parseInt(prefs.getString("Number of Stages", defaultStageCount));
	countModes = Integer.parseInt(prefs.getString("Number of Modes", defaultModeCount));
	highestScore = prefs.getInteger("Top Score", defaultTopScore);
	characterNames = new String[countCharacters];
	opponentNames = new String[countOpponents];
	weaponNames = new String[countWeapons];
	stageNames = new String[countStages];
	modeNames = new String[countModes];
	modeStatus = new String[countModes];
	characterFileNames = new String[countCharacters];
	characterSoundFileNames = new String[countCharacters][countCharacterSounds];
	characterBGNames = new String[countCharacters];
	opponentFileNames = new String[countOpponents];
	opponentSoundFileNames = new String[countOpponents][countOpponentSounds];
	opponentBGNames = new String[countOpponents];
	weaponFileNames = new String[countWeapons];
	weaponSoundFileNames = new String[countWeapons][countWeaponSounds];
	stageFileNames = new String[countStages];
	stageMusicFileNames = new String[countStages];
	//TODO read enabled/locked status for each
	//TODO add language support for all names
		
	int length;
	String all;
	language = prefs.getString("Language", defaultLang);
	if(language.equals("Jp"))
		all = prefs.getString("Japanese Character Names", defaultCharNamesJp);
	else if(language.equals("Ar"))
		all = prefs.getString("Arabic Character Names", defaultCharNamesAr);
	else 
		all = prefs.getString("Character Names", defaultCharNames);
	for (int i = 0; i < countCharacters; i++){
		length = Integer.parseInt(all.substring(0, 2));
		characterNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	
	all = prefs.getString("Opponent Names", defaultOppoNames);
	for (int i = 0; i < countOpponents; i++){
		length = Integer.parseInt(all.substring(0, 2));
		opponentNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	
	if(language.equals("Jp"))
		all = prefs.getString("Japanese Weapon Names", defaultWeapNamesJp);
	else if(language.equals("Ar"))
		all = prefs.getString("Arabic Weapon Names", defaultWeapNamesAr);
	else 
		all = prefs.getString("Weapon Names", defaultWeapNames);
	for (int i = 0; i < countWeapons; i++){
		length = Integer.parseInt(all.substring(0, 2));
		weaponNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	
	all = prefs.getString("Stage Names", defaultStageNames);
	for (int i = 0; i < countStages; i++){
		length = Integer.parseInt(all.substring(0, 2));
		stageNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	
	if(language.equals("Jp"))
		all = prefs.getString("Japanese Mode Names", defaultModeNamesJp);
//	else if(language.equals("Ar"))
//		all = prefs.getString("Arabic Weapon Names", defaultWeapNamesAr);
	else 
		all = prefs.getString("Mode Names", defaultModeNames);
	for (int i = 0; i < countModes; i++){
		length = Integer.parseInt(all.substring(0, 2));
		modeNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	
	all = prefs.getString("Mode Status", defaultModeStatus);
	for (int i = 0; i < countModes; i++){
		modeStatus[i] = all.substring(0, 1);
		all = all.substring(1);
	}
	all = prefs.getString("Character File Names", defaultCFNames);
	for (int i = 0; i < countCharacters; i++){
		length = Integer.parseInt(all.substring(0, 2));
		characterFileNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	all = prefs.getString("Character Sound File Names", defaultCSFNames);
	for (int i = 0; i < countCharacters; i++){
		for(int j=0; j < countCharacterSounds; j++){
		length = Integer.parseInt(all.substring(0, 2));
		characterSoundFileNames[i][j] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
		}
	}
	all = prefs.getString("Character BG File Names", defaultCFFNames);
	for (int i = 0; i < countCharacters; i++){
		length = Integer.parseInt(all.substring(0, 2));
		characterBGNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	all = prefs.getString("Opponent File Names", defaultOFNames);
	for (int i = 0; i < countOpponents; i++){
		length = Integer.parseInt(all.substring(0, 2));
		opponentFileNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	
	all = prefs.getString("Opponent Sound File Names", defaultOSFNames);
	for (int i = 0; i < countOpponents; i++){
		for(int j=0; j < countOpponentSounds; j++){
		length = Integer.parseInt(all.substring(0, 2));
		opponentSoundFileNames[i][j] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
		}
	}
	all = prefs.getString("Opponent BG File Names", defaultOFFNames);
	for (int i = 0; i < countOpponents; i++){
		length = Integer.parseInt(all.substring(0, 2));
		opponentBGNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	
	all = prefs.getString("Weapon File Names", defaultWFNames);
	for (int i = 0; i < countWeapons; i++){
		length = Integer.parseInt(all.substring(0, 2));
		weaponFileNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	all = prefs.getString("Weapon Sound File Names", defaultWSFNames);
	for (int i = 0; i < countWeapons; i++){
		for(int j = 0; j < countWeaponSounds; j++){
		length = Integer.parseInt(all.substring(0, 2));
		weaponSoundFileNames[i][j] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
		}
	}
	all = prefs.getString("Stage File Names", defaultSFNames);
	for (int i = 0; i < countStages; i++){
		length = Integer.parseInt(all.substring(0, 2));
		stageFileNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
	all = prefs.getString("Stage Music File Names", defaultSMFNames);
	for (int i = 0; i < countStages; i++){
		length = Integer.parseInt(all.substring(0, 2));
		stageMusicFileNames[i] = all.substring(2, 2 + length);
		all = all.substring(2 + length);
	}
}
public int getCharCount(){
	return countCharacters;
}

public int getOppCount(){
	return countOpponents;
}

public int getWeapCount(){
	return countWeapons;
}
public int getStageCount(){
	return countStages;
}

public int getModeCount() {
	return countModes;
}


public void setModeCount(int c) {
	countModes = c;
}

public double getHighestScore() {
	return highestScore;
}
public void setHighestScore(double highestScore) {
	this.highestScore = highestScore;
}
public double getSingleMatches() {
	return singleMatches;
}
public void setSingleMatches(double singleMatches) {
	this.singleMatches = singleMatches;
}
public Time getTimePlayed() {
	return timePlayed;
}
public void setTimePlayed(Time timePlayed) {
	this.timePlayed = timePlayed;
}
public double getItemsCollected() {
	return itemsCollected;
}
public void setItemsCollected(double itemsCollected) {
	this.itemsCollected = itemsCollected;
}
public int getCharactersUsed() {
	return charactersUsed;
}
public void setCharactersUsed(int charactersUsed) {
	this.charactersUsed = charactersUsed;
}
public int getWeaponsUsed() {
	return weaponsUsed;
}
public void setWeaponsUsed(int weaponsUsed) {
	this.weaponsUsed = weaponsUsed;
}
public double getItemsFired() {
	return itemsFired;
}
public void setItemsFired(double itemsFired) {
	this.itemsFired = itemsFired;
}
public Time getQuickestWin() {
	return quickestWin;
}
public void setQuickestWin(Time quickestWin) {
	this.quickestWin = quickestWin;
}
public int getHighestDifficultyFinish() {
	return highestDifficultyFinish;
}
public void setHighestDifficultyFinish(int highestDifficultyFinish) {
	this.highestDifficultyFinish = highestDifficultyFinish;
}
public int getPerfectMatches() {
	return perfectMatches;
}
public void setPerfectMatches(int perfectMatches) {
	this.perfectMatches = perfectMatches;
}


public String getLanguage() {
	return language;
}


public void setLanguage(String language) {
	this.language = language;
	writePreferences("Language", language);
}




//TODO functions to return each property






//public void writeSettings(String newSettings) throws IOException{
//	PrintWriter out = new PrintWriter(new FileWriter(mainSettingsFile));
//    out.println(newSettings);
//    out.close();
//}
//public void writeDefaultSettings() throws IOException{
//	File file = new File(mainSettingsFile);
//	file.getParentFile().mkdirs();
//	PrintWriter out = new PrintWriter(new FileWriter(mainSettingsFile));
//    out.println("Character0205FARAH05KARIMOp0107BADROBOWe0105COMET");
//    out.close();
//    mainSettings.equals("Character0205FARAH05KARIMOp0107BADROBOWe0105COMET");
//}



}

