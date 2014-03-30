package de.bplaced.mopfsoft.message;


public class GamePlayerInteraction extends Message{

	public GamePlayerInteraction(String action) {
		super("Class=GamePlayerInteraction:"+action);
	}
}
