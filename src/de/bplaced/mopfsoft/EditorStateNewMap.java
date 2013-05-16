package de.bplaced.mopfsoft;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

public class EditorStateNewMap {

	public TextField mapName,mapDescription,mapHeight,mapWidth;
	private Color color;
	int x,y;
	DrawableMap drawableMap;
	private Image bgImage;
	public EditorStateNewMap( GUIContext c,Font font , Color color, int x, int y, int height, int width, DrawableMap drawableMap) throws SlickException
	{
		this.color=color;
		this.mapName = new TextField(c , font, x, y, width, 32);
		this.mapHeight = new TextField (c, font,x, y+64, width, 32);
		this.mapWidth = new TextField (c, font,x, y+128, width, 32);
		this.mapDescription = new TextField (c, font,x, y+192, width, 32);
		this.x=x;
		this.y=y;
		this.drawableMap=drawableMap;
		bgImage = new Image("resources/images/editor/openerBg.gif");
		mapName.setText("");
		mapHeight.setText("");
		mapWidth.setText("");
		mapDescription.setText("");
		mapName.setTextColor(color);
		mapHeight.setTextColor(color);
		mapWidth.setTextColor(color);
		mapDescription.setTextColor(color);
			
	}
	public void draw(GUIContext c, Graphics g) {
		//g.setColor(color);
		g.drawImage(bgImage, x-28, y-64);
		mapName.render(c, g);
		mapHeight.render(c, g);
		mapWidth.render(c, g);
		mapDescription.render(c, g);
		g.drawString("Mapname:", x, y-28);
		g.drawString("Height", x, y+36);
		g.drawString("Width", x, y+100);
		g.drawString("map description", x, y+164);
		
	}
	public void saveMap()
	{
		drawableMap.saveToFile("resources//images//editor//test.map");
		drawableMap.saveToFile("resources\\images\\editor\\test.map");	
	}
		
	}
