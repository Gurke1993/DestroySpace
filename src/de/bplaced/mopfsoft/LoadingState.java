package de.bplaced.mopfsoft;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.blocks.Block;
import de.bplaced.mopfsoft.blocks.Stone;

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
	
	public void setupGame(Block [][] gameFieldArray, String [] [] playerArray) {
		//Setup game 
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Initialise GameField
		mainScreen.getDestroySpace().setMultiplayerGameManager(new MultiplayerGameManager(mainScreen.getDestroySpace().getClientThread()));
		mainScreen.getDestroySpace().getMultiplayerGameManager().setGameField(gameFieldArray);
		loaded = 0.8;
		
		//Load Players
		for (int i = 0; i< playerArray.length; i++) {
		mainScreen.getDestroySpace().getMultiplayerGameManager().getGameField().addPlayer(playerArray[i]);
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
		Block [][] testArray = new Block[1000][700];
		for (int i = 0; i<testArray.length; i++) {
			for (int j = 0; j<testArray[0].length; j++) {
			testArray[i][j] = new Stone(i,j);
			}
		}
		
		//Name Level Champion Handicap
		String [][] testPlayerArray = new String [2][4];
		String [] player1 = {"Gurke1993","Newbie","Heimerdinger","100"};
		String [] player2 = {"Darabasi","Pro","Evelynn","70"};
		testPlayerArray[0] = player1;
		testPlayerArray[1] = player2;
		
		//Setup game
		setupGame(testArray, testPlayerArray);
		loaded = 1;
	}

	public double getLoaded() {
		return loaded;
	}

}
