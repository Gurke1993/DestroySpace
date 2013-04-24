package de.bplaced.mopfsoft;

import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.EmptyImageData;

public class GameField {

	
	private int[] gameArray;
	
	
	private Image arrayAsImage;

	public GameField(int [] gameArray, int columnLength) {
		this.gameArray = Arrays.copyOf(gameArray, gameArray.length);
		
		//Create new Image
		arrayAsImage = new Image(new EmptyImageData(columnLength, gameArray.length/columnLength));
		
		//Initialise Image
		Graphics g;
		try {
			g = arrayAsImage.getGraphics();

			for (int i = 0; i < gameArray.length; i++) {
			g.setColor(new Color(gameArray[i]));
			g.fillRect(i%columnLength ,i/columnLength,1,1);
			}
			g.flush();
			
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
	
	public void changePixel(int x, int y, int color) throws SlickException {
		Graphics g;
		g = arrayAsImage.getGraphics();
		g.setColor(new Color(color));
		g.fillRect(x ,y,1,1);
		g.flush();
	}

	public void addPlayer(String[] playerArray) {
		System.out.println("Adding "+playerArray);
		//TODO
	}
}
