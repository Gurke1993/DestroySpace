package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileOutputStreamExtended extends FileOutputStream{

	private File file;

	public FileOutputStreamExtended(File file) throws FileNotFoundException {
		super(file);
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

}
