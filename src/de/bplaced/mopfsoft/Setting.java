package de.bplaced.mopfsoft;

import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

public class Setting {

	private String name;
	private TextField textField;
	private int x;
	private int y;
	private Color color;

	public Setting(GUIContext c, Entry<String, String> entry, Font font, Color color, int x, int y) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.name = entry.getKey();
		this.textField = new TextField(c, font, 0, 0, 200, 32);
		this.textField.setTextColor(color);
		this.textField.setText(entry.getValue());
		textField.setLocation(x+200, y);
		textField.setCursorPos(entry.getValue().length());
	}
	
	public void draw(GUIContext c, Graphics g) {
		g.setColor(color);
		g.drawString(name, x, y);
		textField.render(c, g);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getValue() {
		return this.textField.getText();
	}

}
