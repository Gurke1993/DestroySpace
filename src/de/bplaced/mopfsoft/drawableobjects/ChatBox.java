package de.bplaced.mopfsoft.drawableobjects;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

import de.bplaced.mopfsoft.handler.ChatManager;

public class ChatBox extends DrawableObject{

	private Color color;
	private TextField textField;

	public ChatBox(GUIContext c, Font font, Color color, int x, int y, int height, int width) {
		super(x, y, width, height, null);
		this.color = color;
		this.textField = new TextField(c, font, x, y+height-32, width, 32) {
			@Override
			public void keyPressed(int key, char arg1) {
				super.keyPressed(key, arg1);
				if (key == 28) {
					//Enter
					String message = textField.getText().replaceAll(":", "").replaceAll("=", "");
					if (!message.equals(""))
						ChatManager.getInstance().send(message);
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
	

	
	public void draw(GUIContext c, Graphics g) {
		g.setColor(color);
		ConcurrentLinkedQueue<String> ch = ChatManager.getInstance().getChatHistory();
		int amount = ch.size();
		int maxAmount = (height-40)/15;
		int i = 0;
		for (String chatMessage: ch) {
			if (y+(i*15)+(15*(maxAmount-amount))>=y) {
			g.drawString(chatMessage, x, y+(i*15)+(15*(maxAmount-amount)));
			}
			i++;
		}
		
		textField.render(c, g);
	}



	public void setFocus(boolean b) {
		textField.setFocus(b);
	}
}
