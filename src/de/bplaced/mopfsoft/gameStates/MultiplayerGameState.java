package de.bplaced.mopfsoft.gameStates;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.handler.FileHandler;
import de.bplaced.mopfsoft.handler.GameHandler;
import de.bplaced.mopfsoft.handler.MultiplayerGameManager;
import de.bplaced.mopfsoft.message.ClientDisconnect;
import de.bplaced.mopfsoft.network.ClientThread;

public class MultiplayerGameState extends BasicGameState{
	public static final int id = 6;
	@SuppressWarnings("unused")
	private int currentKey;
	private ConcurrentLinkedQueue<String> usedKeys = new ConcurrentLinkedQueue<String>();
	private Image backGround;

	@Override
	public void init(GameContainer arg0, StateBasedGame stateBasedGame)
			throws SlickException {
		backGround = new Image("resources/images/general/Background.jpg");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		graphics.drawImage(backGround, 0, 0);
		MultiplayerGameManager.getInstance().getMap().drawAll(graphics,40,40);

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int timePassed)
			throws SlickException {
		
		
		//MultiplayerGameLoop
		MultiplayerGameManager.getInstance().doGameLoop(container, sbg, timePassed, usedKeys);
	}

	@Override
	public int getID() {
		return id;
	}
	
	
	public void close() {

		ClientThread.getInstance().send(""+new ClientDisconnect());
		ClientThread.getInstance().close();
  		GameHandler.getInstance().enterState(4);
	}
	
	@Override
	public void keyPressed(int key, char c) {

		if (key == 1) {
			//ESC
			close();
			return;
		}
		
		if (FileHandler.getInstance().getInputSettings().containsKey(key))
		this.usedKeys.add(FileHandler.getInstance().getInputSettings().get(key));
		
	}
	
	@Override
	public void keyReleased(int key, char c) {
		while (this.usedKeys.remove(FileHandler.getInstance().getInputSettings().get(key))) {
		}
	}
	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {

	}


}
