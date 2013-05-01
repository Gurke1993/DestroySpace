package de.bplaced.mopfsoft;

import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameField {

	
	private int[] gameArray;
	
	
	private Image arrayAsImage;
	private Graphics arrayAsImageG;

	public GameField(int [] gameArray, int columnLength) {
		this.gameArray = Arrays.copyOf(gameArray, gameArray.length);
		
		//Setup image
		try {
			
		//create image
		arrayAsImage = Image.createOffscreenImage(columnLength,gameArray.length/columnLength);
		arrayAsImageG = arrayAsImage.getGraphics();
		
		
		//set backgroundcolor
		arrayAsImageG.setBackground(Color.black);
		arrayAsImageG.clear();
		

		for (int i = 0; i < gameArray.length; i++) {
			arrayAsImageG.setColor(new Color(gameArray[i]%256, gameArray[i]/256, gameArray[i]/65536));
			//arrayAsImageG.setColor(new Color(gameArray[i]));
			arrayAsImageG.fillRect(i%columnLength ,i/columnLength,1,1);
		}
		arrayAsImageG.flush();
		
		//TESTTESTETTSTSTST
		for (int i= 200; i< 400; i++) {
			changePixel(i, 60, Color.blue);
		}
			
		} catch (SlickException e) {
			System.out.println("Could not initialise Image!");
			e.printStackTrace();
		}
	}
	
	public Image getGameFieldAsImage() {

		return arrayAsImage;
	}
	
	protected int[] getGameArray() {
		return this.gameArray;
	}
	
	public void changePixel(int x, int y, Color color) throws SlickException {
		arrayAsImageG.setColor(color);
		arrayAsImageG.fillRect(x ,y,1,1);
		arrayAsImageG.flush();
	}

	public void addPlayer(String[] playerArray) {
		System.out.println("Adding "+playerArray[0]);
		//TODO
	}
}
