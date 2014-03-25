package de.bplaced.mopfsoft;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;



public class ClientThread extends Thread{
	private static ClientThread instance = null;
	
	Socket mainS;
	  DataOutputStream out;
	  DataInputStream in;

	  private ClientThread(String ip, int mainPort) throws IOException {
	      mainS = new Socket(ip,mainPort);
	      in = new DataInputStream(mainS.getInputStream()) ;
	      out = new DataOutputStream(mainS.getOutputStream()) ;
	    this.start();
	  }

	  public void run() {
		String text;
		try {
			// Normal Mode
			while ((text = in.readUTF()) != null) {
				analyseServerMessage(text);
			}
		} catch (IOException e) {
			System.out.println("Lost connection to server... terminating");
		}
		try {
		    mainS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	  }


	public void send(String message) {
	    try {
	      out.writeUTF(message);
	    }
	    catch(IOException e) {
	      e.printStackTrace();
	    }
	  }
	  
	  public void close() {
		  try {
			  mainS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }

	public void closeByClient() {
		send("action=closeserver");
	}

	public static ClientThread getInstance() {
		return instance ;
	}
	
	private static void setInstance(ClientThread clientThread) {
		instance = clientThread;
	}
	
	public static void init(String ip, int mainPort) throws IOException {
		  setInstance(new ClientThread(ip, mainPort)); 
	}
	
	/**
	 * processes messages from the server
	 * @param message : message to analyze
	 */
	public synchronized void analyseServerMessage(String message) {
		if (!message.contains("givemapstring:partofstring"))
		System.out.println("ServerSays:"+message);
		
		//Structure message
		Map<String,String> args= new HashMap<String,String>();
		
		String[] argArray;
		for (String split: message.split(":")) {
			argArray = split.split("=");
			if (argArray.length<2)
				args.put(argArray[0], "");
			else
				args.put(argArray[0], argArray[1]);
		}
		
		analyseServerMessage(args);
	}
	
	/** processes messages from the server
	 * @param args
	 */
	private void analyseServerMessage(Map<String,String> args) {
		
		String action = args.get("action");
		
		if (action.equals("gamechange")) {
			MultiplayerGameManager.getInstance().queueServerUpdate(args);
		} else
		
		if (action.equals("playerchat")) {
			ChatManager.getInstance().addNewMessage(args.get("message"),args.get("player"));
		} else
				
		if (action.equals("givefiletransferinfo")) {
			ClientFileTransferThread.getInstance().prepareForNewFileTransfer(new File(args.get("path")), Long.parseLong(args.get("filelength")));
		} else
			
		if (action.equals("allplayersready")) {
			if (args.get("type").equals("load")) {
				PreGameManager.getInstance().setAllPlayersReadyToLoad(Boolean.parseBoolean(args.get("areready")));
			} else
			if (args.get("type").equals("start")) {
				PreGameManager.getInstance().setAllPlayersReadyToStart(Boolean.parseBoolean(args.get("areready")));
			}
		} else
			
		if (action.equals("givelobbyinfo")) {
			PreGameManager.getInstance().setLobbyInformation(args.get("mapname"), args.get("mapdescription"), Integer.parseInt(args.get("amountofplayers")), Integer.parseInt(args.get("maxamountofplayers")), args.get("players").split(","), args.get("ishost").equals("true"));
		} else 
			
		if (action.equals("loadupgame")) {
			PreGameManager.getInstance().loadUpGame();
		} else  
		if (action.equals("startgame")) {
			PreGameManager.getInstance().startGame();
		} else
		
		if (action.equals("givemapstring")) {
			if (args.get("finished").equals("false"))
				PreGameManager.getInstance().addToMapString(args.get("partofstring"));
			else
				PreGameManager.getInstance().setMapStringIsFinished(true);
		} else 
			
		if (action.equals("mapchange")) {
			PreGameManager.getInstance().reloadLobby();
		} else

		if (action.equals("playerchange")) {
			PreGameManager.getInstance().updateLobby(args);
		}
	}
	
	
	/** 
	 * @return
	 * Client is connected? true/false
	 */
	public Boolean isConnected() {
		return ClientThread.getInstance() != null && ClientFileTransferThread.getInstance() != null;
	}
	
	/**
	 * connects the client to the server
	 * @param ip: server IP
	 * @param port: server ort
	 * @return if succsesfully true/false
	 */
	public static Boolean connectToServer(String ip, Integer port) {
		System.out.println("Trying to connect...");
		try {
			ClientThread.init(ip, port);
			ChatManager.init(ClientThread.getInstance());
			ClientFileTransferThread.init(ip, 27016);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
