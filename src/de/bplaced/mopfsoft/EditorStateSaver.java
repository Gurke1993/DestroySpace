package de.bplaced.mopfsoft;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

import de.bplaced.mopfsoft.drawableobjects.DrawableMap;

public class EditorStateSaver {
	public TextField mapName,mapDescription;
	private Color color;
	int x,y;
	DrawableMap drawableMap;
	private Image bgImage;
	
	public EditorStateSaver( GUIContext c,Font font , Color color, int x, int y, int height, int width, DrawableMap drawableMap) throws SlickException
	{
		this.color=color;
		this.mapName = new TextField(c , font, x, y, width, 32);
		this.mapDescription = new TextField (c, font,x, y+64, width, 32);
		this.x=x;
		this.y=y;
		this.drawableMap=drawableMap;
		bgImage = new Image("resources/images/editor/openerBg.gif");
		mapName.setText("");
		mapDescription.setText("");
		mapName.setTextColor(color);
		mapDescription.setTextColor(color);
			
	}
	public void draw(GUIContext c, Graphics g) {
		//g.setColor(color);
		g.drawImage(bgImage, x-28, y-64);
		mapName.render(c, g);
		mapDescription.render(c, g);
		g.drawString("Mapname:", x, y-28);
		g.drawString("Height", x, y+36);
		g.drawString("Width", x, y+100);
		g.drawString("map description", x, y+164);
		
	}
	
		
	}
