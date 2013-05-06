package de.bplaced.mopfsoft;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ClientFileTransferThread extends Thread{
	Socket fileS;
	  ExtendedFileOutputStream out;
	  DataInputStream in;
      DestroySpace destroySpace;
	private long transmittedFileSize;
	private long completedTransmission = 0;
	private int step = 15000;
	private File currentFile;
	private boolean wait = true;

	  public ClientFileTransferThread(String ip, int port, DestroySpace destroySpace) throws IOException {
	      System.out.println("Starting FileTransferClient!");
	      this.destroySpace = destroySpace;
	      fileS = new Socket(ip,port);
	      in = new DataInputStream(fileS.getInputStream()) ;
	    this.start();
	  }

	  public void run() {
		  try {
		  byte[] buffer = new byte[step];
		  int readBytes;
	    	while ((readBytes = in.read(buffer)) != -1) {
	    		if (out != null) {
	    		if (readBytes >= 0) {
	    			
				out.write(Arrays.copyOfRange(buffer, 0, readBytes-1));
				completedTransmission += step;
				System.out.println("Downloaded "+(((double)completedTransmission/transmittedFileSize)*100)+"%");
			
				if (completedTransmission  >= transmittedFileSize) {
					System.out.println("Finished FileTransfer of "+currentFile.getName());
		    		completedTransmission = 0;
		    		File file = out.getFile();
		    		out.flush();
					out.close();
					out = null;
					wait = false;
					
					//Load the file inside of the FileHandler for further use in this session
					destroySpace.getFileHandler().setFileIsReady(file, true);
					
				}
	    		}
		}
	    	}
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }

	  
	  public void close() {
		  try {
			  fileS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }

	public void prepareForNewFileTransfer(File file, long fileSize) {
		//Set values
		currentFile = file;
		transmittedFileSize = fileSize;
		
		
		System.out.println("Preparing file download of"+currentFile.getName()+" of size "+transmittedFileSize);
		//Create the file
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Setup Output
		try {
			out = new ExtendedFileOutputStream(file);
			
			//Send message to server
			System.out.println("letting server know...");
			destroySpace.getClientThread().send("action=starttransfer:filename="+file.getName()+":path="+file.getPath());
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] Could not prepare for File Transfer of file: "+file.getName());
			e.printStackTrace();
		}
		
		wait = true;
		while(this.wait) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("notified");

	}
	  
}
