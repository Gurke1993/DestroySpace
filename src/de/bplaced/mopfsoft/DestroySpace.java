package de.bplaced.mopfsoft;

import java.io.File;
import java.io.IOException;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.GameState;

//Main Gameclass
public class DestroySpace {
	
	private ClientThread clientThread = null;
	private ConfigurationHandler configurationHandler;
	private AppGameContainer app;
	
	
	// Add new States here (ONLY AS LAST ENTRY!!!):
	private GameState [] gameStateArray = {new MenuState(), new EditorState(), new ServerSelectState(), new GameLobbyState(), new SettingsState(), new MultiplayerGameState(), new LoadingState()};
	private ClientFileTransferThread clientFileTransferThread;
	private FileHandler fileHandler;
	
	private MultiplayerGameManager multiplayerGameManager;

	/**
	 * starts the Game 
	 */
	public DestroySpace(String[] args) {

		System.out.println("Setting up Game...");		
		System.out.println("Loading Config...");
		
		//get configurations
		this.configurationHandler = new ConfigurationHandler("config.txt");
		
		//Setting up Maps
		System.out.println("Creating all needed folders...");
		(new File("maps")).mkdir();
		
		//new Filehandler
		System.out.println("Setting up FileHandler...");
		fileHandler = new FileHandler(this);
		
		//new Screen
		try {
		System.out.println("Setting up Screen...");
		app = new AppGameContainer(new MainScreen("DestroySpace",this, gameStateArray));
	    app.setDisplayMode(1024, 768, true);
	    app.start();
		} catch (Exception e) {
			System.out.println("Could not set up screens!!");
			e.printStackTrace();
		}
		
	}

	//MAIN//
	public static void main(String[] args) {
		//set libarypaths
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		
		//"lets start the program!"
		new DestroySpace(args);
	}

	
	/**
	 * analyzes messages 
	 * @param message : message to analyzing
	 */
	public void analyzeNewMessage(String message) {
		if (message.split(":")[0].equals("1")) {
		message = message.split(":", 2)[1];
		System.out.println("New: "+message);
		int index = Integer.parseInt(message.split(":")[0]);
		switch (index) {
		
		case 0: {
			//Lobby
			((GameLobbyState)gameStateArray[3]).analyzeNewMessage(message.split(":", 2)[1]);
			break;
		}
		
		}
		}
	}
	
	/** 
	 * @return
	 * Client ist connected? true/false
	 */
	public Boolean isConnected() {
		return clientThread != null && clientFileTransferThread != null;
	}
	
	/**
	 * connects the client to the server
	 * @param ip: server IP
	 * @param port: server ort
	 * @return if succsesfully true/false
	 */
	public Boolean connectToServer(String ip, Integer port) {
		System.out.println("Trying to connect...");
		try {
			clientThread = new ClientThread(ip, port, this);
			clientFileTransferThread = new ClientFileTransferThread(ip, 27016, this);
			return true;
		} catch (IOException e) {
			return false;
			//e.printStackTrace();
		}
	}
	
	/** 
	 * @return
	 * ConigurationHandler
	 */
	public ConfigurationHandler getConfigurationHandler() {
		return this.configurationHandler;
	}
	
	/** 
	 * @return
	 * ClientThread
	 */
	public ClientThread getClientThread() {
		return this.clientThread;
	}
	
	/** 
	 * @return
	 * clientFileTransferThread
	 */	
	public ClientFileTransferThread getClientFileTransferThread() {
		return this.clientFileTransferThread;
	}

	/** 
	 * @return
	 * fileHandler
	 */
	public FileHandler getFileHandler() {
		return this.fileHandler;
	}
	
	/** 
	 * @return
	 * multiplayerGameManager
	 */
	public MultiplayerGameManager getMultiplayerGameManager() {
		return multiplayerGameManager;
	}
	
	/**
	 * sets to current multiplayerGameManager
	 * @param multiplayerGameManager 
	 */
	public void setMultiplayerGameManager(MultiplayerGameManager multiplayerGameManager) {
		this.multiplayerGameManager = multiplayerGameManager;
	}
}
