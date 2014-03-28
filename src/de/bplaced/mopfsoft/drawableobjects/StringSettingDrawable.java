package de.bplaced.mopfsoft.drawableobjects;


import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public class StringSettingDrawable extends DrawableSetting{

	public StringSettingDrawable(int x, int y, int width, int height,
			Image image, int offsetX, int offsetY, Color color, Sound sound, int stateId, int filterR, int filterG, int filterB, Entry<String,String> entry) {
		super(x, y, width, height, image, offsetX, offsetY, color, sound, stateId, filterR, filterG, filterB, entry);
	}

	@Override
	public void keyPressed(int key, char keyAsChar) {
		String oldString = displayedText.substring(entry.getKey().length()+2);
		String newString = "";
		if (active && key != 28) {
			if (oldString.equals("<>")) {
				newString = keyAsChar+"";
			} else
			if (key == 14 && oldString.length()>0) {
				newString = oldString.substring(0, oldString.length()-1);
			} else {
				newString = oldString+keyAsChar;
			}
			
		super.getSetting().setValue(newString);
		super.displayedText = entry.getKey()+": "+entry.getValue();
		}
	}



}
