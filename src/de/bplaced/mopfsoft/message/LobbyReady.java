package de.bplaced.mopfsoft.message;


public class LobbyReady extends Message{

	public LobbyReady(String forWhat, boolean ready) {
		super("Class=LobbyReady:ForWhat="+forWhat+":Ready="+ready);
	}


}
