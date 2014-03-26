package de.bplaced.mopfsoft.editor;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import de.bplaced.mopfsoft.drawableobjects.DrawableObjectClickable;

public class Button extends DrawableObjectClickable {

	boolean active;//Paintfunktion
	public Button(int x, int y, int width, int height, Image image, Sound sound, int stateId) {
		super(x, y, width, height, image, sound, stateId);
		active =false;
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		System.out.println("works2");
		
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
}
