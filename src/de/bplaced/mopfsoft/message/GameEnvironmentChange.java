package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.MultiplayerGameManager;

public class GameEnvironmentChange extends Message implements ExecutableClient {

	public GameEnvironmentChange(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		MultiplayerGameManager.getInstance().queueServerUpdate(this);
	}

}
