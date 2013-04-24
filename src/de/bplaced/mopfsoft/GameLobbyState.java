package de.bplaced.mopfsoft;

import java.util.Arrays;

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
	private String playerAmount ="";
	private String maxPlayerAmount ="";
	private String[] players = new String[0];
	private String mapDescription = "";
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		mainScreen = (MainScreen)stateBasedGame;
		backGround = new Image("resources/images/general/Background.jpg");
		hud = new Image("resources/images/gameLobby/Hud.png");
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		graphics.drawImage(backGround, 0, 0);
		graphics.drawImage(hud, 0, 0);
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
		mainScreen.getDestroySpace().getClientThread().closeByClient();
  		mainScreen.enterState(4);
	}
	

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		//Request Lobbyinformation
		send("0:0:1");
	}

	
	public void send(String message) {
		mainScreen.getDestroySpace().getClientThread().send(message);
	}
	
	public void analyzeNewMessage(String message) {
		int index = Integer.parseInt(message.split(":")[0]);
		switch (index) {
		case 1: {
			//Recieved Lobbyinformation
			setLobbyInformation(message.split(":", 2)[1]);
			break;
		}
		}
	}

	private void setLobbyInformation(String string) {
		System.out.println("Setting Lobbyinformation...");
		String[] array = string.split(":");
		this.mapName = array[0];
		this.mapDescription = array[1];
		this.playerAmount = array[2];
		this.maxPlayerAmount = array[3];
		if (array.length > 4) {
			this.players = Arrays.copyOfRange(array,4,array.length-1);
		} else {
			this.players = new String[0];
		}
		System.out.println("Asking for fileinformation...");
		send("0:-1:2:"+mapName+".gif");
		
		
	}
}
