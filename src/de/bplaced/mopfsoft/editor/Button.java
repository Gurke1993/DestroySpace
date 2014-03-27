package de.bplaced.mopfsoft.editor;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.GUIContext;

import de.bplaced.mopfsoft.drawableobjects.DrawableObjectClickable;

public class Button extends DrawableObjectClickable {

	boolean active;//Paintfunktion
	Image hover;//Hover Image
	
	public Button(int x, int y, int width, int height, Image image, Sound sound, int stateId) {
		super(x, y, width, height, image, sound, stateId);
		active =false;
		
		try {
			hover = new Image("resources/images/editor/hover.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		
		
	}

	@Override
	public void onClick(int button, int x, int y) {
		active =true;
	}
	
	public void setActiveFalse ()
	{
		active =false;
	}
	
	public boolean getActive ()
	{
		return active;
	}
	public void draw(GUIContext c, Graphics g) {
		super.draw(c, g);
		if (active)
		{		
		g.drawImage(hover,x,y);
		}
	}
}
