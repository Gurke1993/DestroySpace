package de.bplaced.mopfsoft.gameStates;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.handler.MultiplayerGameManager;
import de.bplaced.mopfsoft.handler.PreGameManager;

public class LoadingState extends BasicGameState{
	public static final int id = 7;
	private Image loadingScreen;
	private double loaded;
	private String loadingMessage ="";

	@Override
	public void init(GameContainer arg0, StateBasedGame stateBasedGame)
			throws SlickException {
		
		loadingScreen = new Image("resources/images/multiplayerGame/LoadingScreen.jpg");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		//LoadingScreen
			graphics.drawImage(loadingScreen, 0, 0);
			graphics.setColor(Color.green);
			for (int i = 0; i<59; i++) {
				graphics.drawLine(274, 313+i, 274+(int)(503*loaded), 313+i);
			}
			graphics.drawRect(274, 313, (int)(503*loaded), 59);
			graphics.drawString(loadingMessage, 274, 380);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (PreGameManager.getInstance().isMapLoaded() && loaded < 1) {
			
			//Initialise Map
			loadingMessage = "Initialising map";
			MultiplayerGameManager.getInstance().setMap();
			loaded = 0.9;
			
			loadingMessage = "Waiting for other players...";
			loaded = 1;
			
			PreGameManager.getInstance().setClientIsReadyToStart(true);
			
		}
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void setLoaded(double d) {
		this.loaded = d;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		
		//Enable Load mode
		loaded = 0;
		
		//Download map
		loadingMessage = "Downloading map...";
		PreGameManager.getInstance().downloadMap();
		loaded = 0.1;
	}

	public double getLoaded() {
		return loaded;
	}

}
