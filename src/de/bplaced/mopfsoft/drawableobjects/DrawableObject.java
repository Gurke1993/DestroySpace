package de.bplaced.mopfsoft.drawableobjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;

public abstract class DrawableObject {
	
	protected final int x;
	protected final int y;
	protected final int width;
	protected final int height;
	protected final int offsetX = 3;
	protected final int offsetY = 0;
	private final Image image;
	protected String displayedText;
	protected Color color;

	
	public DrawableObject (int x, int y, int width, int height, Image image) {
		this(x,y,width,height, image, 0, 0, null ,null);
	}
	
	public DrawableObject(int x, int y, int width, int height, Image image, int offsetX, int offsetY, Color color, String displayedText) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.image = image;
		this.color = color;
		this.displayedText = displayedText;
	}

	public void draw(GUIContext c, Graphics g) {
		if (image != null)
		g.drawImage(image,  x, y);
		
		if (displayedText != null && color != null){
			Color tempC = g.getColor();
			g.drawString(displayedText, x+offsetX, y+offsetY);
			g.setColor(tempC);
		}
	}
	

	
}
