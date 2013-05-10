package de.bplaced.mopfsoft;




import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameLobbyState extends BasicGameState{
	public static final int ID = 3;
	private Image backGround, hud;
	private MainScreen mainScreen;

	private Image startButton;
	private Image startButtonOff;
	private ChatBox chatBox = null;
	private PreGameManager pgm;
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		mainScreen = (MainScreen)stateBasedGame;
		
		
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
		graphics.drawString(pgm.getMapName(), 812, 144);
		graphics.drawString(pgm.getMapDescription(), 812, 156);
		
		graphics.drawString("Currently connected: "+pgm.getPlayerAmount()+"/"+pgm.getMaxPlayerAmount(), 115, 119);
		
		graphics.drawString("Players:", 115, 200);
		int i = 0;
		for (String player: pgm.getPlayers()) {
			graphics.drawString(player, 115, 215+i);
			i = i+15;
		}
		

		if (pgm.isHost()) {
			if (pgm.allPlayersReadyToLoad()) {
				graphics.drawImage(startButton, 801,463);
			} else {
				graphics.drawImage(startButtonOff, 801,463);
			}
		}
		
		if (chatBox != null) {
			chatBox.draw(gameContainer, graphics);
		}
		
		mainScreen.getDestroySpace().getFileHandler().drawImageIfLoaded(801, 197, graphics, pgm.getMapName()+".gif");
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int timePassed)
			throws SlickException {
		if (!mainScreen.getDestroySpace().getFileHandler().isFileLoaded(pgm.getMapName()+".gif")) {
			mainScreen.getDestroySpace().getFileHandler().tryToLoadFile(pgm.getMapName()+".gif");
			
			if (mainScreen.getDestroySpace().getFileHandler().isFileLoaded(pgm.getMapName()+".gif")) {
				mainScreen.getDestroySpace().getPreGameManager().setClientIsReadyToLoad(true);
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
		pgm.disconnect();
  		mainScreen.enterState(4);
	}
	

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		//Request Lobbyinformation
		mainScreen.getDestroySpace().setPreGameManager(new PreGameManager(mainScreen));
		
		pgm = mainScreen.getDestroySpace().getPreGameManager();
		pgm.reloadLobby();
		
		TrueTypeFont font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 26), false);
		
		this.chatBox = new ChatBox(mainScreen.getDestroySpace().getClientThread().getChatManager(), container, font, Color.green, 102, 400, 150, 400);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		this.chatBox.setFocus(false);
	}

	




	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (button == 0) {
		if( x >= 800 && x <= 1000) {
      	if (y >= 463 && y <= 525 && pgm.allPlayersReadyToLoad() && pgm.getPlayerAmount() == pgm.getMaxPlayerAmount()) {
      		//Start game
      		mainScreen.getDestroySpace().getClientThread().send("action=loadupgame");

      		
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
