package de.bplaced.mopfsoft.drawableobjects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;

public abstract class DrawableObject {
	
	protected final int x;
	protected final int y;
	protected final int width;
	protected final int height;
	private final Image image;
	
	public DrawableObject (int x, int y, int width, int height, Image image) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.image = image;
	}

	public void draw(GUIContext c, Graphics g) {
		if (image != null)
		g.drawImage(image,  x, y);
	}
	

	
}
