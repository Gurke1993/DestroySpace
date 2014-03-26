package de.bplaced.mopfsoft.Network;

import java.util.Map;

public class ServerUpdate {
	private Map<String, String> args;

	public ServerUpdate(Map<String,String> args) {
		this.args = args;
		}

	public String getType() {
		return args.get("type");
	}
	
	public Map<String,String> getArgs() {
		return this.args;
	}
}
