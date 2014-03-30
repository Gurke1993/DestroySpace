package de.bplaced.mopfsoft.drawableobjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.Sound;

import de.bplaced.mopfsoft.handler.GameHandler;

public abstract class DrawableObjectClickable extends DrawableObject implements InputListener{
	
	private final Sound sound;
	private final int stateId;
	private final int filterR, filterG, filterB;
	protected boolean active = false;
	
	public DrawableObjectClickable(int x, int y, int width, int height, Image image, int offsetX, int offsetY, Color color, String displayedText, Sound sound, int stateId, int filterR, int filterG, int filterB) {
		super(x, y, width, height, image, offsetX, offsetY, color, displayedText);
		this.sound = sound;
		this.stateId = stateId;
		this.filterR = filterR;
		this.filterG = filterG;
		this.filterB = filterB;
		GameHandler.getInstance().getContainer().getInput().addListener(this);
	}
	
	public DrawableObjectClickable(int x, int y, int width, int height, Image image, int offsetX, int offsetY, Color color, String displayedText, Sound sound, int stateId) {
		this(x, y, width, height, image, offsetX, offsetY, color, displayedText, sound, stateId, 255, 255, 255);
	}
	
	public DrawableObjectClickable(int x, int y, int width, int height, Image image, Sound sound, int stateId) {
		this(x, y, width, height, image, 0, 0, null, null, sound, stateId);
	}

	public void mousePressed(int button, int x, int y) {
		if (contains(x,y) && stateId == GameHandler.getInstance().getCurrentStateID()) {
			active = true;
			onClick(button,x,y);
			if (sound != null)
				sound.play();
		} else {
			active = false;
		}
	}
	
	private boolean contains(int x, int y) {
		return (x>=this.x && x<this.x+this.width && y>=this.y && y<this.y+this.height);
	}
	
	@Override
	public boolean isAcceptingInput() {
		return stateId == GameHandler.getInstance().getCurrentStateID();
	}
	
	@Override
	public void mouseMoved(int xOld, int yOld, int xNew, int yNew) {
		if (super.image == null) return;
		if (contains(xOld,yOld) && !contains(xNew, yNew)) {
			super.image.setImageColor(255, 255, 255);
		} else
		if (!contains(xOld,yOld) && contains(xNew, yNew)) {
			super.image.setImageColor(filterR,filterG,filterB);
		}
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
	public void mouseReleased(int arg0, int arg1, int arg2) {
	}

	@Override
	public void mouseWheelMoved(int arg0) {
	}
}
