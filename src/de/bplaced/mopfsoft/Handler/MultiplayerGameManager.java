package de.bplaced.mopfsoft.handler;



import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.drawableobjects.DrawableMap;
import de.bplaced.mopfsoft.entitys.Entity;
import de.bplaced.mopfsoft.entitys.ItemUser;
import de.bplaced.mopfsoft.network.ClientThread;
import de.bplaced.mopfsoft.network.ServerUpdate;



public class MultiplayerGameManager {

	private static final int loopTime = 50;
	private static MultiplayerGameManager instance = null;
	private DrawableMap map;
	private ConcurrentLinkedQueue<ServerUpdate> serverUpdateQueue = new ConcurrentLinkedQueue<ServerUpdate>();

	private MultiplayerGameManager() {
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
				
				String[] entitySplit = args.get("entity").split(";")[0].split(",");
				Entity entity = map.getEntitys().get(Integer.parseInt(entitySplit[0]));
				
				//General Entitys
				entity.set(Integer.parseInt(entitySplit[2]), Integer.parseInt(entitySplit[3]), entitySplit[4]);
				
				//ItemUsers
				if (entity instanceof ItemUser) {
					((ItemUser)entity).setItems(args.get("entity").split(";")[1].split(","));
				}
				
			}
		}
		
		String move=null, use = null, jump = null;
		//Process player input
		for (String key: usedKeys) {
			Log.debug(key);
			if (key.contains("type=move")) {
				move= key;
			}
			if (key.contains("type=use")) {
				use= key;
			}
			if (key.contains("type=jump")) {
				jump = key;
			}
		}

		if (jump != null) {
			ClientThread.getInstance().send("action=clientupdate:"+jump);
		} else
			
		if (move != null || use != null) {
			if (move != null && use == null) {
				ClientThread.getInstance().send("action=clientupdate:"+move);
			} else
			if (use != null && move == null) {
				ClientThread.getInstance().send("action=clientupdate:"+use);
			} else {
				ClientThread.getInstance().send("action=clientupdate:type=moveanduse:"+move.split(":",2)[1]+":"+use.split(":",2)[1]);
			}
		}
		
		
		//Wait if to fast
		try {
			Thread.sleep(Math.max(loopTime-(System.currentTimeMillis()+timePassed-startTime), 0));
		} catch (InterruptedException e) {
			Log.error(e);
		}
	}

	public void setMap() {
		this.map = new DrawableMap(PreGameManager.getInstance().getMapString(),PreGameManager.getInstance().getPreviewImagePath());
	}


	public DrawableMap getMap() {
		return this.map;
	}
	
	public void queueServerUpdate(Map<String, String> args) {
		this.serverUpdateQueue.add(new ServerUpdate(args));
		
	}


	public static MultiplayerGameManager getInstance() {
		return instance ;
	}
	
	public static void init() {
		if (instance == null)
		setInstance(new MultiplayerGameManager());
	}


	private static void setInstance(MultiplayerGameManager multiplayerGameManager) {
		instance = multiplayerGameManager;
	}
}
