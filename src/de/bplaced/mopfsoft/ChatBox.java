package de.bplaced.mopfsoft;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

public class ChatBox {

	private int x;
	private int y;
	private Color color;
	private TextField textField;
	private ConcurrentLinkedQueue <String> chatHistory = new ConcurrentLinkedQueue<String>();
	private int height;
	private final ClientThread client;

	public ChatBox(ClientThread client, GUIContext c, Font font, Color color, int x, int y, int height, int width) {
		this.client = client;
		this.x = x;
		this.y = y;
		this.height = height;
		this.color = color;
		this.textField = new TextField(c, font, x, y+height-32, width, 32) {
			@Override
			public void keyPressed(int key, char arg1) {
				super.keyPressed(key, arg1);
				if (key == 28) {
					//Enter
					String message = textField.getText().replaceAll(":", "").replaceAll("=", "");
					if (!message.equals(""))
					ChatBox.this.client.send("action=playerchat:message="+message);
					textField.setText("");
				} else 
				if (key == 1) {
					//ESC
					ChatBox.this.textField.setFocus(false);
				}
			}
		};
		this.textField.setTextColor(color);
	}
	
	public void addNewMessage(String message, String sender) {
		chatHistory.add(""+sender+": "+message);
	}
	
	public void draw(GUIContext c, Graphics g) {
		g.setColor(color);
		int amount = chatHistory.size();
		int maxAmount = (height-40)/15;
		int i = 0;
		for (String chatMessage: chatHistory) {
			if (y+(i*15)+(15*(maxAmount-amount))>=y) {
			g.drawString(chatMessage, x, y+(i*15)+(15*(maxAmount-amount)));
			}
			i++;
		}
		
		textField.render(c, g);
	}
}
