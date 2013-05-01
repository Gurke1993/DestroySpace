package de.bplaced.mopfsoft;



import de.bplaced.mopfsoft.blocks.Block;



public class MultiplayerGameManager {

	private GameField gameField;

	public MultiplayerGameManager () {
		
	}
	
	public void setGameField(Block [] gameField, int columnLength) {
		
		this.gameField = new GameField(gameField,columnLength);
	}
	
	public GameField getGameField() {
		return this.gameField;
	}
}
