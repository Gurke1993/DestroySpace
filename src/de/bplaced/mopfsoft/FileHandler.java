package de.bplaced.mopfsoft;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class FileHandler {

	@SuppressWarnings("unused")
	private DestroySpace destroySpace;
	private HashMap<String, Image> imageMap = new HashMap<String, Image>();
	private HashMap<String, String> stringMap = new HashMap<String, String>();
	private HashMap<String, Boolean> readyMap = new HashMap<String, Boolean>();

	public FileHandler(DestroySpace destroySpace) {
		this.destroySpace = destroySpace;
	}

	/**
	 * This method trys to load a file into the game
	 * @param fileName
	 */
	public void tryToLoadFile(String fileName) {
		readyMap.isEmpty();
		if (readyMap.get(fileName) != null && readyMap.get(fileName).equals(true)) {
			System.out.println("Trying to load " + fileName);
			try {
				String fileType = fileName.split("\\.")[1];
				if (fileType.equals("gif")) {
					System.out.println("Is a .gif");
					imageMap.put(fileName, new Image("maps/" + fileName));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**Loads a supported object from file
	 * @param file
	 */
	public void loadFile(File file) {
		// 1 = mapPreview <Image>; 2 = map <String>
		System.out.println("Trying to load " + file.getPath());
		try {
			String fileType = file.getName().split("\\.")[1];

			if (fileType.equals("map")) {
				byte[] buffer = new byte[(int) file.length()];
				FileInputStream f = new FileInputStream(file);
				f.read(buffer);
				f.close();
				stringMap.put(file.getName(), new String(buffer));
			} else if (fileType.equals("gif")) {
				System.out.println("Is a .gif");
				imageMap.put(file.getName(), new Image(
						new FileInputStream(file), file.getPath(), false));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getString(String fileName) {
		return stringMap.get(fileName);
	}

	public Image getImage(String fileName) {
		return imageMap.get(fileName);
	}

	/** Draws the image on the graphics object if it is loaded completly into the game
	 * @param x 
	 * @param y
	 * @param graphics
	 * @param fileName name of the image
	 * @return
	 */
	public Boolean drawImageIfLoaded(int x, int y, Graphics graphics,
			String fileName) {
		if (isFileLoaded(fileName)) {
			graphics.drawImage(imageMap.get(fileName), x, y);
			return true;
		}
		return false;
	}

	public boolean isFileLoaded(String fileName) {
		return (imageMap.containsKey(fileName) || stringMap
				.containsKey(fileName));
	}

	public void setFileIsReady(File file, Boolean ready) {
		this.readyMap.put(file.getName(), ready);
		synchronized (this) {
			this.notifyAll();
		}
	}


//	private boolean isReady(File file) {
//		if (!readyMap.containsKey(file.getName())) return false;
//		return readyMap.get(file.getName());
//	}

}
