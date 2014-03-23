package de.bplaced.mopfsoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.bplaced.mopfsoft.drawableobjects.Setting;

public class FileHandler {

	@SuppressWarnings("unused")
	private DestroySpace destroySpace;
	private HashMap<String, Image> imageMap = new HashMap<String, Image>();
	private HashMap<String, String> stringMap = new HashMap<String, String>();
	private HashMap<String, Boolean> readyMap = new HashMap<String, Boolean>();
	private List<Setting> settings;

	public FileHandler(DestroySpace destroySpace) {
		this.destroySpace = destroySpace;
		this.loadSettings();
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

	public List <Setting> getSettings() {
		return this.settings;
	}

	public void saveSettings() {
		try {
		FileWriter writer = new FileWriter(new File("settings.txt"));
		
		for (Setting setting: this.settings) {
			writer.write(setting.getName()+"="+setting.getValue()+"="+setting.getKind()+System.getProperty("line.separator"));
		}
		
		writer.close();
		
		} catch (Exception e) {
			System.out.println("[ERROR] Could not save settings!");
			e.printStackTrace();
		}
	}
	
	public void loadSettings() {
		
		File settingFile = new File("settings.txt");

		if (!settingFile.exists()) {
			try {
			settingFile.createNewFile();
			
			InputStream is = Map.class.getClass().getResourceAsStream(
					"/resources/other/settings.txt");
			FileOutputStream os = new FileOutputStream(settingFile);
			
			for (int read = 0; (read = is.read()) != -1;) {
				os.write(read);
			}
			
			os.flush();
			os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.settings = new ArrayList<Setting>();
		
		try {
		FileReader fr = new FileReader(settingFile);
		BufferedReader reader = new BufferedReader(fr);
		
		String line;
		String[] args;
		while ((line = reader.readLine()) != null) {
			args = line.split("=");
			settings.add(new Setting(args[0], args[1],args[2]));
		}
		
		reader.close();
		fr.close();
		
		} catch (Exception e) {
			System.out.println("[ERROR] Could not load settings!");
			e.printStackTrace();
		}
		
		int ind;
		if ((ind = settings.indexOf(new Setting("build", "", "system"))) != -1 && Integer.parseInt(settings.get(ind).getValue()) != DestroySpace.BUILD) {
			System.out.println("Settings are to old... Removing...");
			settingFile.delete();
			loadSettings();
		}
		settings.remove("build");
	}

}
