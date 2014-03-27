package de.bplaced.mopfsoft.drawableobjects;


import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public class KeySettingDrawable extends DrawableSetting{

	public KeySettingDrawable(int x, int y, int width, int height, Image image, int offsetX, int offsetY, Color color,
			Sound sound, int stateId, Entry<String,String> entry) {
		super(x, y, width, height, image, offsetX, offsetY, color,  sound, stateId, entry);
	}



	@Override
	public void keyPressed(int key, char keyAsChar) {
		if (active) {
		super.getSetting().setValue(""+key);
		super.displayedText = entry.getKey()+": "+entry.getValue();
		this.active = false;
		}
	}

}
