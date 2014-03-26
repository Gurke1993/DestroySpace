package de.bplaced.mopfsoft.Handler;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.DestroySpace;

public class GameHandler extends StateBasedGame {
	
	private static GameHandler instance = null;



	public static GameHandler getInstance() {
		return instance;
	}


	private static void setInstance(GameHandler instance) {
		GameHandler.instance = instance;
	}


	private GameHandler() {
		super(DestroySpace.GAME_NAME);
	}


	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		//Adding all GameStates
		for (GameState gameState: DestroySpace.GAME_STATE_ARRAY) {
			addState(gameState);
		}
	}


	public static void init() {
		if (instance == null)
		setInstance(new GameHandler());
	}
	


}
