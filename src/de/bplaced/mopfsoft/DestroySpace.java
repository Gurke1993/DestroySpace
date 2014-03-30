package de.bplaced.mopfsoft;

import java.io.File;

import javax.swing.JOptionPane;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.util.Log;

import util.AppGameContainerExtended;

import de.bplaced.mopfsoft.gameStates.DebugState;
import de.bplaced.mopfsoft.gameStates.EditorState;
import de.bplaced.mopfsoft.gameStates.GameLobbyState;
import de.bplaced.mopfsoft.gameStates.LoadingState;
import de.bplaced.mopfsoft.gameStates.MenuState;
import de.bplaced.mopfsoft.gameStates.MultiplayerGameState;
import de.bplaced.mopfsoft.gameStates.ServerSelectState;
import de.bplaced.mopfsoft.gameStates.SettingsState;
import de.bplaced.mopfsoft.handler.FileHandler;
import de.bplaced.mopfsoft.handler.GameHandler;
import de.bplaced.mopfsoft.handler.MultiplayerGameManager;
import de.bplaced.mopfsoft.handler.PreGameManager;
import de.bplaced.mopfsoft.logging.CustomLogSystem;

//Main Gameclass
public class DestroySpace {
	
	public static final int LOGGING_LEVEL = CustomLogSystem.HIGH;
	public static final int BUILD = 1;
	//TODO EditorState
	public static final GameState [] GAME_STATE_ARRAY = {new MenuState(), new EditorState(), new ServerSelectState(), new GameLobbyState(), new SettingsState(), new MultiplayerGameState(), new LoadingState()};
	public static final String GAME_NAME = "DestroySpace Beta";
	
	

	/**
	 * starts the Game 
	 */
	private DestroySpace(String[] args) {
		
		try {

		Log.setLogSystem(new CustomLogSystem(LOGGING_LEVEL));

		Log.info("Setting up Game...");	
		
		//Create folder structure
		Log.info("Creating all needed folders...");
		(new File("maps")).mkdir();
		
		
		//init Different Handler and Manager Classes
		
		Log.info("Init FileHandler...");
		FileHandler.init();
		
		Log.info("Init GameHandler...");
		GameHandler.init();
		
		Log.info("Init PreGameManager...");
		PreGameManager.init();
		
		Log.info("Init MultiplayerGameManager...");
		MultiplayerGameManager.init();
		
		Log.info("Init AppGameContainer...");
		AppGameContainerExtended.init();
		
		AppGameContainerExtended.getInstance().setDisplayMode(1024, 768, Boolean.parseBoolean(FileHandler.getInstance().getSetting("boolean.graphics.fullscreen")));
		AppGameContainerExtended.getInstance().start();

		
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Could not launch game!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	//MAIN//
	public static void main(String[] args) {
		
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		
		new DestroySpace(args);
	}

}
