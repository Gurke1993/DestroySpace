package de.bplaced.mopfsoft;

//TODO in enter constructor von drawablemap

import java.io.File;
import java.io.FileNotFoundException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.drawableobjects.DrawableMap;
import de.bplaced.mopfsoft.map.Map;


public class EditorState extends BasicGameState{
	public static final int ID = 2;	
	private  StateBasedGame stateBasedGame ;
	
	
	//Toolvalues
	private boolean quickLine;
	private int radius;
	private int tool;//O Pencil //1 Rubber //2 Fill //3 Line //4 Circle
	private int blockId;
	private boolean info;
	private int x1,y1;
	private int appNum;//Number of application //0 MapOpener //1 MapCreate //2 MapSaver
	//gamefield for editing
	private DrawableMap drawableMap;
	//map position
	int imgPosX,imgPosY;
    //BackgroundImages
	Image bgImage;
	EditorPaintFunction paintFunction;//Implement paintfunctions
	EditorStateMapOpener mapOpener;//open application
	EditorStateNewMap mapCreater; //create application
	EditorStateSaver mapSaver; //save application
	TrueTypeFont font;
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException 
	{
		Map.copyDefaultMap();
		try {
		drawableMap = new DrawableMap(new File("maps"+System.getProperty("file.separator")+"DefaultMap.map"),"");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		paintFunction = new EditorPaintFunction(drawableMap);
		
		mapOpener = new EditorStateMapOpener( container, font, Color.green, 800, 64, 150, 200,drawableMap);
		mapSaver = new EditorStateSaver( container, font, Color.green, 800, 64, 150, 200,drawableMap);
		mapCreater = new EditorStateNewMap( container, font, Color.green, 800, 64, 150, 200,drawableMap);
	}
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		this.stateBasedGame = stateBasedGame;			

		blockId=2;
		bgImage = new Image("resources/images/editor/bg.png");
		imgPosX=0;
		imgPosY=0;
		font = new TrueTypeFont(new java.awt.Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 26), false);
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
        	graphics.drawString("(C)ircle", 10, 150);
        }
        graphics.drawString("current Tool: "+tool, 100, 10);
        graphics.drawString("radius: "+ radius,250,10);
        graphics.drawString("x1: "+x1+" y1: "+y1,400,10);
        if (tool==3 ){graphics.drawString("QuickLine: "+ quickLine,550,10);}
        mapOpener.draw(gameContainer, graphics);
        
      //0 MapOpener //1 MapCreate //2 MapSaver
        if (appNum==0){mapOpener.draw( gameContainer ,graphics);}
        else if(appNum==1){mapCreater.draw(gameContainer, graphics);}
        else if(appNum==2){mapSaver.draw(gameContainer, graphics);}
       
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
        		paintFunction.paint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY(), radius, blockId);
        	}
        	if (tool ==1)
        	{
        		paintFunction.delete(input.getAbsoluteMouseX(), input.getAbsoluteMouseY(), radius);
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

			case 46 :{
				tool=4;
				break;
			}
			case  23:{
				System.out.print("works!");
				info = !info;
				break;
			}
			case 16:{
				quickLine= !quickLine;
				break;
			}
			//0 MapOpener //1 MapCreate //2 MapSaver
			case 31:{
				appNum=2;
				break;
			}
			case 49:{
				appNum=1;
				break;
			}
			case 24:{
				appNum=0;
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
				int idOverwrite=drawableMap.getBlock(x, y).getBid();
				paintFunction.fill( x, y, blockId,idOverwrite);
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
