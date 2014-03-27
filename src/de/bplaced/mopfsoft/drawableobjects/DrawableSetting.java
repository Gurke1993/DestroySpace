package de.bplaced.mopfsoft.drawableobjects;


import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public abstract class DrawableSetting extends DrawableObjectClickable{
	


	protected Entry <String,String> entry;
	
	public DrawableSetting(int x, int y, int width, int height, Image image, int offsetX, int offsetY, Color color,
			Sound sound, int stateId, Entry <String,String> entry) {
		super(x, y, width, height, image, offsetX, offsetY, color, entry.getKey()+": "+entry.getValue(), sound, stateId);
		this.entry = entry;
	}
	

	@Override
	public void onClick(int button, int x, int y) {
		this.displayedText = entry.getKey()+": <>";
	}

	
	public Entry <String,String> getSetting() {
		return entry;
	}

}
