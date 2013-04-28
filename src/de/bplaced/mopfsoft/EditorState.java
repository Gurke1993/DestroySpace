package de.bplaced.mopfsoft;



import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class EditorState extends BasicGameState{
	public static final int ID = 2;	
	private int[][] pitchArray;
	private Image test1,test2,hud,mapImg;
	private Graphics mapImgG;
	private  StateBasedGame stateBasedGame ;
	
	//Toolsvalues
	private boolean filled;
	private int fontSize;
	private int tool;//O Pencil //1 Rubber //2 Fill //3 Line //4 Rectangle //5 Circle
	private int shapeX1,shapeX2,shapeY1,shapeY2;
	
	//NEW TEST
	private Image baseImage;
	private Image cutoutImage;
	private int x,y;
	private Image outputImage;
	
	//localImgG.copyArea(img, 0, 0);
	///////////////////////////////////////////TESTCOMMENT
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		this.stateBasedGame = stateBasedGame;

		test1 = new Image("resources/images/test1.png");
		test2 = new Image("resources/images/test2.png");
		hud = new Image("resources/images/editor/Hud.png");		
		pitchArray = new int[200][300]; //[x][y]
		mapImg= Image.createOffscreenImage(200, 200);
		mapImgG = mapImg.getGraphics();
		
		
		//NEW
		baseImage = new Image("resources/images/editor/testBg.png");
		cutoutImage = new Image("resources/images/editor/boom.png");

		outputImage = new Image(cutoutImage.getWidth(), cutoutImage.getHeight());
		x=0;
		y=0;
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		
		/*
		//Grapics: Map
	    for (int i = 0; i<pitchArray.length; i++) {
	    	for (int j = 0; j < pitchArray[0].length; j++){ 
	    		if (pitchArray[i][j]==0)
	    		{
	    		graphics.drawImage(test1, i*4+100, j*4+100);
	    		//mapImgG.copyArea(test1, i*4, j*4);
	    		}
	    		else if (pitchArray[i][j]==1)
	    		{
	    		graphics.drawImage(test2, i*4+100, j*4+100);
	    		//mapImgG.copyArea(test2, i*4, j*4);
	    		}
	    	}
	    }
	    
	    //mapImgG.flush();
	//    graphics.drawImage(mapImgG,100,100);
	   
	    
	    //Graphics: Hud
		graphics.drawImage(hud,0,0);
	    //Graphics: Coordinates
	    graphics.drawString(shapeX1+" "+shapeY1+" "+shapeX2+" "+shapeY2+" ", 360, 10);
	    graphics.drawString("Tool: " + tool, 600, 10);
	    //Graphics filled?
	    graphics.drawString(""+filled, 63, 430);
	    
	    */               //OLD
		
		 graphics.drawImage(baseImage, x, y);
	        graphics.drawImage(outputImage, x, baseImage.getHeight());
		
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int timePassed)
			throws SlickException {
		Input input = gameContainer.getInput();
		  
        if(input.isKeyDown(Input.KEY_A))
        {
        	x -= 1;
        }
  
        if(input.isKeyDown(Input.KEY_D))
        {
        	x += 1;
        }
  
        if(input.isKeyDown(Input.KEY_W))
        {
        	y -= 1;
        }
        if(input.isKeyDown(Input.KEY_S))
        {
        	y += 1;
        }
        Graphics g = gameContainer.getGraphics();
        
        g.clear();
        g.drawImage(cutoutImage, 0.0f, 0.0f);
        g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
        g.drawImage(baseImage, 0.0f, 0.0f);

        //save the result to the outputImage
        g.copyArea(outputImage, 0, 0);

        //reset the graphics
        g.setDrawMode(Graphics.MODE_NORMAL);
        g.clear();
        	
        
		
	}

	@Override
	public int getID() {
		return ID;
	}
	
	@Override
	public void keyPressed(int key, char c) {
		System.out.println(key);
		switch (key) {
			case 1 : {
				stateBasedGame.enterState(1);
			}
		}
		
	}
	@Override
	public void mousePressed(int button, int x, int y) {
		System.out.println("Button pressed: "+button+" at "+x+" "+y);
		if (button == 0) {
			if( x > 22 && x <81) {
				if (y >99 && y < 135)
				{
					tool=0;
				}
				else if (y >139 && y < 172)
				{
					tool=1;
				}
				else if (y >177 && y < 210)
				{
					tool=2;
				}
				else if (y >215 && y < 247)
				{
					tool=3;
				}
				else if (y >254 && y < 285)//Rectangle Select
				{
					tool=4;
				}
				else if (y >290 && y < 324)
				{
					tool=5;
				}			
			}
			else if (x > 100 && y > 100)
			{
				//Rectangle
				if (tool==4)
				{
					if (shapeX1 == 0 && shapeY1 == 0)
					{
						shapeX1=(int)(x - 99) / 4;
						shapeY1=(int)(y - 99) / 4;
					}
					else 
					{
						shapeX2=(int)(x - 99) / 4;
						shapeY2=(int)(y - 99) / 4;
						pitchArray = drawRect(pitchArray,shapeX1,shapeY1,shapeX2,shapeY2,1, 1, filled);
						shapeX1=0;
						shapeX2=0;
						shapeY1=0;
						shapeY2=0;						
					}
				}
				else if (tool == 2)
				{
					fill (pitchArray,(x-99)/4,(y-99)/4,1);					
				}
			}
			//filled?
			else if (y > 443 && y < 470)
			{
				if (x > 12 && x < 40)
				{
					System.out.println("button1");
					filled =false;
				}
				else if (x > 53 && x < 83)
				{
					System.out.println("button2");
					filled = true;
				}
			}			
		}
	}
	
	private int [][] paint(int[][] array, int x, int y, int radius, int type) {
		//TODO
		
		return array;
	}
	
	private void delete(int[][] array, int x, int y, int radius) {
		//TODO
	}
	
	private void fill(int[][] array, int x, int y, int type) {
		int fillType;
		fillType=array[x][y];
		array[x][y] = type;
		if (array [x-1][y] == fillType) {fill( array ,x-1,y, type);}
		if (array [x+1][y] == fillType) {fill( array ,x+1,y, type);}
		if (array [x][y-1] == fillType) {fill( array ,x,y-1, type);}
		if (array [x][y+1] == fillType) {fill( array ,x,y+1, type);}	
	}
	
	private int[][] drawRect(int[][] array, int x1, int y1, int x2, int y2, int radius, int type, boolean filled) {
		if (x1 > x2)
		{
			x1=x1+x2; 
			x2=x1-x2; 
			x1=x1-x2; 			
		}
		if (y1 > y2)
		{
			y1=y1+y2; 
			y2=y1-y2; 
			y1=y1-y2; 	
		}
		
		for (int i = x1; i<x2; i++) 
		   {
		    	for (int j = y1; j < y2; j++) 
		    	{
		    		if (filled)
		    		array[i][j]=type;
		    		else if(j == y1 || j== y2-1||i==x1||i==x2-1)
		    		{
		    			array[i][j]=type;
		    		}
		    	}
		    }		
		return array;
	}
	
	private void drawLine(int[][] array, int x1, int y1, int x2, int y2, int radius, int type) {
		//TODO
	}
	private int [][] drawCircle(int[][] array, int x, int y, int radius, int type) {
		//TODO
		
		return array;
	}
}
