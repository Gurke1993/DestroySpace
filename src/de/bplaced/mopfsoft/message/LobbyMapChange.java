package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.PreGameManager;

public class LobbyMapChange extends Message implements ExecutableClient {

	public LobbyMapChange(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		PreGameManager.getInstance().reloadLobby();
	}

}
