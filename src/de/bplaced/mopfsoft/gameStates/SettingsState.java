
package de.bplaced.mopfsoft.gameStates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.drawableobjects.BooleanSettingDrawable;
import de.bplaced.mopfsoft.drawableobjects.DrawableObject;
import de.bplaced.mopfsoft.drawableobjects.DrawableSetting;
import de.bplaced.mopfsoft.drawableobjects.KeySettingDrawable;
import de.bplaced.mopfsoft.drawableobjects.StringSettingDrawable;
import de.bplaced.mopfsoft.handler.FileHandler;
import de.bplaced.mopfsoft.handler.GameHandler;

public class SettingsState extends BasicGameState{
	public static final int ID = 5;
	private static final List <String> ignoredSettings = Arrays.asList("system","network");
	
	private Image backGround;
	private Image hud;
	private Image settingBackground;
	
	private List<DrawableSetting> drawSettings = new ArrayList<DrawableSetting>();
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {

		backGround = new Image("resources/images/general/Background.jpg");
		hud = new Image("resources/images/settings/Hud.png");
		settingBackground = new Image("resources/images/settings/SettingBackground.png");
		
		
		//Generate Drawable Settings
		int i=0;
		SettingGen:
		for (Entry<String,String> entry: FileHandler.getInstance().getSettings().entrySet()) {
			
			
			if (ignoredSettings.contains(entry.getKey().split("\\.")[1])) continue SettingGen;
			
			if (entry.getKey().split("\\.")[0].equals("key")) {
				drawSettings.add(new KeySettingDrawable(150, 110+i*20, 600, 20, settingBackground, 3, 1, Color.green, null, ID, entry));
			} else
				
			if (entry.getKey().split("\\.")[0].equals("string")) {
				drawSettings.add(new StringSettingDrawable(150, 110+i*20, 600, 20, settingBackground, 3, 1, Color.green, null, ID, entry));
			} else
			
			if (entry.getKey().split("\\.")[0].equals("boolean")) {
				drawSettings.add(new BooleanSettingDrawable(150, 110+i*20, 600, 20, settingBackground, 3, 1, Color.green, null, ID, entry));
			} else {
				Log.error("Found unknown setting kind: "+entry.getKey().split("\\.")[1]+"... ignoring...");
			}
				
			i++;
		}
		
		
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		graphics.drawImage(backGround,0,0);
		graphics.drawImage(hud,0,0);
		
		for (DrawableObject dobj: drawSettings) {
			dobj.draw(gameContainer, graphics);
		}
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int timePassed)
			throws SlickException {
	}

	@Override
	public int getID() {
		return ID;
	}
	
	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
			case 1 : {
				
				close();
			}
		}
		
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		Log.debug("Button pressed: "+button+" at "+x+" "+y);
		if (button == 0) {
			
		if( x >= 800 && x <= 1000) {
      	
      	if (y >= 526 && y <= 588) {
      		//Save and quit
    		for (DrawableSetting drawableSetting: drawSettings) {
    			FileHandler.getInstance().getSettings().put(drawableSetting.getSetting().getKey(), drawableSetting.getSetting().getValue());
    		}
    		
    		FileHandler.getInstance().saveSettings();
    		FileHandler.getInstance().loadSettings();
			GameHandler.getInstance().enterState(1);
    
	} else {
		if (button == 1) 
		close();
	}
	}
		}
	}
	
	private void close() {
		GameHandler.getInstance().enterState(1);
	}

}
