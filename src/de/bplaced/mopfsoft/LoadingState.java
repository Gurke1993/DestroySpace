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
	private PreGameManager pgm;
	private String loadingMessage ="";

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
			for (int i = 0; i<59; i++) {
				graphics.drawLine(274, 313+i, 274+(int)(503*loaded), 313+i);
			}
			graphics.drawRect(274, 313, (int)(503*loaded), 59);
			graphics.drawString(loadingMessage, 274, 380);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (pgm.isMapLoaded() && loaded < 1) {
			//Initialise GameManager
			loadingMessage = "Setting up GameManager...";
			mainScreen.getDestroySpace().setMultiplayerGameManager(new MultiplayerGameManager(mainScreen.getDestroySpace().getClientThread()));
			loaded = 0.7;
			
			
			//Initialise Map
			loadingMessage = "Initialising map";
			mainScreen.getDestroySpace().getMultiplayerGameManager().setMap(mainScreen.getDestroySpace().getPreGameManager().getMapString(),mainScreen.getDestroySpace().getPreGameManager().getPreviewImagePath());
			loaded = 0.9;
			
			loadingMessage = "Waiting for other players...";
			loaded = 1;
			
			pgm.setClientIsReadyToStart(true);
			
		}
//		if (loaded == 1 && pgm.allPlayersReadyToStart()) {
//			System.out.println("Going to MultiplayerGameScreen...");
//			loaded = 1.1;
//			mainScreen.enterState(6);
//		}
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void setLoaded(double d) {
		this.loaded = d;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		System.out.println("Entering LoadingState...");
		pgm = mainScreen.getDestroySpace().getPreGameManager();
		
		//Enable Load mode
		loaded = 0;
		
		//Download map
		loadingMessage = "Downloading map...";
		pgm.downloadMap();
		loaded = 0.1;
	}

	public double getLoaded() {
		return loaded;
	}

}
