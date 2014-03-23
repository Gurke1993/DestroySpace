package de.bplaced.mopfsoft.drawableobjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.Sound;

public abstract class DrawableObjectClickable extends DrawableObject implements InputListener{
	
	private final Sound sound;
	
	public DrawableObjectClickable(int x, int y, int width, int height, Image image, Sound sound) {
		super(x, y, width, height, image);
		this.sound = sound;
	}

	public void mousePressed(int button, int x, int y) {
		if (contains(x,y)) {
			onClick(button,x,y);
			if (sound != null)
				sound.play();
		}
	}
	
	private boolean contains(int x, int y) {
		return (x>=this.x && x<this.x+this.width && y>=this.y && y<this.y+this.height);
	}

	public  abstract void onClick(int button, int x, int y);
	
	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}
	
	@Override
	public void setInput(Input arg0) {
	}
	
	@Override
	public void keyReleased(int arg0, char arg1) {
	}

	@Override
	public void controllerButtonPressed(int arg0, int arg1) {
	}

	@Override
	public void controllerButtonReleased(int arg0, int arg1) {
	}

	@Override
	public void controllerDownPressed(int arg0) {
	}

	@Override
	public void controllerDownReleased(int arg0) {
	}

	@Override
	public void controllerLeftPressed(int arg0) {
	}

	@Override
	public void controllerLeftReleased(int arg0) {
	}

	@Override
	public void controllerRightPressed(int arg0) {
	}

	@Override
	public void controllerRightReleased(int arg0) {
	}

	@Override
	public void controllerUpPressed(int arg0) {
	}

	@Override
	public void controllerUpReleased(int arg0) {
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
	}

	@Override
	public void mouseWheelMoved(int arg0) {
	}
}
