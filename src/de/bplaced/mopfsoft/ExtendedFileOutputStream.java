package de.bplaced.mopfsoft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ExtendedFileOutputStream extends FileOutputStream{

	private File file;

	public ExtendedFileOutputStream(File file) throws FileNotFoundException {
		super(file);
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

}
