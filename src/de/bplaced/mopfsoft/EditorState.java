package de.bplaced.mopfsoft;



import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.bplaced.mopfsoft.blocks.Block;
import de.bplaced.mopfsoft.blocks.Dirt;
import de.bplaced.mopfsoft.blocks.Stone;


public class EditorState extends BasicGameState{
	public static final int ID = 2;	
	private  StateBasedGame stateBasedGame ;
	
	
	//Toolvalues
	private boolean filled;
	private int fontSize;
	private int tool;//O Pencil //1 Rubber //2 Fill //3 Line //4 Rectangle //5 Circle
	private int shapeX1,shapeX2,shapeY1,shapeY2;
	
	//gamefield for editing
	private GameField gameField;
	
	//map position
	int imgPosX,imgPosY;
	
	Block [][] mapArray;

	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		this.stateBasedGame = stateBasedGame;	
		
		//New Gamefield
		Block[][] mapArray = new Block[1024][1000];
		for (int i = 0; i<mapArray.length; i++) {
			for (int j = 0; j<mapArray[0].length; j++) {
			mapArray[i][j] = new Stone(i,j);
			
		}
		}

		
		gameField = new GameField(mapArray);
		
		imgPosX=0;
		imgPosY=0;
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {


        graphics.drawRect(1, 1, 100, 100);
        graphics.drawImage(gameField.getGameFieldAsImage(), imgPosX, imgPosY);
	
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
        if(input.isKeyDown(Input.KEY_Q))
        {
        	drawCircle(mapArray,80, 80, 50);
	
        }
	}
	@Override
	public int getID() {
		return ID;
	}
	
	@Override
	public void keyPressed(int key, char c) {
		System.out.println(key);
		
		switch (key) {
			case 1 : {//Exit / ESC
				stateBasedGame.enterState(1);
			}
		}
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		System.out.println("Button pressed: "+button+" at "+x+" "+y);
		if (button == 0) { 
			
		}
	}	
	
	
	//Paint functions
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
		
		return array;
	}
	
	private void drawLine(int[][] array, int x1, int y1, int x2, int y2, int radius, int type) {
		//TODO
	}
	
	private Block[][] drawCircle(Block[][] array, int x, int y, int radius) {
		double alpha = 0;
		int xRad=0;
		int yRad=0;
		
		//calculate around
		for(int i=1;i<=360;i++){
		  xRad= (int) Math.round(radius+(radius*Math.cos(alpha)));
		  yRad=(int) Math.round(radius+(radius*Math.sin(alpha)));
		  
		 gameField.changeBlock(xRad+x, yRad+y, new Dirt(xRad+x, yRad+y));
	
		 alpha += 3.14159265/180; //
		}
		
		return array;
	}
}
