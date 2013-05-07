package de.bplaced.mopfsoft;

//TODO in enter constructor von drawablemap

import java.io.File;
import java.io.FileNotFoundException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import de.bplaced.mopfsoft.map.Map;


public class EditorState extends BasicGameState{
	public static final int ID = 2;	
	private  StateBasedGame stateBasedGame ;
	
	
	//Toolvalues
	private boolean filled;
	private int radius;
	private int tool;//O Pencil //1 Rubber //2 Fill //3 Line //4 Rectangle //5 Circle
	private int blockId;
	private boolean info;
	private int x1,y1;
	//gamefield for editing
	private DrawableMap drawableMap;
	//map position
	int imgPosX,imgPosY;
    //BackgroundImages
	Image bgImage;
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		this.stateBasedGame = stateBasedGame;			

		blockId=2;
		Map.copyDefaultMap();
		try {
		drawableMap = new DrawableMap(new File("maps"+System.getProperty("file.separator")+"DefaultMap.map"),"");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bgImage = new Image("resources/images/editor/bg.png");
		imgPosX=0;
		imgPosY=0;
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		//drawing game background
		graphics.drawImage(bgImage, 0, 0);
		//drawing the gamefield
        graphics.drawImage(drawableMap.getGamefieldAsImage(), imgPosX, imgPosY);
        
        //drawing infomation
        if (info)
        {
        	graphics.drawString("(P)encil", 10, 30);
        	graphics.drawString("(E)rase", 10, 60);
        	graphics.drawString("(F)ill", 10, 90);
        	graphics.drawString("(L)ine", 10, 120);
        	graphics.drawString("(R)ectangle", 10, 150);
        	graphics.drawString("(C)ircle", 10, 180);
        }
        graphics.drawString("current Tool: "+tool, 100, 10);
        graphics.drawString("radius: "+ radius,250,10);
        graphics.drawString("x1: "+x1+" y1: "+y1,400,10);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int timePassed)
			throws SlickException {
		Input input = gameContainer.getInput();
		 
		//picture position
        if(input.isKeyDown(Input.KEY_A))
        {
        	imgPosX -= 1;
        }
  
        if(input.isKeyDown(Input.KEY_D))
        {
        	imgPosX += 1;
        }
  
        if(input.isKeyDown(Input.KEY_W))
        {
        	imgPosY -= 1;
        }
        if(input.isKeyDown(Input.KEY_S))
        {
        	imgPosY += 1;
        }
       
        if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
        {
        	if (tool ==0)
        	{
        	paint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY(), radius, blockId);
        	}
        	if (tool ==1)
        	{
        	delete(input.getAbsoluteMouseX(), input.getAbsoluteMouseY(), radius);
        	}
        }
       
        
        
	}
	@Override
	public int getID() {
		return ID;
	}
	
	@Override //when a key is clicked
	public void keyPressed(int key, char c) {
		System.out.println(key);
		
		switch (key) {
			case 1 : {//Exit / ESC
				stateBasedGame.enterState(1);
				break;
			}
			case 78 :{
				radius++;
				break;
			}
			case 74 :{
				if(radius >=0){radius--;}
				
				break;
			}
			//O Pencil //1 Rubber //2 Fill //3 Line //4 Rectangle //5 Circle
			case  25:{
				tool=0;
				break;
			}
			case 18 :{
				tool=1;
				break;
			}
			case 33 :{
				tool=2;
				break;
			}
			case  38:{
				tool=3;
				break;
			}
			case  19:{
				tool=4;
				break;
			}
			case 46 :{
				tool=5;
				break;
			}
			case  23:{
				info = !info;
				break;
			}
		}
	}
	
	@Override //when mouse is clicked
	public void mousePressed(int button, int x, int y) {
		if (button == 0) { //O Pencil //1 Rubber //2 Fill //3 Line //4 Rectangle //5 Circle
			if (tool==0)
			{
				
			}
			else if (tool ==1)
			{
				
			}
			else if (tool ==2)
			{
				fill( x, y, blockId);
			}
			else if (tool ==3)//Line
			{

				if (x1 ==0 && y1 == 0)
				{
					x1 = x;
					y1 =y;
				}
				else
				{
					drawLine( x, y, x1, y1, radius, blockId);
					x1=0;
					y1=0;
				}
			}
			else if (tool ==4)
			{
				
			}
			else if (tool ==5)
			{
			drawCircle( x, y, radius,blockId);
			}
		}
	}	
	
	
	private void paint(  int x, int y, int radius, int blockId) {	
		for (int i=radius; i >= 0 ; i--)
		{
			drawCircle( x, y, i , blockId);
		}
	}
	
	
	private void delete( int x, int y, int radius) {	

			for (int i=radius; i >= 0 ; i--)
			{
				 drawCircle(x, y, i , 0);
			}

	}
	
	//TODO
	private void fill( int x, int y, int id2) {
	
			drawableMap.updateBlock(x, y, 2);


						
	}
	
	private void drawRect( int x1, int y1, int x2, int y2, int radius, int type, boolean filled) {	
		
	}
	
	private void drawLine( int x0, int x1, int y0, int y1, int radius, int blockId) {
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

		    		drawableMap.updateBlock(y, x0, blockId);

		        }
		        else
		        {
		    		drawableMap.updateBlock(x0, y, blockId);

		        }
		        error = error + deltay;
		        if(error > 0)
		        {
		            y = y + ystep;
		            error = error - deltax;
		        }
		    }
	}

	private void drawCircle(int xPos, int yPos, int radius,int id) {
			int x = 0;
			int y = radius;
			int dE =1;
			int dSE = 2 - radius - radius;
			if (xPos+radius >0 && xPos+radius < drawableMap.getWidth() &&yPos+radius >0 && yPos+radius < drawableMap.getHeight())
			{

		
				 drawableMap.updateBlock(0+xPos , radius+yPos , id);
				 drawableMap.updateBlock(radius+xPos , 0+yPos , id);
				 drawableMap.updateBlock(0+xPos , -radius+yPos , id);
				 drawableMap.updateBlock(-radius+xPos ,0+yPos, id);
			
			 int F=1-radius;
			 while (x < y)
			 {
				 if (F < 0)
				 {
					F = F + dE;
				 }
				 else
				 {
					 F = F+dSE;
					 y = y-1;
					 dSE = dSE+4;
				 }
				 x=x+1;
				 dE=dE+2;
				 
				 
			drawableMap.updateBlock(x + xPos, y + yPos, id);
			drawableMap.updateBlock(-x + xPos, y + yPos, id); //
			drawableMap.updateBlock(-y + xPos, x + yPos, id);//
			drawableMap.updateBlock(-y + xPos, -x + yPos, id);//
			drawableMap.updateBlock(y + xPos, x + yPos, id);
			drawableMap.updateBlock(y + xPos, -x + yPos, id);
			drawableMap.updateBlock(x + xPos, -y + yPos, id);
			drawableMap.updateBlock(-x + xPos, -y + yPos, id);
			 }
			}
		
	}
	
}
