package utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Settings {

	private static Scanner scan;
	private static PrintWriter out;
	
	public static final String VOLUME = "volume";
	
	private static float volume;
	
	private Settings(){}
	
	public static void setToDefaults(){
		volume = .25f;
	}
	
	public static void loadSettings(String path) throws FileNotFoundException{
		File file = new File(path);
		scan = new Scanner(new BufferedReader(new FileReader(file)));
		scan.useDelimiter("[;\r\n]");
		if(file.exists() && file.isFile()){
			while(scan.hasNext()){
				set(scan.next(), scan.next());
			}
			scan.close();
		}
	}
	
	public static void save(String path) throws IOException{
		File file = new File(path);
		out = new PrintWriter(new FileWriter(file));
		out.println("volume:"+volume);
		
		out.close();
	}
	
	public static Object getSetting(String name){
		switch(name){
			case VOLUME: return new Float(volume);
			default: return null;
		}
	}
	
	
	public static void set(String name, String value){
		switch(name){
			case VOLUME: volume = Float.parseFloat(value); break;
			default: break;
		}
	}
}