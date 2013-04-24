package de.bplaced.mopfsoft;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingState extends BasicGameState{
	public static final int id = 7;
	private GameField gameField;
	private MainScreen mainScreen;
	private Image loadingScreen;
	private double loaded;

	@Override
	public void init(GameContainer arg0, StateBasedGame stateBasedGame)
			throws SlickException {
		mainScreen = (MainScreen)stateBasedGame;
		loadingScreen = new Image("resources/images/multiplayerGame/LoadingScreen.jpg");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		//LoadingScreen
		if (loaded < 1) {
			graphics.drawImage(loadingScreen, 0, 0);
			graphics.setColor(Color.green);
			graphics.drawRect(274, 313, (int)(503*loaded), 59);
		} else {
			//TODO
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void setLoaded(double d) {
		this.loaded = d;
	}
	
	public void setupGame(int [] gameFieldArray, int collumnLength, String [] [] playerArray) {
		//Setup game 
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Initialise GameField
		this.gameField = new GameField(gameFieldArray, collumnLength);
		loaded = 0.8;
		
		//Load Players
		for (int i = 0; i< playerArray.length; i++) {
		this.gameField.addPlayer(playerArray[i]);
		}
		loaded = 0.9;
		//
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		//Enable Load mode
		loaded = 0;
		
		//TODO , send method, serverside array sending
		
		//TODO update method für preload progressbar siehe inet defferedloading
		
		/*
		 * TEST
		 */
		int [] testArray = new int[20000];
		for (int i = 0; i<testArray.length; i++) {
			testArray[i] = i%2;
		}
		
		//Name Level Champion Handicap
		String [][] testPlayerArray = new String [2][4];
		String [] player1 = {"Gurke1993","Newbie","Heimerdinger","100"};
		String [] player2 = {"Darabasi","Pro","Evelynn","70"};
		testPlayerArray[0] = player1;
		testPlayerArray[1] = player2;
		
		//Setup game
		new Thread(new LoadingThread(this, testArray, 200, testPlayerArray)).start();
		loaded = 1;
	}

	public double getLoaded() {
		return loaded;
	}

}
