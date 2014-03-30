package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.PreGameManager;

public class StartGame extends Message implements ExecutableClient {

	public StartGame(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		PreGameManager.getInstance().startGame();
	}

}
