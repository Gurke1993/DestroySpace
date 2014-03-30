package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.PreGameManager;

public class LobbyLoadGame extends Message implements ExecutableClient {

	public LobbyLoadGame() {
		super("Class=LobbyLoadGame");
	}
	
	public LobbyLoadGame(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		PreGameManager.getInstance().loadUpGame();
	}

}
