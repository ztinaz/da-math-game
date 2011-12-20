package com.games.dmg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;

public class cDataLoader {
	
	String filename = "/data/data/com.games.dmg/";
	private Context context;
	
	public void setContext(Context mcontext) { this.context = mcontext; }
	
	
	@SuppressWarnings("unchecked")
	public void loadGameData(String gamefile) {
		
		//Loading games files
		try {
			InputStream fis = this.context.getAssets().open(gamefile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename + gamefile));
			
			HashMap<String,ArrayList<Object>> gameset = new HashMap<String,ArrayList<Object>>();
			gameset = (HashMap<String,ArrayList<Object>>) ois.readObject();
			
			oos.writeObject(gameset);
			
			fis.close();
			ois.close();
			oos.close();	
		} catch(Exception e) { ; }
	}
	
	public boolean checkGameFileExists(String gamefile) {
		try {
			File file = new File(filename + gamefile);
			return file.exists();
		} catch(Exception e) { return false; }
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String,ArrayList<Object>> readGameData(String gamefile) {
		HashMap<String,ArrayList<Object>> ret = new HashMap<String,ArrayList<Object>>();
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename + gamefile));
			ret = (HashMap<String,ArrayList<Object>>) ois.readObject();
			
		} catch(Exception e) { ; }
		return ret;
	}
}
