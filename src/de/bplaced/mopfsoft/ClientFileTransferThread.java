package de.bplaced.mopfsoft;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class ClientFileTransferThread extends Thread{
	private static ClientFileTransferThread instance;
	Socket fileS;
	  ExtendedFileOutputStream out;
	  DataInputStream in;
	private long transmittedFileSize;
	private long completedTransmission = 0;
	private int step = 15000;
	private File currentFile;
	private boolean wait = false;

	  private ClientFileTransferThread(String ip, int port) throws IOException {
	      System.out.println("Starting FileTransferClient!");
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
	    		out.write(buffer, 0, readBytes-1);
				//out.write(Arrays.copyOfRange(buffer, 0, readBytes-1));
				completedTransmission += step;
				System.out.println("Downloaded "+(int)(((double)completedTransmission/transmittedFileSize)*100)+"%");
			
				if (completedTransmission  >= transmittedFileSize) {
					System.out.println("Finished FileTransfer of "+currentFile.getName());
		    		completedTransmission = 0;
		    		File file = out.getFile();
		    		out.flush();
					out.close();
					out = null;
					wait = false;
					
					//Load the file inside of the FileHandler for further use in this session
					FileHandler.getInstance().setFileIsReady(file, true);
					
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
		while(this.wait) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("notified");
		
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
			ClientThread.getInstance().send("action=starttransfer:filename="+file.getName()+":path="+file.getPath());
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] Could not prepare for File Transfer of file: "+file.getName());
			e.printStackTrace();
		}
		
		wait = true;

	}

	public static void init(String ip, int i) throws IOException {
		setInstance(new ClientFileTransferThread(ip,i));
	}

	private static void setInstance(ClientFileTransferThread clientFileTransferThread) {
		instance = clientFileTransferThread;
	}

	public static ClientFileTransferThread getInstance() {
		return instance;
	}
	  
}
