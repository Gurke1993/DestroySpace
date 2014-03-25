package de.bplaced.mopfsoft.drawableobjects;


import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.GUIContext;

public abstract class DrawableSetting extends DrawableObjectClickable{
	
	private final int offsetX = 3;
	private final int offsetY = 0;

	private Entry <String,String> entry;
	protected String displayedValue;
	
	public DrawableSetting(int x, int y, int width, int height, Image image,
			Sound sound, Entry <String,String> entry) {
		super(x, y, width, height, image, sound);
		this.entry = entry;
		this.displayedValue = entry.getValue();
	}
	
	public void draw(GUIContext c, Graphics g) {
		super.draw(c, g);
		g.setColor(Color.green);
		g.drawString(entry.getKey()+": "+displayedValue, x+offsetX, y+offsetY);
		g.setColor(Color.white);
	}

	@Override
	public void onClick(int button, int x, int y) {
		System.out.println("Clicked on "+entry.getKey());
		this.displayedValue = "<>";
	}



	@Override
	public boolean isAcceptingInput() {
		return true;//this.waitingForInput;
	}
	
	public Entry <String,String> getSetting() {
		return entry;
	}

}
