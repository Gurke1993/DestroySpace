package de.bplaced.mopfsoft;



import de.bplaced.mopfsoft.blocks.Block;



public class MultiplayerGameManager {

	private GameField gameField;

	public MultiplayerGameManager() {

	}

	public void setGameField(Block[][] gameField) {

		this.gameField = new GameField(gameField);
	}

	public GameField getGameField() {
		return this.gameField;
	}
}
