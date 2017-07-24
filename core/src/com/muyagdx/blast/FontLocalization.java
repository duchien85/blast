package com.muyagdx.blast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontLocalization {
	FreeTypeFontGenerator gen;
	BitmapFont gameFont;
	public FontLocalization(){
		gameFont = new BitmapFont(); 
		gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/honoka.ttf"));
	}
	@SuppressWarnings("deprecation")
	public BitmapFont getFontLocale(String lang){
		if(lang.equals("Jp")){
			gameFont = gen.generateFont(25, "戦いましょう？恐怖の世界によこそ!無設定終了ダスーファラカリムセイ火コメロケットマンabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", false);
		}
		else if(lang.equals("Ar")){
			gameFont = gen.generateFont(15, "نشهابصروخلغمحكيسفabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", false);
		}
		else{
			gameFont = new BitmapFont();
		}
		return gameFont;
	}
	
	void dispose(){
		gen.dispose();
	}
}
