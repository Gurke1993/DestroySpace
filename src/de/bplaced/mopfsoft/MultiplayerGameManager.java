package de.bplaced.mopfsoft;



import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;




public class MultiplayerGameManager {

	private static final int loopTime = 50;
	private DrawableMap map;
	private Queue<ServerUpdate> serverUpdateQueue = new LinkedList<ServerUpdate>();
	@SuppressWarnings("unused")
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
	 * 
	 */
	public void doGameLoop(GameContainer container, StateBasedGame sbg, int timePassed) {
		//GameLoop for Multiplayer
		
		long startTime = System.currentTimeMillis();
		
		//Update map according to server messages
		ServerUpdate serverUpdate;
		Map <String,String> args;
		while ((serverUpdate = serverUpdateQueue.poll()) != null) {
			args = serverUpdate.getArgs();
			if (args.get("type").equals("gamefieldupdate")) {
				map.updateBlock(Integer.parseInt(args.get("x")), Integer.parseInt(args.get("y")), Integer.parseInt(args.get("bid")));
			} else
			if (args.get("type").equals("entityupdate")) {
				String[] entitySplit = args.get("entity").split(",");
				map.getEntitys().get(Integer.parseInt(entitySplit[0])).move(Integer.parseInt(entitySplit[2]), Integer.parseInt(entitySplit[3]));
			}
		}
		
		//Wait if to fast
		try {
			Thread.sleep(Math.max(loopTime-(System.currentTimeMillis()+timePassed-startTime), 0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setMap(String path) {
		this.map = new DrawableMap(path);
	}


	public DrawableMap getMap() {
		return this.map;
	}
	
	public void queueServerUpdate(Map<String, String> args) {
		System.out.println("Got new server update... adding to stack...");
		this.serverUpdateQueue.add(new ServerUpdate(args));
		
	}
}
