package de.bplaced.mopfsoft;


import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

public class EditorStateMapOpener {

	private Color color;
	int x,y;
	DrawableMap drawableMap;
	private Image bgImage;
	public EditorStateMapOpener( GUIContext c,Font font , Color color, int x, int y, int height, int width, DrawableMap drawableMap) throws SlickException
	{
		this.color=color;
		
		this.y=y;
		this.drawableMap=drawableMap;
		bgImage = new Image("resources/images/editor/openerBg.gif");

	}
	public void draw(GUIContext c, Graphics g) {
		g.drawImage(bgImage, x-28, y-64);
		g.drawString("Mapname:", x, y-28);
		g.drawString("Height", x, y+36);
		g.drawString("Width", x, y+100);
		g.drawString("map description", x, y+164);
		
	}	
	}

