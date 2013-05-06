package de.bplaced.mopfsoft;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.bplaced.mopfsoft.blocks.Block;
import de.bplaced.mopfsoft.entitys.Entity;
import de.bplaced.mopfsoft.map.Map;

public class DrawableMap extends Map{
	
	private Image gamefieldAsImage;
	private Image previewImage;
	private Graphics gamefieldAsImageG;

	
	
	/** Returns the preview image of the map
	 * @return
	 */
	public Image getPreviewImage() {
		return this.previewImage;
	}
	
	public DrawableMap(String mapString, String previewImageFile){
		super(mapString);
		
		// Set path to previewImage
		Image previewImageTemp = null;
		try {
			previewImageTemp = new Image(previewImageFile);
		} catch (SlickException e1) {
		}
		previewImage = previewImageTemp;
		
		try {
			System.out.println("Creating gamefieldimage with "+gamefield.length+" "+gamefield[0].length);
			// setup image
			gamefieldAsImage = Image.createOffscreenImage(gamefield.length,
					gamefield[0].length);
			gamefieldAsImageG = gamefieldAsImage.getGraphics();

			// set backgroundcolor
			gamefieldAsImageG.setBackground(Color.black);
			gamefieldAsImageG.clear();

			
			//Draw gamefield
			for (int i = 0; i < gamefield.length; i++) {
				for (int j = 0; j < gamefield[0].length; j++) {
					gamefieldAsImageG.setColor(gamefield[i][j].getColor());
					gamefieldAsImageG.fillRect(i, j, 1, 1);
				}
			}
			
			//Clear Graphics
			gamefieldAsImageG.flush();

		} catch (SlickException e) {
			System.out.println("Could not initialise Image!");
			e.printStackTrace();
		}
	}
	
	public Image getGamefieldAsImage() {
		return gamefieldAsImage;
	}
	
	@Override
	public void updateBlock(int x, int y, int newId) {
		updateBlock(x,y,Block.getNewBlock(x, y, newId));
	}
	
	@Override
	public void updateBlock(int x, int y, Block newBlock) {
		
		//Update image
		changePixel(x,y,newBlock.getColor());
		
		//Update array
		super.updateBlock(x,y,newBlock);
	}
	
	/** Changes a pixel of the image representation of the gamefield
	 * @param x
	 * @param y
	 * @param color
	 */
	private void changePixel(int x, int y, Color color) {
		gamefieldAsImageG.setColor(color);
		gamefieldAsImageG.fillRect(x, y, 1, 1);
		gamefieldAsImageG.flush();
	}

	public void drawAll(Graphics graphics, int x, int y) {
		
		//Draw gamefield
		graphics.drawImage(getGamefieldAsImage(), x, y);
		
		//Draw Entities
		for (Entity entity: entitys) {
			graphics.drawImage(entity.getImage(), x+entity.getX(), y+entity.getY());
		}
	}

}
