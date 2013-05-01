package de.bplaced.mopfsoft.entitys;

import org.newdawn.slick.Image;

public abstract class Entity {
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private int y;
	private int x;
	
	public abstract int getId();
	public abstract Image getImage();
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}
