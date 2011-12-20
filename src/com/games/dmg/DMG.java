package com.games.dmg;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.games.dmg.cDataLoader;
import com.games.dmg.Engine;
import com.games.dmg.vTestSession;

public class DMG extends Activity {
	
	public HashMap<String, ArrayList<Object>> addSet;
	public HashMap<String, ArrayList<Object>> subSet;
	public HashMap<String, ArrayList<Object>> mulSet;
	public HashMap<String, ArrayList<Object>> divSet;
	
	public int difficulty=0;
	public int operation;
	public boolean timebased;
	public int questions = 10;
	public ArrayList<Object> questionset;
	public Engine eng;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Initialize variables
        addSet = new HashMap<String, ArrayList<Object>>();
        subSet = new HashMap<String, ArrayList<Object>>();
        mulSet = new HashMap<String, ArrayList<Object>>();
        divSet = new HashMap<String, ArrayList<Object>>();
        
        this.startup();
        eng = new Engine();
        addSet = eng.loadProblemset(1);
        subSet = eng.loadProblemset(2);
        mulSet = eng.loadProblemset(3);
        divSet = eng.loadProblemset(4);
        
        eng.setAddSet(addSet);
        eng.setSubSet(subSet);
        eng.setMulSet(mulSet);
        eng.setDivSet(divSet);
        
        /** Show App Rater **/
        AppRater.app_launched(this);
        //AppRater.showRateDialog(this, null);
        this.setupgui();
    }
    
    
    public void setEasy(View view)   { this.difficulty = 1; }
    
    public void setNormal(View view) { this.difficulty = 2; }
    
    public void setHard(View view)   { this.difficulty = 3; }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void startTest(View view) {
    	eng.compilequestionset(this.operation, this.difficulty);
    	
    	//Create new activity for displaying the test
    	ArrayList tmp = new ArrayList();
    	tmp.add(eng.getQuestionSet());
    	
    	Intent i = new Intent();
    	Bundle b = new Bundle();
    	b.putString("difficulty", Integer.toString(this.difficulty));
    	b.putString("operation" , Integer.toString(this.operation));
    	b.putString("questions" , Integer.toString(this.questions));
    	b.putString("timebased" , Boolean.toString(this.timebased));
    	b.putParcelableArrayList("questionset", tmp);
    	
    	i.setClass(this, vTestSession.class);
    	i.putExtras(b);
	    startActivity(i);
    }
    
    public void setupgui() {
    	final Spinner spnumquestions = (Spinner) findViewById(R.id.spNumQuestions);
    	ArrayAdapter<CharSequence> numadapter = ArrayAdapter.createFromResource(this, R.array.numquestions_array, android.R.layout.simple_spinner_item);
    	numadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spnumquestions.setAdapter(numadapter);

    	spnumquestions.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				DMG.this.questions = Integer.parseInt(spnumquestions.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { ; }
    	});
    }
    
    //Script which checks if the game files exists otherwise import them from Assets
    
    public void startup() {
    	cDataLoader cdl = new cDataLoader();
    	cdl.setContext(this.getApplicationContext());
    	
    	String gamefile = "1.DMG";
    	
    	if( !cdl.checkGameFileExists(gamefile)) { cdl.loadGameData(gamefile); }
    	
    	gamefile = "2.DMG";
    	if( !cdl.checkGameFileExists(gamefile)) { cdl.loadGameData(gamefile); }
    	
    	gamefile = "3.DMG";
    	if( !cdl.checkGameFileExists(gamefile)) { cdl.loadGameData(gamefile); }
    	
    	gamefile = "4.DMG";
    	if( !cdl.checkGameFileExists(gamefile)) { cdl.loadGameData(gamefile); }
    }
    
    
    public void setTimed(View view) { this.timebased = true; }
    
    public void setUntimed(View view) { this.timebased = false; }  
    
    
    //Action handler for Add button
    public void addClicked(View view) { 
    	this.operation = 1;
    	
    	if( this.difficulty == 0 ) {
    		CharSequence text = "Please Select Difficulty Level\n1 Easy\n2 Normal\n3 Hard";
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
    		toast.show();
    		return;
    	}
    		
    	ImageButton button = (ImageButton) findViewById(R.id.btnStart);
    	button.setVisibility(View.VISIBLE);
    }
    
    public void subClicked(View view) { 
    	this.operation = 2;
    	
    	if( this.difficulty == 0 ) {
    		CharSequence text = "Please Select Difficulty Level\n1 Easy\n2 Normal\n3 Hard";
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
    		toast.show();
    		return;
    	}
    	
    	ImageButton button = (ImageButton) findViewById(R.id.btnStart);
    	button.setVisibility(View.VISIBLE);
    }
    
    public void mulClicked(View view) { 
    	this.operation = 3;
    	
    	if( this.difficulty == 0 ) {
    		CharSequence text = "Please Select Difficulty Level\n1 Easy\n2 Normal\n3 Hard";
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
    		toast.show();
    		return;
    	}
    	
    	ImageButton button = (ImageButton) findViewById(R.id.btnStart);
    	button.setVisibility(View.VISIBLE);
    }
    
    public void divClicked(View view) { 
    	this.operation = 4;
    	
    	if( this.difficulty == 0 ) {
    		CharSequence text = "Please Select Difficulty Level\n1 Easy\n2 Normal\n3 Hard";
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
    		toast.show();
    		return;
    	}
    	
    	ImageButton button = (ImageButton) findViewById(R.id.btnStart);
    	button.setVisibility(View.VISIBLE);
    }
 
    public void showStats(View view) {
    	Intent i = new Intent(this, vHistoryStats.class);
    	startActivity(i);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		try { 
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.layout.dmg_menu, menu);
		    return true;
		} catch(Exception e) { return false; }
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch( item.getItemId() ) {
    	case R.id.dmg_help:
    		String url = "http://damathgame.blogspot.com/";
        	try {
    			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    			startActivity(browserIntent);

    		} catch(Exception e) { ; }
    		break;
    	
    	case R.id.dmg_about:
    		showAbout();
    		break;
    	}
    	return true;
    }
    
    
    public void showAbout() {
    	final Dialog dialog = new Dialog(DMG.this);
    	dialog.setContentView(R.layout.dmg_about);
    	dialog.setTitle("Da Math Game - Version 1.0");
    	dialog.setCancelable(true);
    	dialog.show();
    }
}