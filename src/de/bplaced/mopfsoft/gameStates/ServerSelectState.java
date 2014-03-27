package de.bplaced.mopfsoft.gameStates;

import util.Util;
import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.handler.FileHandler;
import de.bplaced.mopfsoft.handler.GameHandler;
import de.bplaced.mopfsoft.network.ClientThread;



public class ServerSelectState extends BasicGameState{
	public static final int ID = 4;
	private Image backGround, hud, overlayFade, newServerHud;
	private String[] favoriteServers;
	private int selectedServer = 0;
	private Boolean addingServer = false;
	private TextField newServerName, newServerIp, newServerPort;
	private String screenOutput = "";
	
	
	
	public void loadFavoriteServers() {
		if (FileHandler.getInstance().getSetting("string.network.serverFavorites") != "") {
			if ((favoriteServers = FileHandler.getInstance().getSetting("string.network.serverFavorites").split(",")) == null) {
				//One entry
				favoriteServers = new String[1];
				favoriteServers[0] = FileHandler.getInstance().getSetting("string.network.serverFavorites");
			}
		} else {
			//No entry
			favoriteServers = new String[0];
		}
	}
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		
		loadFavoriteServers();
		backGround = new Image("resources/images/general/Background.jpg");
		hud = new Image("resources/images/serverSelect/Hud.png");
		overlayFade = new Image("resources/images/general/OverlayFade.png");
		TrueTypeFont font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 26), false);
		newServerHud = new Image("resources/images/serverSelect/NewServerHud.png");
		(newServerName = new TextField(gameContainer, font, 305, 270, 185, 30)).setBorderColor(Color.green);
		newServerName.setTextColor(Color.green);
		(newServerIp = new TextField(gameContainer, font, 305, 340, 185, 30)).setBorderColor(Color.green);
		newServerIp.setTextColor(Color.green);
		(newServerPort = new TextField(gameContainer, font, 305, 410, 185, 30)).setBorderColor(Color.green);
		newServerPort.setTextColor(Color.green);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		graphics.drawImage(backGround,0,0);
		graphics.drawImage(hud,0,0);
		
		for (int i = 0; i< favoriteServers.length; i++) {
			
			if (i == selectedServer) {
				graphics.setColor(Color.gray);
			} else {
				graphics.setColor(Color.darkGray);
			}
			
			graphics.fillRect(110, 110+i*40, 300, 40);
			graphics.setColor(Color.white);
			graphics.drawString(favoriteServers[i].split(";")[0]+":"+favoriteServers[i].split(";")[1], 120, 133+i*40);
			graphics.setColor(Color.green);
			graphics.drawString(favoriteServers[i].split(";")[2], 120, 113+i*40);
			graphics.setColor(Color.red);
			graphics.drawString(screenOutput,116,570);
		}
		
		if (addingServer) {
			graphics.drawImage(overlayFade, 0, 0);
			graphics.drawImage(newServerHud, 0, 0);
			graphics.setColor(Color.green);
			newServerName.render(gameContainer, graphics);
			newServerIp.render(gameContainer, graphics);
			newServerPort.render(gameContainer, graphics);
		}
		
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int timePassed)
			throws SlickException {
		
	}

	@Override
	public int getID() {
		return ID;
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		Log.debug("Button pressed: "+button+" at "+x+" "+y);
		if (addingServer) {
			//Adding a new server
		
			if (button == 0) {
				if (x >= 308 && y <= 480 && x <= 484 && y >= 450) {
					String name = newServerName.getText();
					String ip = newServerIp.getText();
					String port = newServerPort.getText();
					if (name.contains(",") || name.contains(":")) {
						newServerName.setText("");
						newServerName.setBackgroundColor(Color.red);
						newServerName.setFocus(true);
					} else {
						newServerName.setBackgroundColor(Color.gray);
					if (!Util.isIp(ip)) {
						newServerIp.setText("");
						newServerIp.setFocus(true);	
						newServerIp.setBackgroundColor(Color.red);
					} else {
						newServerIp.setBackgroundColor(Color.gray);
					if (!Util.isIntegerBetween(port,1,99999)) {
						newServerPort.setText("");
						newServerPort.setFocus(true);
						newServerPort.setBackgroundColor(Color.red);
					} else {
						newServerPort.setBackgroundColor(Color.gray);
						//Save new Server
			      		String newFavoriteServers = FileHandler.getInstance().getSetting("string.network.serverFavorites")+","+ip+";"+port+";"+name;
			      		FileHandler.getInstance().setSetting("string.network.serverFavorites", newFavoriteServers);
			      		loadFavoriteServers();
						
						addingServer = false;
					}
				}
					}
				}
			
			} else
			
			if (button == 1) {
				addingServer = false;
			}
		
			
		} else {
			//Normal View
		
		if (button == 0) {
		if( x >= 800 && x <= 1000) {
      	
      	if (y >= 130 && y <= 190) {
      		//ADD Server
      		addingServer = true;
      		
      	} else
      	if (y >= 197 && y <= 257) {
      		//REMOVE Server
      		String newFavoriteServers = FileHandler.getInstance().getSetting("string.network.serverFavorites").replaceAll(favoriteServers[selectedServer]+",", "");
      		newFavoriteServers = FileHandler.getInstance().getSetting("string.network.serverFavorites").replaceAll(","+favoriteServers[selectedServer], "");
      		FileHandler.getInstance().setSetting("string.network.serverFavorites", newFavoriteServers);
      		loadFavoriteServers();
      		screenOutput = "Removed Server!";
      		
      	} else 	
      	if (y >= 526 && y <= 587) {
      		//Connect to selected Server
      		if (!ClientThread.connectToServer(favoriteServers[selectedServer].split(";")[0], Integer.parseInt(favoriteServers[selectedServer].split(";")[1]))) {
      			//Could not connect to Server
      			Log.error("Failed to connect.");
      			screenOutput = "["+System.currentTimeMillis()+"] Failed to connect to Server!";
      		} else {
      			//Connected sucessfully -> switch to Lobby
      			GameHandler.getInstance().enterState(3);
      		}

      	}
      } else {
    	if (x >= 110 && x < 800) {
          	if (y >= 110 && y <= 110+favoriteServers.length*40) {
          		//Select new Server
          		selectedServer = (y-110)/40;
          		
          	}
    	}
      }
	} else {
	if (button == 1) 
		close();
		}
		}
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
		if (addingServer) {
			addingServer = false;
		} else
  		GameHandler.getInstance().enterState(1);
	}

}


