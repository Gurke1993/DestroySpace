package de.bplaced.mopfsoft;

import java.io.File;
import java.io.IOException;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.GameState;

public class DestroySpace {
	
	private ClientThread clientThread = null;
	private ConfigurationHandler configurationHandler;
	private AppGameContainer app;
	
	
	// Add new States here (ONLY AS LAST ENTRY!!!):
	private GameState [] gameStateArray = {new MenuState(), new EditorState(), new ServerSelectState(), new GameLobbyState(), new SettingsState(), new MultiplayerGameState(), new LoadingState()};
	private ClientFileTransferThread clientFileTransferThread;
	private FileHandler fileHandler;
	
	private MultiplayerGameManager multiplayerGameManager;

	
	public DestroySpace(String[] args) {

		System.out.println("Setting up Game...");
		
		System.out.println("Loading Config...");
		this.configurationHandler = new ConfigurationHandler("config.txt");
		
		
		System.out.println("Creating all needed folders...");
		(new File("maps")).mkdir();
		
		System.out.println("Setting up FileHandler...");
		fileHandler = new FileHandler(this);
		
		try {
		System.out.println("Setting up Screen...");
		app = new AppGameContainer(new MainScreen("DestroySpace",this, gameStateArray));
	     app.setDisplayMode(1024, 768, true);
	    // app.setDisplayMode(1920, 1080, true);
	     app.start();
		} catch (Exception e) {
			System.out.println("Could not set up screens!!");
			e.printStackTrace();
		}
		
	}

	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		new DestroySpace(args);
	}

	
	//This function gets called every time a message arrives clientside
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

	public Boolean isConnected() {
		return clientThread != null && clientFileTransferThread != null;
	}
	
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
	
	public ConfigurationHandler getConfigurationHandler() {
		return this.configurationHandler;
	}
	
	public ClientThread getClientThread() {
		return this.clientThread;
	}
	
	public ClientFileTransferThread getClientFileTransferThread() {
		return this.clientFileTransferThread;
	}


	public FileHandler getFileHandler() {
		return this.fileHandler;
	}
	
	public MultiplayerGameManager getMultiplayerGameManager() {
		return multiplayerGameManager;
	}
	
	public void setMultiplayerGameManager(MultiplayerGameManager multiplayerGameManager) {
		this.multiplayerGameManager = multiplayerGameManager;
	}
}
