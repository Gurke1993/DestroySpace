package de.bplaced.mopfsoft;

import java.io.File;
import javax.swing.JOptionPane;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.state.GameState;

//Main Gameclass
public class DestroySpace {
	
	public static final int BUILD = 1;
	public static final GameState [] GAME_STATE_ARRAY = {new MenuState(), new EditorState(), new ServerSelectState(), new GameLobbyState(), new SettingsState(), new MultiplayerGameState(), new LoadingState()};
	public static final String GAME_NAME = "DestroySpace Beta";
	
	
	
	
	//private ClientThread clientThread = null;
	
	
	//private ClientFileTransferThread clientFileTransferThread;
	
	

	/**
	 * starts the Game 
	 */
	public DestroySpace(String[] args) {
		
		try {

		System.out.println("Setting up Game...");		
		System.out.println("Loading Config...");
		
		//Create folder structure
		System.out.println("Creating all needed folders...");
		(new File("maps")).mkdir();
		
		
		//init Different Handlerclasses
		System.out.println("Init ConfigHandler...");
		ConfigurationHandler.init("config.txt");
		
		System.out.println("Init FileHandler...");
		FileHandler.init(this);
		
		System.out.println("Init GameHandler...");
		GameHandler.init();
		
		System.out.println("Init PreGameManager...");
		PreGameManager.init();
		
		System.out.println("Init MultiplayerGameManager...");
		MultiplayerGameManager.init();
		
		System.out.println("Init AppGameContainer...");
		AppGameContainerExtended.init();
		
		AppGameContainerExtended.getInstance().setDisplayMode(1024, 768, true);
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
