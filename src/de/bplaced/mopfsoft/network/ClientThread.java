package de.bplaced.mopfsoft.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.handler.ChatManager;
import de.bplaced.mopfsoft.message.CloseServer;
import de.bplaced.mopfsoft.message.ExecutableClient;
import de.bplaced.mopfsoft.message.Message;



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
			Log.warn("Lost connection to server... terminating Connection...");
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
	    	Log.error(e);
	    }
	  } 
	  
	  public void close() {
		  try {
			  mainS.close();
		} catch (IOException e) {
			Log.error(e);
		}
	  }

	public void closeByClient() {
		send(new CloseServer()+"");
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
		Log.debug("ServerSays:"+message);
		
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
		((ExecutableClient)Message.getMessage(args)).execute();
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
		Log.info("Connecting to server...");
		try {
			ClientThread.init(ip, port);
			ChatManager.init(ClientThread.getInstance());
			ClientFileTransferThread.init(ip, 27016);
			Log.info("Connected successfully to "+ip+":"+port+"!");
			return true;
		} catch (IOException e) {
			Log.error("Could not connect...");
			return false;
		}
	}
}
