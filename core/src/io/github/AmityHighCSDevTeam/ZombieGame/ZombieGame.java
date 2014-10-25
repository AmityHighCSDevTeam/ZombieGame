package io.github.AmityHighCSDevTeam.ZombieGame;

import io.github.AmityHighCSDevTeam.ZombieGame.client.screen.LoadingScreen;
import io.github.AmityHighCSDevTeam.ZombieGame.client.screen.MainMenu;

import com.badlogic.gdx.Game;

public class ZombieGame extends Game {
	
	public boolean isLoaded = false;
	
	@Override
	public void create () {
		setScreen(new LoadingScreen());
		
		new Thread(new Runnable() {			
			@Override
			public void run() {
				//Do Loading
				
				try {
					Thread.sleep(10000);//Loading Delay Test
					isLoaded = true;	
				} catch (InterruptedException e) {}
				
			}
		}).start();
	}
	@Override
	public void render () {
		super.render();
		if (isLoaded) {
			setScreen(new MainMenu());
		}
	}
	@Override
	public void dispose() {
		super.dispose();
	}
	@Override
	public void pause() {
		super.pause();
	}	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}	
	@Override
	public void resume() {
		super.resume();
	}
}
