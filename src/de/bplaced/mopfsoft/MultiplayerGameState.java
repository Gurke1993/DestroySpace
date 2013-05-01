package de.bplaced.mopfsoft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MultiplayerGameState extends BasicGameState{
	public static final int id = 6;
	private MainScreen mainScreen;

	@Override
	public void init(GameContainer arg0, StateBasedGame stateBasedGame)
			throws SlickException {
		mainScreen = (MainScreen)stateBasedGame;
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		graphics.drawImage(mainScreen.getDestroySpace().getMultiplayerGameManager().getGameField().getGameFieldAsImage(), 1, 1);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		System.out.println("Going to Multiplayer screen...");
	}


}
