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
	
	public void setupGame() {
		//Setup game 
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		//Initialise GameManager
		mainScreen.getDestroySpace().setMultiplayerGameManager(new MultiplayerGameManager(mainScreen.getDestroySpace().getClientThread()));
		loaded = 0.3;
		
		//Initialise Map
		mainScreen.getDestroySpace().getMultiplayerGameManager().setMap(((GameLobbyState)mainScreen.getDestroySpace().getGameState(3)).getMapString(),((GameLobbyState)mainScreen.getDestroySpace().getGameState(3)).getPreviewImagePath());
		loaded = 0.9;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		//Enable Load mode
		loaded = 0;
		
		//TODO update method f�r preload progressbar siehe inet defferedloading
		
		
		//Setup game
		setupGame();
		loaded = 1;
	}

	public double getLoaded() {
		return loaded;
	}

}
