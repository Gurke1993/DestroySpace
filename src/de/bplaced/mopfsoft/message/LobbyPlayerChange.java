package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.PreGameManager;

public class LobbyPlayerChange extends Message implements ExecutableClient{

	public LobbyPlayerChange(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		PreGameManager.getInstance().updateLobby(args);
	}

}
