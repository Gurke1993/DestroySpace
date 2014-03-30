package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.MultiplayerGameManager;

public class GameEntityChange extends Message implements ExecutableClient {

	public GameEntityChange(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		MultiplayerGameManager.getInstance().queueServerUpdate(this);
	}

}
