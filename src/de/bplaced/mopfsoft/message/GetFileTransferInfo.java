package de.bplaced.mopfsoft.message;

import java.io.File;


public class GetFileTransferInfo extends Message{

	public GetFileTransferInfo(File file) {
		super("Class=GetFileTransferInfo:FileName="+file.getName()+":Path="+file.getPath());
	}


}
