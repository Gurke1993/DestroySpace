package de.bplaced.mopfsoft.network;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.message.StartTransfer;

import util.FileOutputStreamExtended;

public class ClientFileTransferThread extends Thread{
	private static ClientFileTransferThread instance;
	private Socket fileS;
	private FileOutputStreamExtended out;
	private DataInputStream in;
	private long transmittedFileSize;
	private long completedTransmission = 0;
	private int step = 15000;
	private File currentFile;
	private boolean wait = false;

	  private ClientFileTransferThread(String ip, int port) throws IOException {
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
				completedTransmission += step;
				Log.info("Downloaded "+(int)(((double)completedTransmission/transmittedFileSize)*100)+"%");
			
				if (completedTransmission  >= transmittedFileSize) {
					Log.info("Finished FileTransfer of "+currentFile.getName());
		    		completedTransmission = 0;
		    		out.flush();
					out.close();
					out = null;
					wait = false;
	    		}
		}
	    	}
		  } catch (Exception e) {
			  Log.error(e);
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
		
		//Set values
		currentFile = file;
		transmittedFileSize = fileSize;
		
		
		Log.debug("Preparing file download of"+currentFile.getName()+" of size "+transmittedFileSize);
		//Create the file
		try {
			file.createNewFile();
		} catch (IOException e) {
			Log.error(e);
		}

		//Setup Output
		try {
			out = new FileOutputStreamExtended(file);
			
			//Send message to server
			ClientThread.getInstance().send(new StartTransfer(file)+"");
		} catch (FileNotFoundException e) {
			Log.error("Could not prepare for File Transfer of file: "+file.getName(),e);
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
