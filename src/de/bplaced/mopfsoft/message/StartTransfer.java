package de.bplaced.mopfsoft.message;

import java.io.File;

public class StartTransfer extends Message{

	public StartTransfer(File file) {
		super("Class=StartTransfer:FileName="+file.getName()+":Path="+file.getPath());
	}

}
