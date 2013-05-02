package de.bplaced.mopfsoft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SettingsState extends BasicGameState{
	public static final int ID = 5;
	private StateBasedGame stateBasedGame;
	private Image backGround;
	@SuppressWarnings("unused")
	private Image hud;
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		// TODO Auto-generated method stub

		this.stateBasedGame = stateBasedGame;
		backGround = new Image("resources/images/general/Background.jpg");
		hud = new Image("resources/images/menu/Hud.png");
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		graphics.drawImage(backGround,0,0);
		//graphics.drawImage(hud,0,0);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int timePassed)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
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
			
			stateBasedGame.enterState(7);
//		if( x >= 800 && x <= 1000) {
//      	
//      	if (y >= 130 && y <= 190) {
//      		//Start Multiplayer
//      		stateBasedGame.enterState(4);
//      		
//      	} else
//      	if (y >= 197 && y <= 257) {
//      		//Start Editor
//      		stateBasedGame.enterState(2);
//      		
//      	} else
//      	if (y >= 264 && y <= 324) {
//      		//Start Settings
//      		stateBasedGame.enterState(5);
//      	
//      	} else
//      	if (y >= 331 && y <= 391) {
//      		//Close
//      		close();
//      	}
      				        	
//      }
	} else {
		if (button == 1) 
		close();
	}
	}
	
	private void close() {
		stateBasedGame.enterState(1);
	}

}
