package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.PreGameManager;

public class GiveMapString extends Message implements ExecutableClient {

	public GiveMapString(Map<String, String> args) {
		super(args);
	}

	@Override
	public void execute() {
		if (Boolean.parseBoolean(args.get("Finished")))
				PreGameManager.getInstance().setMapStringIsFinished(true);
		else
			PreGameManager.getInstance().addToMapString(args.get("Line"));
	}

}
