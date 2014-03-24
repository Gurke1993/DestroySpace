
package de.bplaced.mopfsoft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.drawableobjects.DrawableObject;
import de.bplaced.mopfsoft.drawableobjects.DrawableSetting;
import de.bplaced.mopfsoft.drawableobjects.KeySettingDrawable;
import de.bplaced.mopfsoft.drawableobjects.StringSettingDrawable;

public class SettingsState extends BasicGameState{
	public static final int ID = 5;
	private StateBasedGame stateBasedGame;
	private Image backGround;
	private Image hud;
	
	private FileHandler fileHandler;
	private List<DrawableSetting> drawSettings = new ArrayList<DrawableSetting>();
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {

		this.stateBasedGame = stateBasedGame;
		backGround = new Image("resources/images/general/Background.jpg");
		hud = new Image("resources/images/settings/Hud.png");
		
		this.fileHandler = ((MainScreen)stateBasedGame).getDestroySpace().getFileHandler();
		
		//TrueTypeFont font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 26), false);
		
		System.out.println("Generating Settings");
		System.out.println(fileHandler.getSettings());
		
		//Generate Drawable Settings
		int i=0;
		SettingGen:
		for (Entry<String,String> entry: fileHandler.getSettings().entrySet()) {
			
			
			if (entry.getKey().startsWith("system")) continue SettingGen;
			
			if (entry.getKey().startsWith("key")) {
				drawSettings.add(new KeySettingDrawable(200, 200+i*40, 128, 40, null, null, entry));
			} else {
				drawSettings.add(new StringSettingDrawable(200, 200+i*40, 128, 40, null, null, entry));
			}
			i++;
		}
		
		//Register keylistener
		for (DrawableSetting drawableSetting: drawSettings) {
			stateBasedGame.getContainer().getInput().addListener(drawableSetting);
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
		System.out.println(key);
		switch (key) {
			case 1 : {
				
				close();
			}
		}
		
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		System.out.println("Button pressed: "+button+" at "+x+" "+y);
		if (button == 0) {
			
		if( x >= 800 && x <= 1000) {
      	
      	if (y >= 526 && y <= 588) {
      		//Save and quit
    		for (DrawableSetting drawableSetting: drawSettings) {
    			fileHandler.getSettings().put(drawableSetting.getSetting().getKey(), drawableSetting.getSetting().getValue());
    		}
    		
    		fileHandler.saveSettings();
			stateBasedGame.enterState(1);
    
	} else {
		if (button == 1) 
		close();
	}
	}
		}
	}
	
	private void close() {
		stateBasedGame.enterState(1);
	}

}
