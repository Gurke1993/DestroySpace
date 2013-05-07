package de.bplaced.mopfsoft;



import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameLobbyState extends BasicGameState{
	public static final int ID = 3;
	private Image backGround, hud;
	private MainScreen mainScreen;
	private String mapName = "";
	private int playerAmount = -1;
	private int maxPlayerAmount = -1;
	private String[] players = new String[0];
	private String mapDescription = "";
	private String mapString = "";
	@SuppressWarnings("unused")
	private boolean mapStringIsReady = false;
	private boolean host;
	private Image startButton;
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		mainScreen = (MainScreen)stateBasedGame;
		backGround = new Image("resources/images/general/Background.jpg");
		hud = new Image("resources/images/gameLobby/Hud.png");
		startButton = new Image("resources/images/gameLobby/StartButton.png");
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		graphics.drawImage(backGround, 0, 0);
		graphics.drawImage(hud, 0, 0);
		graphics.drawImage(startButton, 120,120);
		graphics.drawString(mapName, 812, 144);
		graphics.drawString(playerAmount+" "+maxPlayerAmount+" "+mapDescription, 100, 100);
		int i = 0;
		for (String player: players) {
			graphics.drawString(player, 100, 130+i);
			i = i+10;
		}
		mainScreen.getDestroySpace().getFileHandler().drawImageIfLoaded(801, 197, graphics, mapName+".gif");
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int timePassed)
			throws SlickException {
		if (!mainScreen.getDestroySpace().getFileHandler().isFileLoaded(mapName+".gif")) {
			mainScreen.getDestroySpace().getFileHandler().tryToLoadFile(mapName+".gif");
		}
		
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
	
	private void close() {
		mainScreen.getDestroySpace().getClientThread().close();
  		mainScreen.enterState(4);
	}
	

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		//Request Lobbyinformation
		send("action=getlobbyinfo");
	}

	
	public void send(String message) {
		mainScreen.getDestroySpace().getClientThread().send(message);
	}

	public void setLobbyInformation(String mapName, String mapDescription, int playerAmount, int maxPlayerAmount, String[] players, boolean host) {
		System.out.println("Setting Lobbyinformation...");
		this.mapName = mapName;
		this.mapDescription = mapDescription;
		this.playerAmount = playerAmount;
		this.maxPlayerAmount = maxPlayerAmount;
		this.players = players;
		this.host = host;

		
		System.out.println("Ask for mapstring...");
		send("action=getMapString:filename="+mapName+".map:path=maps"+System.getProperty("file.separator")+mapName+".map");
		
		System.out.println("Ask for mappreviewinfo...");
		send("action=getfiletransferinfo:filename="+mapName+".gif:path=maps"+System.getProperty("file.separator")+mapName+".gif");
	}
	
	public void loadUpGame() {
		mainScreen.enterState(7);
	}

	public String getMapString() {
		return this.mapString;
	}
	
	public void setMapString(String mapString) {
		this.mapString = mapString;
	}

	public void addToMapString(String string) {
		this.mapString += string;
	}

	public void setMapStringIsFinished(boolean b) {
		this.mapStringIsReady  = b;
	}

	public String getPreviewImagePath() {
		return "maps"+System.getProperty("file.separator")+mapName+".gif";
	}

	public void reloadLobby() {
		send("action=getlobbyinfo");
	}
}
