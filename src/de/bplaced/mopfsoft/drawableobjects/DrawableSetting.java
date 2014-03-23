package de.bplaced.mopfsoft.drawableobjects;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.GUIContext;

public abstract class DrawableSetting extends DrawableObjectClickable{

	private Setting setting;
	protected String currentValue;
	protected boolean waitingForInput = false;
	
	public DrawableSetting(int x, int y, int width, int height, Image image,
			Sound sound, Setting setting) {
		super(x, y, width, height, image, sound);
		this.setting = setting;
		this.currentValue = setting.getValue();
	}
	
	public void draw(GUIContext c, Graphics g) {
		super.draw(c, g);
		g.drawString(setting.getName()+": "+currentValue, x, y);
	}

	@Override
	public void onClick(int button, int x, int y) {
		System.out.println("Clicked on "+setting.getName());
		if (!waitingForInput) {
			this.currentValue = "<>";
			waitingForInput  = true;
		}
	}



	@Override
	public boolean isAcceptingInput() {
		return this.waitingForInput;
	}
	
	public Setting getSetting() {
		return setting;
	}

}
