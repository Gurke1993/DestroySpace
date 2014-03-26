package de.bplaced.mopfsoft.gameStates;




import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.Handler.FileHandler;
import de.bplaced.mopfsoft.Handler.GameHandler;
import de.bplaced.mopfsoft.Handler.PreGameManager;
import de.bplaced.mopfsoft.Network.ClientThread;
import de.bplaced.mopfsoft.drawableobjects.ChatBox;

public class GameLobbyState extends BasicGameState{
	public static final int ID = 3;
	private Image backGround, hud, startButton, startButtonOff;

	private ChatBox chatBox = null;
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		
		backGround = new Image("resources/images/general/Background.jpg");
		hud = new Image("resources/images/gameLobby/Hud.png");
		startButton = new Image("resources/images/gameLobby/StartButton.png");
		startButtonOff = new Image("resources/images/gameLobby/StartButtonOff.png");
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		graphics.drawImage(backGround, 0, 0);
		graphics.drawImage(hud, 0, 0);
		graphics.drawString(PreGameManager.getInstance().getMapName(), 812, 144);
		graphics.drawString(PreGameManager.getInstance().getMapDescription(), 812, 156);
		
		graphics.drawString("Currently connected: "+PreGameManager.getInstance().getPlayerAmount()+"/"+PreGameManager.getInstance().getMaxPlayerAmount(), 115, 119);
		
		graphics.drawString("Players:", 115, 200);
		int i = 0;
		for (String player: PreGameManager.getInstance().getPlayers()) {
			graphics.drawString(player, 115, 215+i);
			i = i+15;
		}
		

		if (PreGameManager.getInstance().isHost()) {
			if (PreGameManager.getInstance().allPlayersReadyToLoad()) {
				graphics.drawImage(startButton, 801,463);
			} else {
				graphics.drawImage(startButtonOff, 801,463);
			}
		}
		
		if (chatBox != null) {
			chatBox.draw(gameContainer, graphics);
		}
		
		FileHandler.getInstance().drawImageIfLoaded(801, 197, graphics, PreGameManager.getInstance().getMapName()+".gif");
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int timePassed)
			throws SlickException {
		if (!FileHandler.getInstance().isLoaded(PreGameManager.getInstance().getMapName()+".gif")) {
			FileHandler.getInstance().addFile(new File("maps"+System.getProperty("file.separator")+PreGameManager.getInstance().getMapName()+".gif"));
			
			if (FileHandler.getInstance().addFile(new File("maps"+System.getProperty("file.separator")+PreGameManager.getInstance().getMapName()+".gif"))) {
				PreGameManager.getInstance().setClientIsReadyToLoad(true);
			}
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
		PreGameManager.getInstance().disconnect();
  		GameHandler.getInstance().enterState(4);
	}
	

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		//Request Lobbyinformation
		
		PreGameManager.getInstance().reloadLobby();
		
		TrueTypeFont font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 26), false);
		
		this.chatBox = new ChatBox(container, font, Color.green, 102, 400, 150, 400);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		this.chatBox.setFocus(false);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (button == 0) {
		if( x >= 800 && x <= 1000) {
      	if (y >= 463 && y <= 525 && PreGameManager.getInstance().allPlayersReadyToLoad() && PreGameManager.getInstance().getPlayerAmount() == PreGameManager.getInstance().getMaxPlayerAmount()) {
      		//Start game
      		ClientThread.getInstance().send("action=loadupgame");

      		
      	} else
      	if (y >= 526 && y <= 588) {
      		//close
      		close();
      		
      	}
      				        	
      }
	} else {
		if (button == 1) 
		close();
	}
	}


}
