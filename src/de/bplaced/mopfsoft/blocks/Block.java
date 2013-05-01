package de.bplaced.mopfsoft.blocks;

import org.newdawn.slick.Color;

import de.bplaced.mopfsoft.items.Item;

public abstract class Block {
	
	
	public abstract int getStrength(Item item);
	
	public abstract int getDrop();
	
	public abstract Color getColor();
	
	public abstract int getId();
}
