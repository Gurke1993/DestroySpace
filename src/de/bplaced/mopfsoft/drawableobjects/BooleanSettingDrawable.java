package de.bplaced.mopfsoft.drawableobjects;

import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public class BooleanSettingDrawable  extends DrawableSetting{

	public BooleanSettingDrawable(int x, int y, int width, int height,
			Image image, int offsetX, int offsetY, Color color, Sound sound, int stateId, int filterR, int filterG, int filterB, Entry<String, String> entry) {
		super(x, y, width, height, image, offsetX, offsetY, color, sound, stateId, filterR, filterG, filterB, entry);
	}
	
	@Override
	public void onClick(int button, int x, int y) {
		getSetting().setValue(!Boolean.parseBoolean(getSetting().getValue())+"");
		this.displayedText = entry.getKey()+": "+entry.getValue();
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
	}

}
