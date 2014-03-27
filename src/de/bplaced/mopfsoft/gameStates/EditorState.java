package de.bplaced.mopfsoft.gameStates;

//TODO in enter constructor von drawablemap

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.drawableobjects.DrawableMap;
import de.bplaced.mopfsoft.editor.Button;
import de.bplaced.mopfsoft.editor.EditorPaintFunction;


public class EditorState extends BasicGameState{
	public static final int ID = 2;	
	private  StateBasedGame stateBasedGame ;
	
	
	//Toolvalues
	private boolean quickLine;
	private int radius;
	private int tool;//O Pencil //1 Rubber //2 Fill //3 Line //4 Circle
	private int blockId;

	private int x1,y1;


	//gamefield for editing
	private DrawableMap drawableMap;
	//map position
	int imgPosX,imgPosY;
    //BackgroundImages
	Image bgImage;
	Image menuImage,menuImage2;
	EditorPaintFunction paintFunction;//Implement paintfunctions

	

	ArrayList<Button> buttonList = new ArrayList<Button>(); 
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException 
	{

		try {
			
		drawableMap = new DrawableMap(new File("maps"+System.getProperty("file.separator")+"DefaultMap.map"),"");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		paintFunction = new EditorPaintFunction(drawableMap);
		

	}
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		this.stateBasedGame = stateBasedGame;			

		blockId=2;
		bgImage = new Image("resources/images/editor/bg.png");
	    menuImage = new Image("resources/images/editor/editormenu.png");
		imgPosX=0;
		imgPosY=0;
		
			buttonList.add(new Button(0, 0, 100, 100, null,  null, ID));			
			buttonList.add( new Button(100, 0, 100, 100, null,  null,  ID));
			buttonList.add( new Button(0, 100, 100, 100, null, null,  ID));
			buttonList.add( new Button(100, 100, 100,100, null , null,  ID));
			buttonList.add( new Button(0, 200, 100, 100, null , null,  ID));
			buttonList.add( new Button(100,200, 100, 100, null , null,  ID));
			buttonList.add( new Button(0, 300, 100, 100, null , null,  ID));
			buttonList.add(new Button(100, 300, 100, 100, null, null,  ID));
		
		tool=0;
		
		
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		
		//drawing game background
		graphics.drawImage(bgImage, 0, 0);
		//drawing the gamefield
        graphics.drawImage(drawableMap.getGamefieldAsImage(), imgPosX, imgPosY);
        //drawing game menu
        graphics.drawImage(menuImage,0,0);
        
        //drawing Informations //TODO
        graphics.drawString("radius: "+ radius,10,400);
        graphics.drawString("x1: "+x1+" y1: "+y1,10,420);
        if (tool==3 ){graphics.drawString("QuickLine: "+ quickLine,10,440);}
        graphics.drawString(""+tool, 10, 460);
        
        
        
       
      //Button Selection -draw
        for (int i = buttonList.size()-1;i>=0;i--)
        {
        	buttonList.get(i).draw(gameContainer, graphics);
        }        		       	
        
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
       
        //DRAW UND ERASE
        if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
        {
        	if (tool==0)
			{
        		paintFunction.paint(input.getAbsoluteMouseX()-imgPosX, input.getAbsoluteMouseY()-imgPosY ,radius, blockId);

			}
			else if (tool ==1)
			{
				paintFunction.delete(input.getAbsoluteMouseX()-imgPosX, input.getAbsoluteMouseY()-imgPosY, radius);
			}
        }
       
                
        //Button Selection -> AppNum
        for (int i = buttonList.size()-1;i>=0;i--)
        {
        	
        	if(buttonList.get(i).getActive()==true)
        	{
        		
        		 if (i !=tool)
        		{
        			 
        			 buttonList.get(tool).setActiveFalse();
        			tool =i;
        		}
        	}
        }        		       	
        }
                
	
	@Override
	public int getID() {
		return ID;
	}
	
	@Override //when a key is clicked
	
	public void keyPressed(int key, char c) {
		
		switch (key) {
			case 1 : {//Exit / ESC
				stateBasedGame.enterState(1);
				break;
			}
	}	
	}
	
	@Override //when mouse is clicked
	public void mousePressed(int button, int x, int y) {
		if (button == 0) //O Pencil //1 Rubber //2 Fill //3 Line //4 Rectangle //5 Circle
		{ 
		
			if (tool ==2)//Fill
			{
				@SuppressWarnings("unused")
				int idOverwrite=drawableMap.getBlock(x, y).getBid();
				//paintFunction.fill( x, y, blockId,idOverwrite);
			}
			else if (tool ==3)//Line
			{
				if (quickLine) {
					if (x1 == 0 && y1 == 0) {
						x1=x;y1=y;
					}
					else {
						paintFunction.drawLine(x, y, x1, y1, blockId);
						x1 = x;
						y1 = y;
					}
				} 
				else {
					if (x1 == 0 && y1 == 0) {
						x1 = x;
						y1 = y;
					} else {
						paintFunction.drawLine(x, y, x1, y1, blockId);
						x1 = 0;
						y1 = 0;
					}
				}
			}
			else if (tool ==4)
			{
			paintFunction.drawCircle( x, y, radius,blockId);
			}
			
		}	
   }
}
