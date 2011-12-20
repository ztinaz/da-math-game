package com.games.dmg;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.games.dmg.Engine;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


public class vStats extends Activity  {
	
	private String numquestions;
	private String numcorrect;
	private String numwrong;
	private String percentcorrect;
	private String operation;
	private String difficulty;
	private Engine eng;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        eng = new Engine();
        
        //Retrieve values from the intent and bundle
        try {
        	 Bundle b 	    = getIntent().getExtras();
        	 numquestions   = b.getString("numquestions");
        	 operation      = b.getString("operation");
        	 numcorrect     = b.getString("numcorrect"); 
        	 numwrong	    = b.getString("numwrong");
        	 percentcorrect = b.getString("percentcorrect");
        	 difficulty     = b.getString("difficulty");
        	 
        	 this.updategui();
        } catch(Exception e) { ; }
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if( keyCode == KeyEvent.KEYCODE_BACK) { return true; }
    	return super.onKeyDown(keyCode, event);
    }
	
	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	
	private void updategui() {
		TextView tvnumquestions = (TextView) findViewById(R.id.tvNumQuestions);
		tvnumquestions.setText("Number of Questions: " + this.numquestions);
		
		TextView tvnumcorrect = (TextView) findViewById(R.id.tvNumCorrect);
		tvnumcorrect.setText("Correct Answers: " + this.numcorrect);
		
		TextView tvnumwrong = (TextView) findViewById(R.id.tvNumWrong);
		tvnumwrong.setText("Wrong Answers: " + this.numwrong);
		
		TextView tvcorrectperc = (TextView) findViewById(R.id.tvCorrectPerc);
		
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		this.percentcorrect = Double.toString( Double.valueOf(twoDForm.format(Double.parseDouble(this.percentcorrect))));
		
		tvcorrectperc.setText("Correct Percentage: " + this.percentcorrect + "%");
		
		CategorySeries categoryseries = new CategorySeries("Correct vs. Wrong");
		categoryseries.add("Correct (" + this.numcorrect + ")", Double.parseDouble(this.numcorrect));
		categoryseries.add("Wrong (" + this.numwrong + ")", Double.parseDouble(this.numwrong));
		
		int[] colors = new int[] { Color.YELLOW, Color.BLUE };
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		renderer.setLabelsTextSize((float) 15);
		renderer.setLabelsColor(Color.DKGRAY);
		renderer.setShowLegend(false);
		
		renderer.setPanEnabled(false);
		
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		
		GraphicalView mChartView = ChartFactory.getPieChartView(this, categoryseries, renderer);
		layout.addView(mChartView, new LayoutParams
				(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		//Updates for the this run
		eng.writestats(this.operation, this.difficulty, this.numquestions, this.numcorrect, this.numwrong);	
	}
	
	/******************* Button Click Handler for bottom buttons Starts *******************/
	public void statshistory(View view) {
		try {
			Intent i = new Intent(this, vHistoryStats.class);
			startActivity(i);
		} catch(Exception e) { ; } 
	}
	
	public void backtomain(View view) { 
		Intent i = new Intent(this, DMG.class);
		startActivity(i);
	}
	
	/******************* Button Click Handler for bottom buttons Ends   *******************/
}
