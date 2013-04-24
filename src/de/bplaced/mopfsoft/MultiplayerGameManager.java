package de.bplaced.mopfsoft;


import org.newdawn.slick.SlickException;


public class MultiplayerGameManager {

	private GameField gameField;

	public MultiplayerGameManager () {
		
	}
	
	public void setGameField(int [] gameField, int columnLength) {
		
		this.gameField = new GameField(gameField,columnLength);
	}
	
	public GameField getGameField() {
		return this.gameField;
	}
	
	@Deprecated
	public void changeField(int position, int newType) {
		//gameField.changePixel(position%, y, newType)
	}
	
	public void changeField(int x, int y, int newType) throws SlickException {
		gameField.changePixel(x, y, newType);
	}
}
