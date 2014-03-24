package de.bplaced.mopfsoft.drawableobjects;


import java.util.Map.Entry;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public class KeySettingDrawable extends DrawableSetting{

	public KeySettingDrawable(int x, int y, int width, int height, Image image,
			Sound sound, Entry<String,String> entry) {
		super(x, y, width, height, image, sound, entry);
	}



	@Override
	public void keyPressed(int key, char keyAsChar) {
		if (active) {
		super.displayedValue = ""+key;
		super.getSetting().setValue(""+key);
		this.active = false;
		}
	}

}
