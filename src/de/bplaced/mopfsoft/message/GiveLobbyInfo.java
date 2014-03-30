package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.PreGameManager;

public class GiveLobbyInfo extends Message implements ExecutableClient  {

	public GiveLobbyInfo(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		PreGameManager.getInstance().setLobbyInformation(args.get("MapName"), args.get("MapDescription"), Integer.parseInt(args.get("Count")), Integer.parseInt(args.get("MaxCount")), args.get("Players").split(","), args.get("IsHost").equals("true"));
	}

}
