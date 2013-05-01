package de.bplaced.mopfsoft;

import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.bplaced.mopfsoft.blocks.Block;

public class GameField {

	
	private Block[][] gameArray;
	
	private Image arrayAsImage;
	private Graphics arrayAsImageG;
	

	public GameField(Block [][] gameArray) {
		this.gameArray = Arrays.copyOf(gameArray, gameArray.length);
		
		//Setup image
		try {
			
		//create image
		arrayAsImage = Image.createOffscreenImage(gameArray.length,gameArray[0].length);
		arrayAsImageG = arrayAsImage.getGraphics();
		
		
		//set backgroundcolor
		arrayAsImageG.setBackground(Color.black);
		arrayAsImageG.clear();
		

		for (int i = 0; i < gameArray.length; i++) {
			for (int j = 0; j< gameArray[0].length; j++) {
			arrayAsImageG.setColor(gameArray[i][j].getColor());
			//arrayAsImageG.setColor(new Color(gameArray[i]));
			arrayAsImageG.fillRect(i ,j,1,1);
		}
		}
		arrayAsImageG.flush();
			
		} catch (SlickException e) {
			System.out.println("Could not initialise Image!");
			e.printStackTrace();
		}
	}
	
	public Image getGameFieldAsImage() {

		return arrayAsImage;
	}
	
	protected Block[][] getGameArray() {
		return this.gameArray;
	}
	
	private void changePixel(int x, int y, Color color){
		arrayAsImageG.setColor(color);
		arrayAsImageG.fillRect(x ,y,1,1);
		arrayAsImageG.flush();
	}
	
	public void changeBlock(int x, int y, Block block) {
		gameArray[x][y] = block;
		changePixel(x,y,block.getColor());
	}
	
	public void changeBlock(int x, int y, int id) {
		changeBlock(x,y,Block.getNewBlock(x, y, id));
	}

	public void addPlayer(String[] playerArray) {
		System.out.println("Adding "+playerArray[0]);
		//TODO
	}
}
