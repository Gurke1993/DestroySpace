package de.bplaced.mopfsoft;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatManager {

	private ClientThread sender;
	public ChatManager(ClientThread sender) {
		this.sender = sender;
	}
	
	public void send(String message) {
		sender.send("action=playerchat:message="+message);
	}
	
	public void addNewMessage(String message, String sender) {
		chatHistory.add(""+sender+": "+message);
	}
	
	private ConcurrentLinkedQueue <String> chatHistory = new ConcurrentLinkedQueue<String>();
	
	public ConcurrentLinkedQueue <String> getChatHistory() {
		return this.chatHistory;
	}

}
