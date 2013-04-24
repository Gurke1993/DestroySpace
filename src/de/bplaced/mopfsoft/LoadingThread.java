package de.bplaced.mopfsoft;

public class LoadingThread implements Runnable{

	
	private LoadingState loadingState;
	private int collumnLength;
	private String[][] playerArray;
	private int[] gameFieldArray;

	public LoadingThread(LoadingState loadingState, int [] gameFieldArray, int collumnLength, String [] [] playerArray) {
		this.loadingState = loadingState;
		this.gameFieldArray = gameFieldArray;
		this.collumnLength = collumnLength;
		this.playerArray = playerArray;
	}

	@Override
	public void run() {
		
		//UpdateLoadingScreen

		loadingState.setupGame(gameFieldArray, collumnLength, playerArray);
		
	}
	
	
	
	
}
