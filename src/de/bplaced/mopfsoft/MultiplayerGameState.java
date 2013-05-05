package de.bplaced.mopfsoft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MultiplayerGameState extends BasicGameState{
	public static final int id = 6;
	private MainScreen mainScreen;
	@SuppressWarnings("unused")
	private int currentKey;

	@Override
	public void init(GameContainer arg0, StateBasedGame stateBasedGame)
			throws SlickException {
		mainScreen = (MainScreen)stateBasedGame;
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		//Draw gamefield, entities
		mainScreen.getDestroySpace().getMultiplayerGameManager().getMap().drawAll(graphics);

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int timePassed)
			throws SlickException {
		
		
		//MultiplayerGameLoop
		mainScreen.getDestroySpace().getMultiplayerGameManager().doGameLoop(container, sbg, timePassed);
	}

	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public void keyPressed(int key, char c) {

		
		//TODO movement vs items...
		this.currentKey = key;
		
	}
	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		System.out.println("Going to Multiplayer screen...");
	}


}
