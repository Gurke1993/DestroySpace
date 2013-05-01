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
			graphics.drawImage(loadingScreen, 0, 0);
			graphics.setColor(Color.green);
			graphics.drawRect(274, 313, (int)(503*loaded), 59);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (loaded >= 1) {
			mainScreen.enterState(6);
		}
		
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
		mainScreen.getDestroySpace().setGameField(new GameField(gameFieldArray, collumnLength));
		loaded = 0.8;
		
		//Load Players
		for (int i = 0; i< playerArray.length; i++) {
		mainScreen.getDestroySpace().getGameField().addPlayer(playerArray[i]);
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
		int [] testArray = new int[700000];
		for (int i = 0; i<testArray.length; i++) {
			testArray[i] = i;
		}
		
		System.out.println(testArray[12]+" "+testArray[13]);
		System.out.println(new Color(testArray[12])+" "+new Color(testArray[13]));
		//Name Level Champion Handicap
		String [][] testPlayerArray = new String [2][4];
		String [] player1 = {"Gurke1993","Newbie","Heimerdinger","100"};
		String [] player2 = {"Darabasi","Pro","Evelynn","70"};
		testPlayerArray[0] = player1;
		testPlayerArray[1] = player2;
		
		//Setup game
		setupGame(testArray, 1000, testPlayerArray);
		loaded = 1;
	}

	public double getLoaded() {
		return loaded;
	}

}
