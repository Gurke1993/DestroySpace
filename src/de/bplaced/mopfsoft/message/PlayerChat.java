package de.bplaced.mopfsoft.message;

import java.util.Map;

import de.bplaced.mopfsoft.handler.ChatManager;

public class PlayerChat extends Message implements ExecutableClient {

	public PlayerChat(Map<String, String> args) {
		super(args);
	}
	
	public PlayerChat(String message) {
		super("Class=PlayerChat:Message="+message);
	}

	@Override
	public void execute() {
		ChatManager.getInstance().addNewMessage(args.get("Message"),args.get("Player"));
	}

}
