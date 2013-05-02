package de.bplaced.mopfsoft;



import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.blocks.Block;



public class MultiplayerGameManager {

	private GameField gameField;
	@SuppressWarnings("unused")
	private ClientThread sender;

	public MultiplayerGameManager(ClientThread sender) {
		this.sender = sender;
	}
	
	
	/**
	 * Runs the GameLoop on client side. 
	 * This should only be called by the GameState update 
	 * method as it requires an OpenGL enviroment.
	 * 
	 * @param timePassed 
	 * @param sbg the state based game
	 * @param container the game container
	 * 
	 */
	public void doGameLoop(GameContainer container, StateBasedGame sbg, int timePassed) {
		//GameLoop for Multiplayer
		
		//TODO
	}

	public void setGameField(Block[][] gameField) {

		this.gameField = new GameField(gameField);
	}

	public GameField getGameField() {
		return this.gameField;
	}
}
