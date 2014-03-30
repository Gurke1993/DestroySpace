package de.bplaced.mopfsoft.drawableobjects;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.entitys.Entity;
import de.bplaced.mopfsoft.map.Map;
import de.bplaced.mopfsoft.material.Air;
import de.bplaced.mopfsoft.material.Material;

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
	
	public DrawableMap(File mapFile, String previewImageFile) throws FileNotFoundException {
		super(mapFile);
		setupImages(previewImageFile, Color.black);
	}
	
	public DrawableMap(File mapFile, String previewImageFile, Color backgroundColor) throws FileNotFoundException {
		super(mapFile);
		setupImages(previewImageFile, backgroundColor);
	}
	
	private void setupImages(String previewImageFile, Color backgroundColor) {
		//Update Airblock to match background
		Air.setColor(backgroundColor);
		
		
		// Set path to previewImage
				Image previewImageTemp = null;
				try {
					previewImageTemp = new Image(previewImageFile);
				} catch (SlickException e1) {
				} catch (NullPointerException e2) {
					Log.info("Could not initialise preview. Leaving Image empty....");
				}
				previewImage = previewImageTemp;
				
				try {
					Log.debug("Creating gamefieldimage with "+eMax.getWidth()+" "+eMax.getHeight());
					
					// setup image
					gamefieldAsImage = Image.createOffscreenImage((int)eMax.getWidth(),
							(int)eMax.getHeight());
					gamefieldAsImageG = gamefieldAsImage.getGraphics();

					// set backgroundcolor
					gamefieldAsImageG.setBackground(backgroundColor);
					gamefieldAsImageG.clear();

					
					//Draw gamefield
					for (Entry <Shape,Material> entry : environment.entrySet()) {
						gamefieldAsImageG.setColor(entry.getValue().getColor());
						gamefieldAsImageG.fill(entry.getKey());
					}
					
					//Clear Graphics
					gamefieldAsImageG.flush();

				} catch (SlickException e) {
					Log.error("Could not initialise Image!",e);
				}
	}
	
	/** Supports empty String for previewImageFile but can cause NullPointers
	 * @param mapString
	 * @param previewImageFile
	 */
	public DrawableMap(String mapString, String previewImageFile){
		super(mapString);
		setupImages(previewImageFile, Color.black);
		
	}
	
	public Image getGamefieldAsImage() {
		return gamefieldAsImage;
	}
	
	public void updateBlocks(Shape shape, int mid) {
		updateBlocks(shape, Material.getNewMaterial(mid));
	}
	
	@Override
	public void updateBlocks(Shape shape, Material material) {
		
		//Update image
		gamefieldAsImageG.setColor(material.getColor());
		gamefieldAsImageG.fill(shape);
		gamefieldAsImageG.flush();
		
		//Update array
		super.updateBlocks(shape, material);
	}

	public void drawAll(Graphics graphics, int x, int y) {
		
		//Draw gamefield
		graphics.drawImage(getGamefieldAsImage(), x, y);
		
		//Draw Entities
		for (Entity entity: entitys) {
			graphics.drawImage(entity.getImage(), x+entity.getX(), y+entity.getY());
			//graphics.drawImage(entity.getImage(), x+entity.getX(), y+entity.getY(), x+entity.getX()+entity.getWidth(), y+entity.getY()+entity.getHeight(), 0, 0, entity.getWidth(), entity.getHeight());
		}
	}

}
