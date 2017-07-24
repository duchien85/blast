package com.muyagdx.blast;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ErrorScreen implements Screen{
	final Blast game;
	OrthographicCamera camera;

    public ErrorScreen(final Blast gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
       // game.backgroundImage = new Texture(Gdx.files.internal("BadRoboBigColor.png"));//TODO animate image
	//    game.dropButton = new Texture(Gdx.files.internal("dropbutton.png"));
    }
    @Override
    public void render(float delta){
    	Gdx.gl.glClearColor(0, 0, 0.5f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
	    game.font.setScale(2, 2);
	    game.font.setColor(0.9f, 0.7f, 0.7f, 1);
	    
	    camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
	    game.batch.begin();
	    game.batch.setColor(0.9f,0.5f,0.3f,1);
	    //game.batch.draw(game.backgroundImage, 0, 0);
//	    game.batch.draw(game.dropButton,510,310);
//	    game.batch.draw(game.dropButton,510,160);
//	    game.batch.draw(game.dropButton,200,310);
//	    game.batch.draw(game.dropButton,200,160);
//		game.font.draw(game.batch, "Couldn't read or write settings", 350, 200);
		game.batch.setColor(1,1,1,1);
	    game.batch.end();   
	    if (Gdx.input.justTouched()) {
        // TODO create two rects for yes and no and Message Try again?
	    	game.setScreen(new MainMenuScreen(game));
	    	//game.setScreen(new GameScreen(game));
	    	game.setState(Blast.READY);
            dispose();
        }
	    if (Gdx.input.isButtonPressed(Input.Keys.ESCAPE));
	    	dispose();
	    	
    }
    public void dispose(){
    	//game.backgroundImage.dispose();
    	//game.dropButton.dispose();
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

