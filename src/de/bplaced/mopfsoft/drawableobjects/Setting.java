package de.bplaced.mopfsoft.drawableobjects;

import java.util.Map.Entry;

@Deprecated
public class Setting implements Entry<String,String>{
	
	public Setting(String kind, String name, String value) {
		this.key = kind+"."+name;
		this.value = value;
	}
	
	private String key;
	private String value;
	
	public String setValue(String value) {
		String vOld = this.value;
		this.value = value;
		return vOld;
	}
	public String getValue() {
		return value;
	}
	public String getKind() {
		return key.split("\\.")[0];
	}
	public String getName() {
		return key.split(".")[1];
	}

@Override
public String getKey() {
	return key;
}
}
