package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.PreGameManager;

public class LobbyAllReady extends Message implements ExecutableClient {

	public LobbyAllReady(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		if (args.get("ForWhat").equals("Load")) {
			PreGameManager.getInstance().setAllPlayersReadyToLoad(Boolean.parseBoolean(args.get("Ready")));
		} else
		if (args.get("ForWhat").equals("Start")) {
			PreGameManager.getInstance().setAllPlayersReadyToStart(Boolean.parseBoolean(args.get("Ready")));
			
			if (Boolean.parseBoolean(args.get("Ready"))) {
				PreGameManager.getInstance().startGame();
			}
		}
	}

}
