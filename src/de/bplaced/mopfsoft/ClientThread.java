package de.bplaced.mopfsoft;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;



public class ClientThread extends Thread{
	Socket mainS;
	  DataOutputStream out;
	  DataInputStream in;
      DestroySpace destroySpace;

	  public ClientThread(String ip, int mainPort, DestroySpace destroySpace) throws IOException {
	      System.out.println("Starting client!");
	      this.destroySpace = destroySpace;
	      mainS = new Socket(ip,mainPort);
	      in = new DataInputStream(mainS.getInputStream()) ;
	      out = new DataOutputStream(mainS.getOutputStream()) ;
	    this.start();
	  }

	  public void run() {
		String text;
		try {
			// Normal Mode
			while ((text = in.readUTF()) != null) {
				this.destroySpace.analyseServerMessage(text);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Closing Socket!");
		    mainS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	  }

	public void setupFileClient(String filename, long filelength) {
		  
	ClientFileTransferThread fileTransfer = destroySpace.getClientFileTransferThread();
	fileTransfer.prepareForNewFileTransfer(new File("maps/"+filename), filelength);
	}

	public void send(String message) {
	    try {
	      out.writeUTF(message);
	    }
	    catch(IOException e) {
	      e.printStackTrace();
	    }
	  }
	  
	  public void close() {
		  try {
			  mainS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }

	public void closeByClient() {
		send("action=closeserver");
	}
}
