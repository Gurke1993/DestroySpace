package de.bplaced.mopfsoft.editor;

import de.bplaced.mopfsoft.drawableobjects.DrawableMap;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;

public class EditorPaintFunction {
	DrawableMap drawableMap;
	//Shapes
	Circle c;
	Line l;
	
	
	
	
	public EditorPaintFunction(DrawableMap drawableMap) {
		this.drawableMap = drawableMap;
		c = new Circle(0, 0, 0);
		l = new Line(0, 0,0,0);
	}

	public void drawCircle(int xPos, int yPos, int radius, int id) {
		
		//c.setLocation(xPos, yPos);
		c.setCenterX(xPos);
		c.setCenterY(yPos);
		c.setRadius(radius);
		
		drawableMap.updateBlocks(c, id);



			
		
	}

	public void paint(  int x, int y, int radius, int blockId) {	
		for (int i=radius; i >= 0 ; i--)
		{
			drawCircle( x, y, i , blockId);
		}
	}
	
	
	public void delete( int x, int y, int radius) {	

			for (int i=radius; i >= 0 ; i--)
			{
				drawCircle(x, y, i , 0);
			}
	}
	
	//TODO //bessere Methode finden
	/*
	public void fill( int x, int y, int blockId,int idToOverwrite) {
		if(idToOverwrite != blockId)
		{
		if (drawableMap.isInMap(x, y)) {
			if (drawableMap.getBlock(x, y).getBid() == idToOverwrite) {

				drawableMap.updateBlock(x, y, blockId);
				

				fill(x, y + 1, blockId, idToOverwrite);
				fill(x, y - 1, blockId, idToOverwrite);
				fill(x - 1, y, blockId, idToOverwrite);
				fill(x + 1, y, blockId, idToOverwrite);
			
				}
			}
		}
	} */
	
	public void drawLine( int x0, int y0, int x1, int y1, int blockId) {
		l.set(x0, y0, x1, y1);	
		drawableMap.updateBlocks(l, blockId);			    
	}
}
