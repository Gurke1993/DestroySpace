package de.bplaced.mopfsoft.drawableobjects;

import java.util.Map.Entry;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public class BooleanSettingDrawable  extends DrawableSetting{

	public BooleanSettingDrawable(int x, int y, int width, int height,
			Image image, Sound sound, int stateId, Entry<String, String> entry) {
		super(x, y, width, height, image, sound, stateId, entry);
	}
	
	@Override
	public void onClick(int button, int x, int y) {
		getSetting().setValue(!Boolean.parseBoolean(getSetting().getValue())+"");
		this.displayedValue = getSetting().getValue();
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
	}

}
