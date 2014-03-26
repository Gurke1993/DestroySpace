package de.bplaced.mopfsoft.editor;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;

import de.bplaced.mopfsoft.drawableobjects.DrawableObjectClickable;

public class Button extends DrawableObjectClickable {

	public Button(int x, int y, int width, int height, Image image, Sound sound, Input input) {
		super(x, y, width, height, image, sound, input);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		System.out.println("works2");
		
	}

	@Override
	public void onClick(int button, int x, int y) {
		System.out.println("works");
	}
	
	
}
