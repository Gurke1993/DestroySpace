package de.bplaced.mopfsoft;



import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;




public class MultiplayerGameManager {

	private static final int loopTime = 50;
	private DrawableMap map;
	private Queue<ServerUpdate> serverUpdateQueue = new LinkedList<ServerUpdate>();
	private ClientThread sender;

	public MultiplayerGameManager(ClientThread sender) {
		this.sender = sender;
	}
	
	
	/**
	 * Runs the GameLoop on client side. 
	 * This should only be called by the GameState update 
	 * method as it requires an OpenGL enviroment.
	 * 
	 * @param timePassed 
	 * @param sbg the state based game
	 * @param container the game container
	 * @param usedKeys 
	 * 
	 */
	public void doGameLoop(GameContainer container, StateBasedGame sbg, int timePassed, ConcurrentLinkedQueue<String> usedKeys) {
		//GameLoop for Multiplayer
		
		long startTime = System.currentTimeMillis();
		
		//Update map according to server messages
		ServerUpdate serverUpdate;
		Map <String,String> args;
		while ((serverUpdate = serverUpdateQueue.poll()) != null) {
			args = serverUpdate.getArgs();
			if (args.get("type").equals("gamefieldchange")) {
				map.updateBlock(Integer.parseInt(args.get("x")), Integer.parseInt(args.get("y")), Integer.parseInt(args.get("bid")));
			} else
			if (args.get("type").equals("entitychange")) {
				String[] entitySplit = args.get("entity").split(",");
				map.getEntitys().get(Integer.parseInt(entitySplit[0])).set(Integer.parseInt(entitySplit[2]), Integer.parseInt(entitySplit[3]));
			}
		}
		
		String move=null, use = null;
		//Process player input
		for (String key: usedKeys) {
			if (key.contains("type=move")) {
				move= key;
			}
			if (key.contains("type=use")) {
				use= key;
			}
		}
		
		if (move != null || use != null) {
			if (move != null && use == null) {
				sender.send("action=clientupdate:"+move);
			} else
			if (use != null && move == null) {
				sender.send("action=clientupdate:"+use);
			} else {
				sender.send("action=clientupdate:type=moveanduse:"+move.split(":",2)[1]+":"+use.split(":",2)[1]);
			}
		}
		
		//Wait if to fast
		try {
			Thread.sleep(Math.max(loopTime-(System.currentTimeMillis()+timePassed-startTime), 0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setMap(String mapString, String previewImagePath) {
		this.map = new DrawableMap(mapString,previewImagePath);
	}


	public DrawableMap getMap() {
		return this.map;
	}
	
	public void queueServerUpdate(Map<String, String> args) {
		this.serverUpdateQueue.add(new ServerUpdate(args));
		
	}
}
