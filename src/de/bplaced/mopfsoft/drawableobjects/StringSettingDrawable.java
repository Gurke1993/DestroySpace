package de.bplaced.mopfsoft.drawableobjects;


import java.util.Map.Entry;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;

public class StringSettingDrawable extends DrawableSetting{

	public StringSettingDrawable(int x, int y, int width, int height,
			Image image, Sound sound, Input input, Entry<String,String> entry) {
		super(x, y, width, height, image, sound, input, entry);
	}

	@Override
	public void keyPressed(int key, char keyAsChar) {
		if (active && key != 28) {
			if (displayedValue.equals("<>")) {
				super.displayedValue = "";
			}
			if (key == 14 && displayedValue.length()>0) {
				super.displayedValue = displayedValue.substring(0, displayedValue.length()-1);
				super.getSetting().setValue(displayedValue);
				return;
			}
		super.displayedValue = displayedValue+keyAsChar;
		super.getSetting().setValue(displayedValue);
		}
	}



}
