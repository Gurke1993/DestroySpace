package de.bplaced.mopfsoft;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MultiplayerGameState extends BasicGameState{
	public static final int id = 6;
	private MainScreen mainScreen;
	@SuppressWarnings("unused")
	private int currentKey;
	private ConcurrentLinkedQueue<String> usedKeys = new ConcurrentLinkedQueue<String>();
	private Map <Integer,String> keyMap = new HashMap <Integer,String>();
	private Image backGround;

	@Override
	public void init(GameContainer arg0, StateBasedGame stateBasedGame)
			throws SlickException {
		mainScreen = (MainScreen)stateBasedGame;
		backGround = new Image("resources/images/general/Background.jpg");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		graphics.drawImage(backGround, 0, 0);
		//Draw gamefield, entities
		mainScreen.getDestroySpace().getMultiplayerGameManager().getMap().drawAll(graphics,40,40);

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int timePassed)
			throws SlickException {
		
		
		//MultiplayerGameLoop
		mainScreen.getDestroySpace().getMultiplayerGameManager().doGameLoop(container, sbg, timePassed, usedKeys);
	}

	@Override
	public int getID() {
		return id;
	}
	
	
	public void close() {

		mainScreen.getDestroySpace().getClientThread().send("action=clientdisconnect");
		mainScreen.getDestroySpace().getClientThread().close();
  		mainScreen.enterState(4);
	}
	
	@Override
	public void keyPressed(int key, char c) {

		if (key == 1) {
			//ESC
			close();
			return;
		}
		if (keyMap.containsKey(key))
		this.usedKeys.add(keyMap.get(key));
		
	}
	
	@Override
	public void keyReleased(int key, char c) {
		while (this.usedKeys.remove(keyMap.get(key))) {
			
		}
	}
	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		
		System.out.println("Going to Multiplayer screen...");
		
		System.out.println("Updating key settings...");
		keyMap.put(203, "type=move:direction=left");
		keyMap.put(200, "type=jump");
		keyMap.put(205, "type=move:direction=right");
		keyMap.put(208, "type=move:direction=down");
		keyMap.put(57, "type=use:tid=space");
	}


}
