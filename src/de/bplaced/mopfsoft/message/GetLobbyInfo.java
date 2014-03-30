package de.bplaced.mopfsoft.message;


public class GetLobbyInfo extends Message{

	public GetLobbyInfo(String playerName) {
		super("Class=GetLobbyInfo:PlayerName="+playerName);
	}


}
