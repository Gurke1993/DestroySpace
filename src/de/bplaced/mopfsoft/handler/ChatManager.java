package de.bplaced.mopfsoft.handler;

import java.util.concurrent.ConcurrentLinkedQueue;

import de.bplaced.mopfsoft.message.PlayerChat;
import de.bplaced.mopfsoft.network.ClientThread;

public class ChatManager {

	private static ChatManager instance = null;
	public ChatManager(ClientThread sender) {
	}
	
	public void send(String message) {
		ClientThread.getInstance().send(new PlayerChat(message)+"");
	}
	
	public void addNewMessage(String message, String sender) {
		chatHistory.add(""+sender+": "+message);
	}
	
	private ConcurrentLinkedQueue <String> chatHistory = new ConcurrentLinkedQueue<String>();
	
	public ConcurrentLinkedQueue <String> getChatHistory() {
		return this.chatHistory;
	}

	public static ChatManager getInstance() {
		return instance;
	}
	
	public static void setInstance(ChatManager chatManager) {
		instance = chatManager;
	}
	
	public static void init(ClientThread clientThread) {
		if (instance == null)
		setInstance(new ChatManager(clientThread));
	}

}
