package de.bplaced.mopfsoft.message;

import java.io.File;


public class GetMapString extends Message{

	public GetMapString(File file) {
		super("Class=GetMapString:FileName="+file.getName()+":Path="+file.getPath());
	}


}
