package de.bplaced.mopfsoft;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainScreen extends StateBasedGame {

	private DestroySpace destroySpace;
	private GameState[] gameStateArray;
	
	public DestroySpace getDestroySpace() {
		return this.destroySpace;
	}


	public MainScreen(String name, DestroySpace destroySpace, GameState[] gameStateArray) {
		super(name);
		this.destroySpace = destroySpace;
		this.gameStateArray = gameStateArray;
	}


	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		//Adding all GameStates
		for (GameState gameState: gameStateArray) {
			addState(gameState);
		}
	}
	


}
