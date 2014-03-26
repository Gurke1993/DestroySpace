package de.bplaced.mopfsoft.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.DestroySpace;


public class FileHandler {

	private static FileHandler instance = null;
	private static final List <String> textFormats = Arrays.asList("map", "txt");
	private static final List <String> imageFormats = Arrays.asList("gif", "png");
	
	private HashMap<String, Image> imageFiles = new HashMap<String, Image>();
	private HashMap<String, String> textFiles = new HashMap<String, String>();
	
	private Map<String,String> settings;
	private Map<Integer,String> inputSettings;

	private FileHandler() {
		this.loadSettings();
	}


	/** Loads a file into the application
	 * @param file
	 * @return true if File is now accessible, false otherwise
	 */
	public boolean addFile(File file) {
		
		Log.info("Loading " + file.getPath()+"...");
		try {
			String fileType = file.getName().split("\\.")[1];

			if (textFormats.contains(fileType)) {
				byte[] buffer = new byte[(int) file.length()];
				FileInputStream f = new FileInputStream(file);
				f.read(buffer);
				f.close();
				textFiles.put(file.getName(), new String(buffer));
			} else 
			
			if (imageFormats.contains(fileType)) {
				imageFiles.put(file.getName(), new Image(
						new FileInputStream(file), file.getPath(), false));
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String getTextFile(String fileName) {
		return textFiles.get(fileName);
	}

	public Image getImage(String fileName) {
		return imageFiles.get(fileName);
	}

	/** Draws the image on the graphics object if it is loaded completely into the game
	 * @param x 
	 * @param y
	 * @param graphics
	 * @param fileName name of the image
	 * @return
	 */
	public Boolean drawImageIfLoaded(int x, int y, Graphics graphics,
			String fileName) {
		if (isLoaded(fileName)) {
			graphics.drawImage(imageFiles.get(fileName), x, y);
			return true;
		}
		return false;
	}

	public boolean isLoaded(String fileName) {
		return (imageFiles.containsKey(fileName) || textFiles
				.containsKey(fileName));
	}

	public Map <String,String> getSettings() {
		return this.settings;
	}
	
	public String getSetting(String key) {
		return this.settings.get(key);
	}
	
	public void setSetting(String key, String value) {
		this.settings.put(key, value);
	}

	public void saveSettings() {
		try {
		FileWriter writer = new FileWriter(new File("settings.txt"));
		
		for (Entry <String,String> setting: this.settings.entrySet()) {
			writer.write(setting.getKey()+"="+setting.getValue()+System.getProperty("line.separator"));
		}
		
		writer.close();
		
		} catch (Exception e) {
			Log.error("[ERROR] Could not save settings!",e);
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
				Log.error(e);
			}
		}
		
		this.settings = new HashMap<String,String>();
		this.inputSettings = new HashMap<Integer,String>();
		
		try {
		FileReader fr = new FileReader(settingFile);
		BufferedReader reader = new BufferedReader(fr);
		
		String line;
		String inputString;
		String[] args;
		String[] key;
		String[] inputSplit;
		int inputLength;
		while ((line = reader.readLine()) != null) {
			args = line.split("=");
			key = args[0].split("\\.",2);
			settings.put(key[0]+"."+key[1], args[1]);
			
			if (key[0].equals("key")) {
				inputString = "";
				inputSplit = key[1].split("\\.");
				inputLength = inputSplit.length;
				for (int i = 0; i<inputLength; i= i+2) {
					inputString = inputString + ":" + inputSplit[i] + "=" + inputSplit [i+1];
				}
				inputString = inputString.substring(1);
				inputSettings.put(Integer.parseInt(args[1]), inputString);
				
				Log.debug(Integer.parseInt(args[1])+":"+inputString);
			}
		}
		
		
		reader.close();
		fr.close();
		
		} catch (Exception e) {
			Log.error("[ERROR] Could not load settings!",e);
		}
		
		if (settings.containsKey("system.build") && Integer.parseInt(settings.get("system.build")) != DestroySpace.BUILD) {
			Log.info("Settings are to old... Removing...");
			settingFile.delete();
			loadSettings();
		}
		settings.remove("system.build");
	}

	public static void init() {
		if (instance == null)
		FileHandler.setInstance(new FileHandler());
	}

	public static FileHandler getInstance() {
		return instance;
	}

	private static void setInstance(FileHandler instance) {
		FileHandler.instance = instance;
	}


	public Map <Integer,String> getInputSettings() {
		return inputSettings;
	}

}
