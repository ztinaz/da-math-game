package com.games.dmg;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.games.dmg.Engine;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class vHistoryStats extends Activity {
	
	private int difficulty = 0;
	private Engine eng;
	private TextView tvoperation;
	private TextView tvdifficulty;
	private TextView tvtotal;
	private TextView tvcorrect;
	private TextView tvwrong;
	private TextView tvperc;
	
	RelativeLayout r1;
	RelativeLayout r2;
	RelativeLayout r3;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        //setContentView(R.layout.history);
        
        eng = new Engine();
        eng.readstats();
        
        tvoperation  = (TextView) findViewById(R.id.tvoperation);
        tvdifficulty = (TextView) findViewById(R.id.tvdifficulty);
        tvtotal   = (TextView) findViewById(R.id.tvtotal);
        tvcorrect = (TextView) findViewById(R.id.tvcorrect);
        tvwrong   = (TextView) findViewById(R.id.tvwrong);
        tvperc    = (TextView) findViewById(R.id.tvperc);
        
        r1 = (RelativeLayout) findViewById(R.id.buttonrow1);
		r2 = (RelativeLayout) findViewById(R.id.buttonrow);
		r3 = (RelativeLayout) findViewById(R.id.statsrow);
		
		//Show ads
		 // Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    adView.setBackgroundColor(Color.LTGRAY);
	    AdRequest adr = new AdRequest();
	    adr.setTesting(true);
	    adView.loadAd(adr);

	}
	
	public void setEasy(View view) { 
		this.difficulty = 1;
		r1.setVisibility(View.VISIBLE);
	}
	
	public void setNormal(View view) { 
		this.difficulty = 2;
		r1.setVisibility(View.VISIBLE);
	}
	
	public void setHard(View view) { 
		this.difficulty = 3;
		r1.setVisibility(View.VISIBLE);
	}
	
	public String getDifficulty() { 
		if( this.difficulty == 1) { return "Easy"; }
		else if( this.difficulty == 2) { return "Normal"; }
		else if( this.difficulty == 3) { return "Hard"; }
		return "";
	}
	
	public String getPercentage(String total, String correct) {
		int nt = Integer.parseInt(total);
		int nc = Integer.parseInt(correct);
		
		if( nt > 0 ) { return Double.toString(Double.valueOf(nc*100/nt)); }
		return "0.0";
	}
	
	//key = operation + difficulty
	public void setAdd(View view) { 
		String key = "1" + Integer.toString(this.difficulty);
		ArrayList<String> stats = eng.gethistorystats().get(key);
		
		r1.setVisibility(View.INVISIBLE);
		r2.setVisibility(View.INVISIBLE);
		r3.setVisibility(View.VISIBLE);
	
		tvoperation.setText("Operation : Addition");
		tvdifficulty.setText("Difficulty: " + this.getDifficulty()); 
		tvtotal.setText("Total Questions: " + stats.get(0));
		tvcorrect.setText("Total Correct: " + stats.get(1));
		tvwrong.setText("Total Wrong: " + stats.get(2));
		tvperc.setText("Percent Correct: " + this.getPercentage(stats.get(0), stats.get(1)) + " %");
	}
	
	public void setSub(View view) { 
		String key = "2" + Integer.toString(this.difficulty);
		ArrayList<String> stats = eng.gethistorystats().get(key);
		
		r1.setVisibility(View.INVISIBLE);
		r2.setVisibility(View.INVISIBLE);
		r3.setVisibility(View.VISIBLE);
		
		tvoperation.setText("Operation : Subtraction");
		tvdifficulty.setText("Difficulty: " + this.getDifficulty()); 
		tvtotal.setText("Total Questions: " + stats.get(0));
		tvcorrect.setText("Total Correct: " + stats.get(1));
		tvwrong.setText("Total Wrong: " + stats.get(2));
		tvperc.setText("Percent Correct: " + this.getPercentage(stats.get(0), stats.get(1)) + " %");
	}
	
	public void setMul(View view) { 
		String key = "3" + Integer.toString(this.difficulty);
		ArrayList<String> stats = eng.gethistorystats().get(key);
		
		r1.setVisibility(View.INVISIBLE);
		r2.setVisibility(View.INVISIBLE);
		r3.setVisibility(View.VISIBLE);
		
		tvoperation.setText("Operation : Multiplication");
		tvdifficulty.setText("Difficulty: " + this.getDifficulty()); 
		tvtotal.setText("Total Questions: " + stats.get(0));
		tvcorrect.setText("Total Correct: " + stats.get(1));
		tvwrong.setText("Total Wrong: " + stats.get(2));
		tvperc.setText("Percent Correct: " + this.getPercentage(stats.get(0), stats.get(1)) + " %");
	}
	
	public void setDiv(View view) {
		String key = "4" + Integer.toString(this.difficulty);
		ArrayList<String> stats = eng.gethistorystats().get(key);
		
		r1.setVisibility(View.INVISIBLE);
		r2.setVisibility(View.INVISIBLE);
		r3.setVisibility(View.VISIBLE);
		
		tvoperation.setText("Operation : Divison");
		tvdifficulty.setText("Difficulty: " + this.getDifficulty()); 
		tvtotal.setText("Total Questions: " + stats.get(0));
		tvcorrect.setText("Total Correct: " + stats.get(1));
		tvwrong.setText("Total Wrong: " + stats.get(2));
		tvperc.setText("Percent Correct: " + this.getPercentage(stats.get(0), stats.get(1)) + " %");
	}
	
	public void goback(View view) {
		Intent i = new Intent(this, DMG.class);
    	startActivity(i);
	}
	
	public void gostats(View view) { 
		Intent i = new Intent(this, vHistoryStats.class);
    	startActivity(i);
	}
}
