package com.games.dmg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import android.content.Context;
import com.games.dmg.cDataLoader;

public class Engine {

	private cDataLoader cdl;
	private HashMap<String,ArrayList<Object>> addset;
	private HashMap<String,ArrayList<Object>> subset;
	private HashMap<String,ArrayList<Object>> mulset;
	private HashMap<String,ArrayList<Object>> divset;
	@SuppressWarnings("unused")
	private Context mContext;
	public ArrayList<Object> questionset;
	private HashMap<String,ArrayList<String>> stats;
	private  final String statfile = "/data/data/com.games.dmg/stats.DMG";
	
	
	public Engine() {
		cdl = new cDataLoader();
		addset = new HashMap<String, ArrayList<Object>>();
		subset = new HashMap<String, ArrayList<Object>>(); 
		mulset = new HashMap<String, ArrayList<Object>>();
		divset = new HashMap<String, ArrayList<Object>>();
		
		//Key = (operation)(difficulty)
		ArrayList<String> statsdata = new ArrayList<String>();
		stats = new HashMap<String,ArrayList<String>>();
		
		//statsdata[0] => total questions
		//statsdata[1] => total correct
		//statsdata[2] => total wrong
		statsdata.add("0"); statsdata.add("0"); statsdata.add("0");
		
		stats.put("11", statsdata); stats.put("12", statsdata); stats.put("13", statsdata);
		stats.put("21", statsdata); stats.put("22", statsdata); stats.put("23", statsdata);
		stats.put("31", statsdata); stats.put("32", statsdata); stats.put("33", statsdata);
		stats.put("41", statsdata); stats.put("42", statsdata); stats.put("43", statsdata);
	}
	
	public void setAddSet(HashMap<String, ArrayList<Object>> addset) { this.addset = addset; }
	public void setSubSet(HashMap<String, ArrayList<Object>> subset) { this.subset = subset; }
	public void setMulSet(HashMap<String, ArrayList<Object>> mulset) { this.mulset = mulset; }
	public void setDivSet(HashMap<String, ArrayList<Object>> divset) { this.divset = divset; }
	public ArrayList<Object> getQuestionSet() { return this.questionset; }
	
	public void compilequestionset(int operation, int difficulty) {
    	if( operation == 1 ) {
    		if( difficulty == 1)       { questionset = addset.get("11"); }
    		else if( difficulty == 2 ) { questionset = addset.get("12"); }
    		else if( difficulty == 3 ) { questionset = addset.get("13"); }
    	}
    	else if( operation == 2 ) {
    		if( difficulty == 1)       { questionset = subset.get("21"); }
    		else if( difficulty == 2 ) { questionset = subset.get("22"); }
    		else if( difficulty == 3 ) { questionset = subset.get("23"); }
    	}
    	else if( operation == 3 ) {
    		if( difficulty == 1)       { questionset = mulset.get("31"); }
    		else if( difficulty == 2 ) { questionset = mulset.get("32"); }
    		else if( difficulty == 3 ) { questionset = mulset.get("33"); }
    	}
    	else if( operation == 4 ) {
    		if( difficulty == 1)       { questionset = divset.get("41"); }
    		else if( difficulty == 2 ) { questionset = divset.get("42"); }
    		else if( difficulty == 3 ) { questionset = divset.get("43"); }
    	}
    }
	
	public void setContext(Context context) { this.mContext = context; }
	
	public HashMap<String, ArrayList<Object>> loadProblemset(int operation) {
		try {
			if( operation == 1 )      { return cdl.readGameData("1.DMG"); }
			else if( operation == 2 ) { return cdl.readGameData("2.DMG"); }
			else if( operation == 3 ) { return cdl.readGameData("3.DMG"); }
			else if( operation == 4 ) { return cdl.readGameData("4.DMG"); }
		} catch(Exception e) { return null; }
		return null;
	}
	
	public HashMap<String,ArrayList<Object>> getOperationSet(int operation) {
		if( operation == 1 )     { return this.addset; }
		else if( operation == 2) { return this.subset; }
		else if( operation == 3) { return this.mulset; }
		else if( operation == 4) { return this.divset; }
		return null;
	}
	
	public void writestats(String operation, String difficulty, String totalquestions, String correct, String wrong) {
		
		try {
			BufferedWriter bw = new BufferedWriter( new FileWriter(this.statfile,true));
			bw.write(operation + "|" + difficulty + "|" + totalquestions + "|" + correct + "|" + wrong + "\n");
			bw.close();
		} catch(Exception e) { ; }
		
	}
	
	public void readstats() {
		this.gatherstats(1, 1); this.gatherstats(1, 2); this.gatherstats(1, 3); 
		this.gatherstats(2, 1); this.gatherstats(2, 2); this.gatherstats(2, 3); 
		this.gatherstats(3, 1); this.gatherstats(3, 2); this.gatherstats(3, 3);  
		this.gatherstats(4, 1); this.gatherstats(4, 2); this.gatherstats(4, 3); 
	}
	
	public HashMap<String, ArrayList<String>> gethistorystats() { return this.stats; }
	
	public void gatherstats(int mOperation, int mDifficulty) {
		ArrayList<String> retval = new ArrayList<String>();
		String line = "";
		int tq=0, tc=0, tw=0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.statfile));
			while( (line=br.readLine()) != null ) {
				StringTokenizer st = new StringTokenizer(line, "|");
				if( st.countTokens() > 0 ) {
					String operation = st.nextToken();
					String difficulty = st.nextToken();
					int totalq = Integer.parseInt(st.nextToken());
					int totalc = Integer.parseInt(st.nextToken());
					int totalw = Integer.parseInt(st.nextToken());
					
					if( operation.equals(Integer.toString(mOperation)) && difficulty.equals(Integer.toString(mDifficulty))) { 
						tq += totalq; tc += totalc; tw += totalw; 
					}
				}
			}
			br.close();
			
		} catch(Exception e) { ; }
		
		retval.add(Integer.toString(tq)); retval.add(Integer.toString(tc)); retval.add(Integer.toString(tw));
		String key =Integer.toString(mOperation) + Integer.toString(mDifficulty);
		stats.put(key, retval);
	}
}
