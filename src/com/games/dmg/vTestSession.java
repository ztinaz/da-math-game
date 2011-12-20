package com.games.dmg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import java.lang.Math.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class vTestSession extends Activity {
	
	private int numcorrect;
	private int numwrong;
	private int operation;
	private int difficulty;
	private int numquestions;
	private ArrayList<Object> questions;
	private boolean timebased;
	private static int currentindex;
	private ArrayList<String> current;
	private boolean answered;
	private int totaltime; //in ms
	
	private int correctanswer; //1-4 each integer represents one of the answer buttons
	private int useranswer;    //Integer which is set one a button click - if it matches then answer is right
	private static cGameCounter counter;
	private ProgressBar pb;
	
	private class cGameCounter extends CountDownTimer{

		public cGameCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
		}
		
		@Override
		public void onFinish() {
			//Mark it as a wrong answer
			TextView tvWrong = (TextView) findViewById(R.id.tvWrong);
			tvWrong.setText(Integer.toString(vTestSession.this.numwrong));
			
			if( !vTestSession.this.answered ) {
				vTestSession.this.numwrong++;
				vTestSession.this.currentindex++;
				vTestSession.this.setuparun();
				pb.setProgress(0);
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
			pb.incrementProgressBy(1);
		}
	}

	

	 /** Called when the activity is first created. */
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        
        numcorrect    = 0;
        numwrong      = 0;
        correctanswer = 0;
        operation  	  = 0;
        difficulty    = 0;
        currentindex  = 0;
        
        questions  = new ArrayList<Object>();
        timebased  = false;
        current    = new ArrayList<String>();
        pb = (ProgressBar) findViewById(R.id.pbTimer);
        
        
        //retrieve values from the bundle
        try {
	        Bundle b = getIntent().getExtras();
	        difficulty = Integer.parseInt(b.getString("difficulty"));
	        operation  = Integer.parseInt(b.getString("operation"));
	        numquestions = Integer.parseInt(b.getString("questions"));
	        timebased  = Boolean.parseBoolean(b.getString("timebased"));
	        questions  =  (ArrayList<Object>) b.getParcelableArrayList("questionset").get(0);
	        
	        //Initialize status bar widgets
	        this.sbdifficulty();
	        //this.sbnumquestions();
	        this.sbtimebased();
	        
	        if( difficulty == 1 ) { this.totaltime = 10000;      pb.setMax(10); }
	        else if( difficulty == 2 ) { this.totaltime = 20000; pb.setMax(20); }
	        else if( difficulty == 3 ) { this.totaltime = 30000; pb.setMax(30); }
	        	        
	        this.setuparun();
        } catch(Exception e) { ; }
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if( keyCode == KeyEvent.KEYCODE_BACK) { return true; }
    	return super.onKeyDown(keyCode, event);
    }
    
    private void setuparun() {
    	
    	if( this.timebased == true ) {
    		//start the timer
    		counter = new cGameCounter(this.totaltime,1000);
    		
    		if( this.currentindex > this.numquestions-1 || this.currentindex > this.questions.size()-1)    		
    			counter.cancel();
    		else 
    			counter.start();
    	}

    	//retrieve the first question
        current = this.getcurrentquestion();
        
        if( current == null && this.timebased) counter.cancel();
        
        this.createchoices();
    }
    
    private void createchoices() {
    	if( this.current == null ) { 
    		this.answered = true;
    		
    		if( this.timebased) { counter.cancel(); }
    		
    		Intent i = new Intent();
        	Bundle b = new Bundle();
        	b.putString("numquestions", Integer.toString(this.numquestions));
        	b.putString("numcorrect", Integer.toString(this.numcorrect));
        	b.putString("numwrong", Integer.toString(this.numwrong));
        	b.putString("percentcorrect", Double.toString( (float) this.numcorrect * 100/(float) this.numquestions));
        	b.putString("operation", Integer.toString(this.operation));
        	b.putString("difficulty", Integer.toString(this.difficulty));
        	
        	i.setClass(this, vStats.class);
        	i.putExtras(b);
    	    startActivity(i);

    		return; 
    	}
    	
    	this.answered = false;
    	Random r = new Random();
    	
    	String operator = "";
    	ImageView ivOperation = (ImageView) findViewById(R.id.ivOperation);
    	
    	if( this.operation == 1)      { ivOperation.setImageResource(R.drawable.add_ico);      operator = "+ "; }
    	else if( this.operation == 2) { ivOperation.setImageResource(R.drawable.minus_ico);    operator = "- "; }
    	else if( this.operation == 3) { ivOperation.setImageResource(R.drawable.multiply_ico); operator = "x "; }
    	else if (this.operation == 4) { ivOperation.setImageResource(R.drawable.divide_ico);   operator = "/ "; }
    	
    	try {
	    	//Setup GUI elements 
	    	TextView operand1 = (TextView) findViewById(R.id.tvOperand1);
	    	TextView operand2 = (TextView) findViewById(R.id.tvOperand2);
	    	TextView operation = (TextView) findViewById(R.id.tvOperation);
    	
	    	operand1.setText(this.current.get(0));
	    	operand2.setText(this.current.get(1));
	    	operation.setText(operator);
	    	
    	} catch(Exception e) { ; }
    	
    	//Randomly assign correct answer to one of four choices
    	this.correctanswer = r.nextInt(4);
    	if( this.correctanswer == 0 ) { this.correctanswer = 1; }
    	
    	this.assignanswers();
    }
    
    private void assignanswers() {
    	
    	Random r = new Random();
    	String choice1 = "";
    	String choice2 = "";
    	String choice3 = "";	
    	
    	if( this.operation != 4) {
    		int answer = Integer.parseInt(this.current.get(3));
    		answer = java.lang.Math.abs(answer);
    		
    		int c1 = 0, c2 = 0, c3 = 0;
    		c1 = r.nextInt(answer+2);  
    		c2 = r.nextInt(answer+3);
    		c3 = r.nextInt(answer+4);
    		
    		if( c1 == 0 ) { r.nextInt(answer+1); }
    		if( c2 == 0 ) { r.nextInt(answer+5); }
    		if( c3 == 0 ) { r.nextInt(answer+7); }
    		
    		if( c1 == c2 )      { c1 = r.nextInt(answer+2); }
    		else if( c2 == c3 ) { c2 = r.nextInt(answer+4); }
    		else if( c3 == c1 ) { c3 = r.nextInt(answer+6); }
    		else if( c1 == answer ) { c1 = r.nextInt(answer+3); }
    		else if( c2 == answer ) { c2 = r.nextInt(answer+1); }
    		else if( c3 == answer ) { c3 = r.nextInt(answer+4); }
    		
    		choice1 = Integer.toString( c1 );
    		choice2 = Integer.toString( c2 );
    		choice3 = Integer.toString( c3 );
    		
    	}
    	else {
    		DecimalFormat twoDForm = new DecimalFormat("#.##");
    		Double answer = Double.parseDouble(this.current.get(3));
    		
    		int n = r.nextInt(13); int d = r.nextInt(5);
    		
    		Double c1 = 0.00, c2=0.00, c3=0.00;
    		if( d > 0 ) c1 = Double.valueOf((float) n/ (float) d);
    		else if( d == 0 ) c1 = Double.valueOf((float) n/ (float) 2.40);
    		
    		n = r.nextInt(17); d = r.nextInt(7);
    		if( d > 0 ) c2 = Double.valueOf( (float) n/ (float) d);
    		else if( d == 0 ) c2 = Double.valueOf((float) n/ (float) 1.90);

    		n = r.nextInt(15); d = r.nextInt(3);
    		if( d > 0 ) c3 = Double.valueOf( (float) n/ (float) d);
    		else if( d == 0 ) c2 = Double.valueOf((float) n/ (float) 2.25);
    		
    		choice1 = Double.toString( Double.valueOf(twoDForm.format(c1)) );
    		choice2 = Double.toString( Double.valueOf(twoDForm.format(c2)) );
    		choice3 = Double.toString( Double.valueOf(twoDForm.format(c3)) );
    	}
    	
    	Button answer1 = (Button) findViewById(R.id.answer1);
    	Button answer2 = (Button) findViewById(R.id.answer2);
		Button answer3 = (Button) findViewById(R.id.answer3);
		Button answer4 = (Button) findViewById(R.id.answer4);
    	
    	try {
	    	if( this.correctanswer == 1) {
	    		answer1.setText(this.current.get(3));
	    		answer2.setText(choice1);
	    		answer3.setText(choice2);
	    		answer4.setText(choice3);
	    	}
	    	
	    	if( this.correctanswer == 2) { 
	    		answer1.setText(choice1);
	    		answer2.setText(this.current.get(3));
	    		answer3.setText(choice2);
	    		answer4.setText(choice3);
	    	}
	    	
	    	if( this.correctanswer == 3) {
	    		answer1.setText(choice1);
	    		answer2.setText(choice2);
	    		answer3.setText(this.current.get(3));
	    		answer4.setText(choice3);
	    	}
	    	
	    	if( this.correctanswer == 4) {
	    		answer1.setText(choice1);
	    		answer2.setText(choice2);
	    		answer3.setText(choice3);
	    		answer4.setText(this.current.get(3));
	    	}

    	} catch(Exception e) { ; }
    }
    
    private void sbtimebased() {
    	ImageView ivTimed = (ImageView) findViewById(R.id.ivTimed);
    	ProgressBar pb   = (ProgressBar) findViewById(R.id.pbTimer);
    	
    	if( this.timebased == true ) { 
    		ivTimed.setImageResource(R.drawable.timed_ico);
    		pb.setVisibility(View.VISIBLE);
    	}
    	else if( this.timebased == false ) {
    		ivTimed.setImageResource(R.drawable.nottimed_ico);
    		pb.setVisibility(View.INVISIBLE);
    	}
    }
    
    private void sbdifficulty() {
    	//TextView tvDifficulty = (TextView) findViewById(R.id.tvDifficulty);
    	ImageView ivDifficulty = (ImageView) findViewById(R.id.ivDifficulty);
    	if( this.difficulty == 1 )     { ivDifficulty.setImageResource(R.drawable.easy_ico); }
    	else if( this.difficulty == 2) { ivDifficulty.setImageResource(R.drawable.normal_ico); }
    	else if( this.difficulty == 3) { ivDifficulty.setImageResource(R.drawable.hard_ico); }
    }
    
   
    @SuppressWarnings("unchecked")
	private ArrayList<String> getcurrentquestion() {
    	ArrayList<String> retval = new ArrayList<String>();
    	Random r = new Random();
    	
    	//Check if the current index is not over size of question set and maximum number of questions then return null
    	if( this.currentindex > this.numquestions-1 || this.currentindex > this.questions.size()-1) { return null; }
    	
    	int qindex = r.nextInt(this.questions.size());
    	retval = (ArrayList<String>) this.questions.get(qindex);
    	return retval;
    }
    
    public void quittest(View view) {
    	Intent i = new Intent(this, DMG.class);
    	startActivity(i);
    }
    
    /******************** Event Handles for Answers Buttons Starts ********************/
    public void ans1clicked(View view) {
    	
    	TextView tvcorrect = (TextView) findViewById(R.id.tvCorrect);
    	TextView tvwrong   = (TextView) findViewById(R.id.tvWrong);
    	
    	if( this.correctanswer == 1) {
    		this.numcorrect++;
    		tvcorrect.setText(Integer.toString(this.numcorrect));
    	} else { 
    		this.numwrong++; 
    		tvwrong.setText(Integer.toString(this.numwrong));
    	}
    	
    	if( this.timebased ) this.counter.cancel();
    	pb.setProgress(0);
    	this.answered = true;
    	this.currentindex++;
    	this.setuparun();
    }
    
    public void ans2clicked(View view) {
    	
    	TextView tvcorrect = (TextView) findViewById(R.id.tvCorrect);
    	TextView tvwrong   = (TextView) findViewById(R.id.tvWrong);
    	
    	if( this.correctanswer == 2) {
    		this.numcorrect++;
    		tvcorrect.setText(Integer.toString(this.numcorrect));
    	} else { 
    		this.numwrong++; 
    		tvwrong.setText(Integer.toString(this.numwrong));
    	}
    	if( this.timebased ) this.counter.cancel();
    	pb.setProgress(0);
    	this.answered = true;
    	this.currentindex++;
    	this.setuparun();
    }
    
    public void ans3clicked(View view) {
    	
    	TextView tvcorrect = (TextView) findViewById(R.id.tvCorrect);
    	TextView tvwrong   = (TextView) findViewById(R.id.tvWrong);
    	
    	if( this.correctanswer == 3) {
    		this.numcorrect++;
    		tvcorrect.setText(Integer.toString(this.numcorrect));
    	} else { 
    		this.numwrong++; 
    		tvwrong.setText(Integer.toString(this.numwrong));
    	}
    	
    	if( this.timebased ) this.counter.cancel();
    	pb.setProgress(0);
    	this.answered = true;
    	this.currentindex++;
    	this.setuparun();
    }
    
    public void ans4clicked(View view) {


    	TextView tvcorrect = (TextView) findViewById(R.id.tvCorrect);
    	TextView tvwrong   = (TextView) findViewById(R.id.tvWrong);
    	
    	if( this.correctanswer == 4) {
    		this.numcorrect++;
    		tvcorrect.setText(Integer.toString(this.numcorrect));
    	} else { 
    		this.numwrong++; 
    		tvwrong.setText(Integer.toString(this.numwrong));
    	}
    	if( this.timebased ) this.counter.cancel();
    	pb.setProgress(0);
    	this.answered = true;
    	this.currentindex++;
    	this.setuparun();
    }
    
    /******************** Event Handles for Answers Buttons Ends   ********************/
    
}
