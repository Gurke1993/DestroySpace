package de.bplaced.mopfsoft.handler;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.material.Material;
import de.bplaced.mopfsoft.message.ClientDisconnect;
import de.bplaced.mopfsoft.message.GetFileTransferInfo;
import de.bplaced.mopfsoft.message.GetLobbyInfo;
import de.bplaced.mopfsoft.message.GetMapString;
import de.bplaced.mopfsoft.message.LobbyReady;
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
		ClientThread.getInstance().send(new GetFileTransferInfo(new File("maps"+System.getProperty("file.separator")+mapName+".gif"))+"");
		
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
		ClientThread.getInstance().send(new LobbyReady("Start", isReady)+"");
	}
	
	public void setClientIsReadyToLoad(boolean isReady) {
		Log.debug("Client is ready to load: " + isReady);
		ClientThread.getInstance().send(new LobbyReady("Load", isReady)+"");
	}


	public void loadUpGame() {
		GameHandler.getInstance().enterState(7);
	}
	
	public void startGame() {
		Log.info("Going to MultiplayerGameScreen...");
		for (Entry <Shape,Material> entry: MultiplayerGameManager.getInstance().getMap().getEnvironment().entrySet()) {
			for (float point: entry.getKey().getPoints()) {
				Log.debug(point+"");
			}
			Log.debug(":"+entry.getValue().getMid());
		}
		GameHandler.getInstance().enterState(6);
	}

	public String getPreviewImagePath() {
		return "maps"+System.getProperty("file.separator")+mapName+".gif";
	}

	public void reloadLobby() {
		ClientThread.getInstance().send(new GetLobbyInfo(FileHandler.getInstance().getSettings().get("string.profile.name"))+"");
	}
	
	public void updateLobby(Map<String, String> args) {
		this.isHost = args.get("IsHost").equals("true");
		this.playerAmount = Integer.parseInt(args.get("Count"));
		this.maxPlayerAmount = Integer.parseInt(args.get("MaxCount"));
		this.players = args.get("Players").split(":");
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
		ClientThread.getInstance().send(new ClientDisconnect()+"");
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
		ClientThread.getInstance().send(new GetMapString(new File("maps"+System.getProperty("file.separator")+mapName+".map"))+"");
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
