package de.bplaced.mopfsoft.drawableobjects;


import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public class KeySettingDrawable extends DrawableSetting{

	public KeySettingDrawable(int x, int y, int width, int height, Image image,
			Sound sound, Setting setting) {
		super(x, y, width, height, image, sound, setting);
	}



	@Override
	public void keyPressed(int key, char keyAsChar) {
		super.currentValue = ""+keyAsChar;
		super.getSetting().setValue(""+key);
		this.waitingForInput = false;
		
	}

}
