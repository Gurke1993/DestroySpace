package de.bplaced.mopfsoft.editor;

import de.bplaced.mopfsoft.drawableobjects.DrawableMap;

public class EditorPaintFunction {
	DrawableMap drawableMap;

	public EditorPaintFunction(DrawableMap drawableMap) {
		this.drawableMap = drawableMap;

	}

	public void drawCircle(int xPos, int yPos, int radius, int id) {
		int x = 0;
		int y = radius;
		int dE = 1;
		int dSE = 2 - radius - radius;

		if (xPos + radius > 0 && xPos + radius < drawableMap.getWidth()&& yPos + radius > 0 && yPos + radius < drawableMap.getHeight()) {
			drawableMap.updateBlock(0 + xPos, radius + yPos, id);
			drawableMap.updateBlock(radius + xPos, 0 + yPos, id);
			drawableMap.updateBlock(0 + xPos, -radius + yPos, id);
			drawableMap.updateBlock(-radius + xPos, 0 + yPos, id);

			int F = 1 - radius;
			while (x < y) {
				if (F < 0) {
					F = F + dE;
				} else {
					F = F + dSE;
					y = y - 1;
					dSE = dSE + 4;
				}
				x = x + 1;
				dE = dE + 2;

				drawableMap.updateBlock(x + xPos, y + yPos, id);
				drawableMap.updateBlock(-x + xPos, y + yPos, id);
				drawableMap.updateBlock(-y + xPos, x + yPos, id);
				drawableMap.updateBlock(-y + xPos, -x + yPos, id);
				drawableMap.updateBlock(y + xPos, x + yPos, id);
				drawableMap.updateBlock(y + xPos, -x + yPos, id);
				drawableMap.updateBlock(x + xPos, -y + yPos, id);
				drawableMap.updateBlock(-x + xPos, -y + yPos, id);
			}
		}
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
	}
	public void drawLine( int x0, int y0, int x1, int y1, int blockId) {
		boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
	    if(steep)
	    {//swap variable
				x0=x0+y0; 
				y0=x0-y0; 
				x0=x0-y0; 			
	        
				x1=x1+y1; 
				y1=x1-y1; 
				x1=x1-y1;
	    }
	    if(x0 > x1)
	    {//swap variable
	        x0=x0+x1; 
			x1=x0-x1; 
			x0=x0-x1;
	        
	        y1=y1+y0; 
			y0=y1-y0; 
			y1=y1-y0;        
	    }    
	    int deltax = x1 - x0;
	    int deltay = Math.abs(y1 - y0);
	    int error = -deltax / 2;
	    int ystep;
	    int y = y0;
	    
	    if(y0 < y1)
	    {
	        ystep = 1;
	    }
	    else
	    {
	        ystep = -1;
	    }
	    for( ; x0<=x1; x0++)
	    {
	        if(steep)
	        {
	        	if(drawableMap.isInMap(y, x0)){
	        	drawableMap.updateBlock(y , x0, blockId);}
	        }
	        else
	        {
	        	if(drawableMap.isInMap(x0, y)){
	        	drawableMap.updateBlock(x0, y , blockId);}
	        }
	        error = error + deltay;
	        if(error > 0)
	        {
	            y = y + ystep;
	            error = error - deltax;
	        }
	    }
	}
}
