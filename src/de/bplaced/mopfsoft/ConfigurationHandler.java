package de.bplaced.mopfsoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ConfigurationHandler {
	

	private static ConfigurationHandler instance = null;
	private HashMap<String, String> configurationMap = new HashMap<String, String>();;
	private File file;
	
	
	private ConfigurationHandler (String string) {
		this.file = new File(string);
		if (!file.exists()) {
			try {
				System.out.println("Creating new Config...");
				file.createNewFile();
				InputStream is = getClass().getResourceAsStream("/resources/other/config.txt");
				FileOutputStream os = new FileOutputStream(new File("config.txt"));
				for(int read = 0; (read = is.read()) != -1;)
				    os.write(read);
				os.flush();
				os.close();
			} catch (IOException e) {
				System.out.println("[ERROR] Could not create config file!!");
				e.printStackTrace();
			}
		}
		reload();
	}
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
		reload();
	}
	
	private void reload() {
		//Reload data
		String line;
		
		try {
			FileReader fr = new FileReader (file);
			BufferedReader reader = new BufferedReader(fr);
		
			while((line = reader.readLine()) != null) {
				configurationMap.put(line.split(":")[0], line.split(":")[1]);
			}
			reader.close();
			fr.close();
			} catch (Exception e) {
			System.out.println("[Error] Could not reload config!!");
			e.printStackTrace();
		}
	} 
	
	public String getString(String key) {
		return configurationMap.get(key);
	}
	
	public Integer getInteger(String key) {
		return Integer.parseInt(configurationMap.get(key));
	}
	
	public Double getDouble(String key) {
		return Double.parseDouble(configurationMap.get(key));
	}
	
	public void setEntry(String key, String value) {
		configurationMap.put(key, value);
		save();
	}
	
	
	private void save() {
		try {
		FileWriter writer = new FileWriter(this.file, false);

		for (String key : configurationMap.keySet()) {
			writer.write(key+":"+configurationMap.get(key));
		}
		writer.close();
		} catch (Exception e) {
			System.out.println("[ERROR] Could not save config!!");
			e.printStackTrace();
		}
	}
	public static void init(String path) {
		if (instance == null)
		ConfigurationHandler.setInstance(new ConfigurationHandler(path));
	}
	public static ConfigurationHandler getInstance() {
		return instance;
	}
	private static void setInstance(ConfigurationHandler instance) {
		ConfigurationHandler.instance = instance;
	}
}
