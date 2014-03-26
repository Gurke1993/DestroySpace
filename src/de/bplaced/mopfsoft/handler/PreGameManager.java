package de.bplaced.mopfsoft.handler;

import java.util.Map;

import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.network.ClientThread;

public class PreGameManager {
	private static PreGameManager instance = null;
	
	private String mapName = "";
	private int playerAmount = -1;
	private int maxPlayerAmount = -1;
	private String[] players = new String[0];
	private String mapDescription = "";
	private String mapString = "";
	private boolean isHost = false;
	private boolean mapStringIsReady = false;
	private boolean allPlayersReadyToLoad = false;
	private boolean allPlayersReadyToStart = false;
	private boolean isReadyToStart;
	
	private PreGameManager() {
	}
	
	
	public void setLobbyInformation(String mapName, String mapDescription, int playerAmount, int maxPlayerAmount, String[] players, boolean host) {
		this.mapName = mapName;
		this.mapDescription = mapDescription;
		this.playerAmount = playerAmount;
		this.maxPlayerAmount = maxPlayerAmount;
		this.players = players;
		this.isHost = host;
		
		Log.info("Requesting lobby information from server...");
		send("action=getfiletransferinfo:filename="+mapName+".gif:path=maps"+System.getProperty("file.separator")+mapName+".gif");
	
	}
	
	public void send(String message) {
		ClientThread.getInstance().send(message);
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

	public synchronized void setMapStringIsFinished(boolean b) {
		this.mapStringIsReady  = b;
	}
	
	public boolean getClientIsReadyToStart() {
		return this.isReadyToStart;
	}
	
	public void setClientIsReadyToStart(boolean isReady) {
		Log.debug("Client is ready to start: " + isReady);
		this.isReadyToStart = isReady;
		send("action=readyto:type=start:isready=" + isReady);
	}
	
	public void setClientIsReadyToLoad(boolean isReady) {
		Log.debug("Client is ready to load: " + isReady);
		send("action=readyto:type=load:isready=" + isReady);
	}


	public void loadUpGame() {
		GameHandler.getInstance().enterState(7);
	}
	
	public void startGame() {
		Log.info("Going to MultiplayerGameScreen...");
		GameHandler.getInstance().enterState(6);
	}

	public String getPreviewImagePath() {
		return "maps"+System.getProperty("file.separator")+mapName+".gif";
	}

	public void reloadLobby() {
		send("action=getlobbyinfo:playername="+FileHandler.getInstance().getSettings().get("profile.name"));
	}
	
	public void updateLobby(Map<String, String> args) {
		this.isHost = args.get("ishost").equals("true");
		this.playerAmount = Integer.parseInt(args.get("amountofplayers"));
		this.maxPlayerAmount = Integer.parseInt(args.get("maxamountofplayers"));
		this.players = args.get("players").split(":");
	}

	public boolean allPlayersReadyToLoad() {
		return this.allPlayersReadyToLoad ;
	}
	
	public void setAllPlayersReadyToLoad(boolean b) {
		this.allPlayersReadyToLoad = b;
	}
	
	public boolean allPlayersReadyToStart() {
		return this.allPlayersReadyToStart ;
	}
	
	public void setAllPlayersReadyToStart(boolean b) {
		this.allPlayersReadyToStart = b;
	}

	public void disconnect() {
		send("action=clientdisconnect");
		ClientThread.getInstance().close();
	}

	public String getMapName() {
		return this.mapName;
	}

	public boolean isHost() {
		return this.isHost;
	}

	public String getMapDescription() {
		return this.mapDescription;
	}

	public String[] getPlayers() {
		return this.players;
	}

	public int getPlayerAmount() {
		return this.playerAmount;
	}

	public int getMaxPlayerAmount() {
		return this.maxPlayerAmount;
	}


	public void downloadMap() {
		this.setMapStringIsFinished(false);
		this.setMapString("");
		send("action=getMapString:filename="+mapName+".map:path=maps"+System.getProperty("file.separator")+mapName+".map");
	}


	public synchronized boolean isMapLoaded() {
		return this.mapStringIsReady;
	}


	public static PreGameManager getInstance() {
		return instance;
	}
	
	public static void init() {
		if (instance == null)
		setInstance(new PreGameManager());
	}

	private static void setInstance(PreGameManager preGameManager) {
		instance = preGameManager;
	}
	
}
