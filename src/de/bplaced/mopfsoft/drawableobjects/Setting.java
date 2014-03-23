package de.bplaced.mopfsoft.drawableobjects;

public class Setting{
	
	public Setting(String name, String value, String kind) {
		this.name = name;
		this.value = value;
		this.kind = kind;
	}
	
	private String name;
	private String value;
	private String kind;
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public String getKind() {
		return kind;
	}
@Override
public boolean equals(Object obj) {
	if (obj instanceof Setting && ((Setting) obj).getKind().equals(this.kind) && ((Setting) obj).getName().equals(this.name))
	return true;
	return false;
	
}
}
